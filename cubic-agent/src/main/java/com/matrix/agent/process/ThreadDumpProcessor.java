
package com.matrix.agent.process;


import com.google.common.collect.ImmutableList;
import com.matrix.agent.ResponseCode;
import com.matrix.agent.ResponseWriter;
import com.matrix.agent.jstack.JstackExecutor;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * 获取info信息
 */
public class ThreadDumpProcessor implements Processor<String> {

    private static final Logger log = LoggerFactory.getLogger(ThreadDumpProcessor.class);

    @Override
    public List<Integer> types() {
        return ImmutableList.of(ResponseCode.THREAD_DUMP.getCode());
    }

    @Override
    public void process(ChannelHandlerContext ctx, String id, String command) {

        try {
            JstackExecutor executor = new JstackExecutor();
            String data = executor.execute(ManagementFactory.getRuntimeMXBean().getName().split("@")[0],"");
            ResponseWriter.getInstance().write(ctx,ResponseCode.THREAD_DUMP.getCode(),data,id);

        } catch (Exception e) {
            log.warn("com.sun.tools.attach.VirtualMachine not found will choose ManagementFactory.getThreadMXBean().dumpAllThreads ");
        }
    }

}
