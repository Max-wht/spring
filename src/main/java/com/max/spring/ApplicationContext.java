package com.max.spring;

import com.max.useframework.AppConfig;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Max
 * @description
 * @date 2025/4/22 16:11
 */
public class ApplicationContext {

    private Class configClass;
    private ConcurrentHashMap<String, Object> singleBeanMap = new ConcurrentHashMap<>();//单例池
    private HashMap<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private static ClassLoader classLoader = ApplicationContext.class.getClassLoader();
    public ApplicationContext(Class config) throws ClassNotFoundException {
        this.configClass = config;

        scan(config);

        for(Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()){
            String beanName = entry.getKey();
            String scope = entry.getValue().getScope();
            if(scope.equals("singleton")){
                Object bean = createBean(beanName, entry.getValue());
                singleBeanMap.put(beanName, bean);
            }
        }

    }

    public Object createBean(String beanName,BeanDefinition beanDefinition){
        Class clazz = beanDefinition.getClazz();
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()){
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object bean = getBean(field.getName());
                    field.setAccessible(true);
                    field.set(instance, bean);
                }
            }
            if(instance instanceof BeanNameAware){
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            return instance;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    public Object getBean(String beanName){
        if(beanDefinitionMap.containsKey(beanName)){
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(beanDefinition.getScope().equals("singleton")){
                Object o = singleBeanMap.get(beanName);
                return o;
            } else {
                Object bean = createBean(beanName,beanDefinition);
                return bean;
            }
        }
        throw new RuntimeException("beanName不存在");
    }
    private void scan(Class AppConfig) throws ClassNotFoundException {
        File directory = getDirectoryByComponentScanAnnotation(AppConfig);//service
        if( directory.isDirectory()){
            File[] files = directory.listFiles();

            for (File f : files) {
                String fileName = f.getAbsolutePath();
                if(fileName.endsWith(".class")){
                    String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                    className = className.replace("\\", ".");//com.max.useframework.service.demoService
                    Class<?> clazz = classLoader.loadClass(className);
                    if(clazz.isAnnotationPresent(Component.class)){
                        Component componment = clazz.getDeclaredAnnotation(Component.class);

                        String beanName = componment.value();
                        BeanDefinition beanDefinition = new BeanDefinition();
                        beanDefinition.setClazz(clazz);
                        if(clazz.isAnnotationPresent(Scope.class)){
                            Scope scope = clazz.getDeclaredAnnotation(Scope.class);
                            beanDefinition.setScope(scope.value());
                        }else{
                            beanDefinition.setScope("singleton");
                        }
                        beanDefinitionMap.put(beanName, beanDefinition);
                    }
                }
            }

        }

    }

    private static File getDirectoryByComponentScanAnnotation(Class AppConfig) {
        ComponmentScan componmentScan = (ComponmentScan) AppConfig.getDeclaredAnnotation(ComponmentScan.class);
        if (componmentScan == null) {
            throw new IllegalArgumentException("未添加配置类U_U");
        }
        String path = componmentScan.value();//com.max.useframework.service
        path = path.replace(".", "/");//com/max/useframework/service

        URL resource = classLoader.getResource(path);
        //file:/D:/JavaWeb/a_JavaWeb_BackEnd/learnFactory/spring/target/classes/com/max/useframework/service

        if (resource == null) {
            throw new IllegalArgumentException("配置类无法找到X_X");
        }
        File file = new File(resource.getFile());
        return file;
    }
}
