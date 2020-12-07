package com.cubic.proxy.common.constant;

/**
 * @ClassName ResponseCode
 * @Author QIANGLU
 * @Date 2020/3/20 1:37 下午
 * @Version 1.0
 */
public enum CommandCode {

    /**
     * 心跳命令
     */
    HEART(0),
    /**
     * 基础模式
     */
    COMMAND(1),

    /**
     * arthas
     */
    ARTHAS(3),

    /**
     * arthas模式
     */
    LINUX(4),
    /**
     * 主动断开
     */
    REQ_TYPE_CANCEL(999);


    private Integer code;

    private CommandCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }


}
