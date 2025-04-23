package com.max.useframework;

import com.max.spring.ApplicationContext;
import com.max.useframework.service.BService;
import com.max.useframework.service.DemoService;

/**
 * @author Max
 * @description    
 * @date 2025/4/22 16:38
 */
public class main {

    public static void main(String[] args) throws ClassNotFoundException {

        ApplicationContext app = new ApplicationContext(AppConfig.class);

        DemoService demoService = (DemoService) app.getBean("demoService");
        BService bService = (BService) app.getBean("bService");
        System.out.println(bService.beanName);
    }
}
