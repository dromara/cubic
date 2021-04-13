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
    private String instanceUuid;
    private String key;
    private String name;
    private Object arg;

}
