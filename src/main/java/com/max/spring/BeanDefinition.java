package com.max.spring;

/**
 * @author Max
 * @description
 * @date 2025/4/22 16:43
 */
public class BeanDefinition {

    private Class clazz;
    private String scope;

    public BeanDefinition(Class clazz, String scope) {
        this.clazz = clazz;
        this.scope = scope;
    }
    public BeanDefinition(){
        this.clazz = null;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
