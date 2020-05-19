package com.matrix.proxy.util;

/**
 * @ClassName ResponseCode
 * @Author QIANGLU
 * @Date 2020/3/20 1:37 下午
 * @Version 1.0
 */
public enum ResponseCode {

    RESP_TYPE_HEARTBEAT(0),

    /**
     * 基础模式
     */
    COMMAND(1),

    /**
     * dump
     */
    THREAD_DUMP(2),

    /**
     * arthas模式
     */
    ARTHAS(3);


    private Integer code;

    private ResponseCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}
