package com.max.useframework.service;

import com.max.spring.BeanNameAware;
import com.max.spring.Component;

/**
 * @author Max
 * @description
 * @date 2025/4/22 19:20
 */

@Component("bService")
public class BService implements BeanNameAware {
    public String beanName;
    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
