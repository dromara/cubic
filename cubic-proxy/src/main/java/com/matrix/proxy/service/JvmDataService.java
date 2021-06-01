package com.matrix.proxy.service;

import java.util.Map;

/**
 * JVM数据采集服务
 *
 * @author zhanghao
 * @date 2021/4/75:44 下午
 */
public interface JvmDataService {
    /**
     * 线程池数据查询分页
     *
     * @param instanceUid
     * @return
     */
    Map<String,Object> threadPoolDataPage(String instanceUid, String dayTime);
}
