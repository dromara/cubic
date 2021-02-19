
package com.cubic.agent.process;


import com.cubic.agent.common.ArthasResponseHandler;
import com.cubic.agent.factory.Task;
import com.google.common.collect.ImmutableList;
import com.cubic.agent.factory.TaskFactory;
import com.cubic.agent.remote.CommandCode;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * 使用arthas进行处理
 */
public class ArthasProcessor implements Processor<String> {

    private static final Logger log = LoggerFactory.getLogger(ArthasProcessor.class);

    private final TaskFactory factory;

    public ArthasProcessor(TaskFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Integer> types() {
        return ImmutableList.of(CommandCode.ARTHAS.getCode());
    }

    @Override
    public void process(ChannelHandlerContext ctx, String id, String command) {

        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        ArthasResponseHandler handler = new ArthasResponseHandler(ctx, id);
        Task task = factory.create(id, command, pid, handler);
        if (task == null) {
            return;
        }
        task.execute();

    }


}
