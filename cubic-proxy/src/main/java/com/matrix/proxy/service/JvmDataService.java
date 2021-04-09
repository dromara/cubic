package com.matrix.proxy.service;

import com.github.pagehelper.PageInfo;
import com.matrix.proxy.entity.ThreadPoolEntity;
import com.matrix.proxy.vo.ThreadPoolQuery;

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
     * @param query
     * @return
     */
    PageInfo<ThreadPoolEntity> threadPoolDataPage(ThreadPoolQuery query);
}
