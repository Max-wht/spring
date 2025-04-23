package com.max.useframework.service;

import com.max.spring.Autowired;
import com.max.spring.Component;
import com.max.spring.Scope;

/**
 * @author Max
 * @description
 * @date 2025/4/22 16:35
 */

@Component("demoService")
@Scope("prototype")
public class DemoService {

    @Autowired
    private BService bService;
    public void testAutowired() {
        System.out.println(bService);
    }
}
