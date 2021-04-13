package com.matrix.proxy.vo;

import lombok.Data;

/**
 * 线程池下发命名
 *
 * @author zhanghao
 * @date 2021/4/75:56 下午
 */
@Data
public class ThreadPoolCommandVo {
    /**
     * 实例UUID节点
     */
    private String instanceUuid;
    /**
     * 线程池的key
     * <p>
     * 查询列表接口返回
     */
    private String key;

    /**
     * name 对应 ThreadPoolExecutor set**方法
     * 目前支持：
     * setCorePoolSize 设置核心线程数
     * setKeepAliveTime 设置线程存活时间
     * setMaximumPoolSize 设置最大线程数
     * setRejectedExecutionHandler 设置拒绝策略
     */
    private String name;

    /**
     * name对应的参数
     * <p>
     * 注意：
     * 时间单位：秒
     * 拒绝策略：
     * AbortPolicy
     * CallerRunsPolicy
     * DiscardOldestPolicy
     * DiscardPolicy
     */
    private Object arg;

}
