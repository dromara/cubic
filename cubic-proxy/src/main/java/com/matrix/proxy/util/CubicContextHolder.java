package com.matrix.proxy.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MonitorContextHolder
 * @Author QIANGLU
 * @Date 2019/10/23 6:52 下午
 * @Version 1.0
 */
@Component
public class CubicContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static ConcurrentHashMap<String, Object> dispatchers = new ConcurrentHashMap<>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CubicContextHolder.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {

        return applicationContext.getBean(beanName, clazz);
    }
    public static <T> T getBean( Class<T> clazz) {
        return applicationContext.getBean( clazz);
    }

    public static <T> T getCache( Class<T> clazz) {
        Object dispatcher = dispatchers.get(clazz.getSimpleName());
        if (dispatcher != null) {
            return (T) dispatcher;
        }
        T bean = applicationContext.getBean(clazz);
        dispatchers.put(bean.getClass().getSimpleName(), bean);
        return bean;
    }


    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }
}
