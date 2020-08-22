package com.baizhi.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static Object getBean(String name){
        return context.getBean(name);
    }
    public static Object getBean(Class clazz){
        return context.getBean(clazz);
    }
    public static Object getBean(String name,Class clazz){
        return context.getBean(name,clazz);
    }
}
