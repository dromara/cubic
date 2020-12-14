
package com.cubic.agent.process;


import com.cubic.agent.cmd.jstack.CommandExecutor;
import com.cubic.agent.remote.CommandCode;
import com.cubic.agent.remote.ResponseWriter;
import com.google.common.collect.ImmutableList;
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
