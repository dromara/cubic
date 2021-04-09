package com.cubic.proxy.common.constant;

/**
 * @ClassName ResponseCode
 * @Author QIANGLU
 * @Date 2020/3/20 1:37 下午
 * @Version 1.0
 */
public enum CommandCode {

    /**
     * 心跳
     */
    HEARTBEAT(0),

    /**
     * 自定义命令
     */
    COMMAND(1),

    /**
     * thread dump状态
     */
    THREAD_DUMP(2),

    /**
     * arthas状态
     */
    ARTHAS(3),

    /**
     * 注册信息
     */
    REGIST(4),

    /**
     * JVM 信息
     */
    JVM(5),

    /**
     * JVM 线程栈信息
     */
    JVM_THREAD_DUMP(6),

    ARTHAS_START(7),

    /**
     * JVM 线程池信息
     */
    JVM_THREAD_POOL(8);

    private Integer code;

    private CommandCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }


}
