
package com.matrix.cubic.agent.core.process;


import com.google.common.collect.ImmutableList;
import com.matrix.cubic.agent.core.remote.CommandCode;
import com.matrix.cubic.agent.core.remote.ResponseWriter;
import com.matrix.cubic.agent.core.jstack.CommandExecutor;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * 获取info信息
 */
public class CommandProcessor implements Processor<String> {

    private static final Logger log = LoggerFactory.getLogger(CommandProcessor.class);

    @Override
    public List<Integer> types() {
        return ImmutableList.of(CommandCode.COMMAND.getCode());
    }

    @Override
    public void process(ChannelHandlerContext ctx, String id, String command) {

        CommandExecutor commandExecutor = new CommandExecutor();
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        String data = commandExecutor.execute(pid,command);
        ResponseWriter.getInstance().write(ctx, CommandCode.COMMAND.getCode(),data,id);

    }


}
