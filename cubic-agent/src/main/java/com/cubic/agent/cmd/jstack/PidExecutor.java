
package com.cubic.agent.cmd.jstack;

/**
 * @author luqiang
 */
public interface PidExecutor {

    /**
     * 根据pid 执行命令
     * @param pid
     * @return
     */
    String execute(String pid,String command);
}
