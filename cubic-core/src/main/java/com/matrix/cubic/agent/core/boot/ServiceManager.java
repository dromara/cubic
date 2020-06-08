package com.matrix.cubic.agent.core.boot;

import com.matrix.cubic.agent.core.exception.ServiceConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @ClassName ServiceManager
 * @Author QIANGLU
 * @Date 2020/6/8 10:45 上午
 * @Version 1.0
 */

public enum ServiceManager {

    /**
     * 默认实例
     */
    INMSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(ServiceManager.class);

    private Map<Class, CommonService> services = Collections.emptyMap();

    public void start() {
        services = loadServices();
        onStart();
    }


    public void shutdown() {
        for (CommonService service : services.values()) {
            try {
                service.shutdown();
            } catch (Throwable e) {
                logger.error("ServiceManager try to shutdown [{}] fail.{}", service.getClass().getName(), e);
            }
        }
    }

    private void onStart() {

        for (CommonService service : services.values()) {

            try {
                service.start();
            } catch (Exception e) {
                logger.error("ServiceManager start service : [{}]  fail,{}", service.getClass().getSimpleName(), e);
            }

        }
    }

    private Map<Class, CommonService> loadServices() {
        Map<Class, CommonService> services = new LinkedHashMap<>(16);
        List<CommonService> allService = new LinkedList<>();

        for (CommonService service : ServiceLoader.load(CommonService.class)) {
            allService.add(service);
        }

        for (final CommonService service : allService) {
            Class<? extends CommonService> serviceClass = service.getClass();
            boolean isDefault = serviceClass.isAnnotationPresent(DefaultService.class);
            if (isDefault) {
                if (!services.containsKey(serviceClass)) {
                    services.put(serviceClass, service);
                } else {

                }
            } else {
                if (!services.containsKey(serviceClass)) {
                    services.put(serviceClass, service);
                } else {
                    throw new ServiceConflictException("Duplicate service define for :" + serviceClass);
                }
            }
        }

        return services;
    }
}
