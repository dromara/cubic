package com.cubic.agent.cmd.jvm.threadpool;

/**
 * 线程池下发命令接收对象
 *
 * @author zhanghao
 * @date 2021/4/134:58 下午
 */
public class ThreadPoolCommand {
    private String key;
    private String name;
    private Object arg;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getArg() {
        return arg;
    }

    public void setArg(Object arg) {
        this.arg = arg;
    }

}
