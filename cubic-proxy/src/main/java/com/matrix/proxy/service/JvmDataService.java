package com.matrix.proxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.matrix.proxy.entity.ThreadPoolEntity;
import com.matrix.proxy.vo.ThreadPoolQueryVo;

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
    IPage<ThreadPoolEntity> threadPoolDataPage(ThreadPoolQueryVo query);
}
