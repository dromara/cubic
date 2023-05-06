
package com.cubic.agent.core.process;


import com.cubic.agent.core.boot.DefaultProcess;
import com.cubic.agent.core.common.ArthasResponseHandler;
import com.cubic.agent.core.factory.Task;
import com.cubic.agent.core.remote.CommandCode;
import com.cubic.agent.core.factory.ArthasTelnetTaskFactory;
import com.google.common.collect.ImmutableList;
import com.cubic.agent.core.factory.TaskFactory;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * 使用arthas进行处理
 * @author luqiang
 */
@DefaultProcess
public class ArthasProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(ArthasProcessor.class);

    private final TaskFactory factory;

    public ArthasProcessor() {
        this.factory = ArthasTelnetTaskFactory.getInstance();
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
