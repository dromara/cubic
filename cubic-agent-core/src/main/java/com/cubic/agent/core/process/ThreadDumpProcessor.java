
package com.cubic.agent.core.process;


import com.cubic.agent.core.cmd.jstack.JstackExecutor;
import com.cubic.agent.core.remote.CommandCode;
import com.cubic.agent.core.remote.ResponseWriter;
import com.google.common.collect.ImmutableList;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * @author luqiang
 * 获取info信息
 */

public class ThreadDumpProcessor implements Processor  {

    private static final Logger log = LoggerFactory.getLogger(ThreadDumpProcessor.class);

    @Override
    public List<Integer> types() {
        return ImmutableList.of(CommandCode.THREAD_DUMP.getCode());
    }

    @Override
    public void process(ChannelHandlerContext ctx, String id, String command) {

        try {
            JstackExecutor executor = new JstackExecutor();
            String data = executor.execute(ManagementFactory.getRuntimeMXBean().getName().split("@")[0],"");
            ResponseWriter.getInstance().write(ctx, CommandCode.THREAD_DUMP.getCode(),data,id);

        } catch (Exception e) {
            log.warn("com.sun.tools.attach.VirtualMachine not found will choose ManagementFactory.getThreadMXBean().dumpAllThreads ");
        }
    }

}
