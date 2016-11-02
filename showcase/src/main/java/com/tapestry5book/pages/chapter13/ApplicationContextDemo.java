package com.tapestry5book.pages.chapter13;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.context.ApplicationContext;

public class ApplicationContextDemo {
    @Inject
    private ApplicationContext applicationContext;

    public String[] getBeanDefinitionNames() {
        return applicationContext.getBeanDefinitionNames();
    }
}