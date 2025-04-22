package com.max.useframework;

import com.max.spring.ApplicationContext;

/**
 * @author Max
 * @description    
 * @date 2025/4/22 16:38
 */
public class main {

    public static void main(String[] args) throws ClassNotFoundException {

        ApplicationContext app = new ApplicationContext(AppConfig.class);

        System.out.println(app.getBean("demoService"));
        System.out.println(app.getBean("demoService"));
    }
}
