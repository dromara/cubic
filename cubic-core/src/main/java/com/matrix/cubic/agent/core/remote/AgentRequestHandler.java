/*
 * Copyright (C) 2019 Qunar, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.matrix.cubic.agent.core.remote;


import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.matrix.cubic.agent.core.module.Message;
import com.matrix.cubic.agent.core.process.Processor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 处理业务命令
 */
@ChannelHandler.Sharable
public class AgentRequestHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(AgentRequestHandler.class);


    private final Map<Integer, Processor> processorMap;

    public AgentRequestHandler(List<Processor> processors) {
        ImmutableMap.Builder<Integer, Processor> builder = new ImmutableMap.Builder<Integer, Processor>();
        for (Processor<?> processor : processors) {
            for (Integer type : processor.types()) {
                builder.put(type, processor);
            }
        }
        processorMap = builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("request process error", cause);
        ctx.channel().close();
    }

    /**
     * 注意gson to map 数字会变成double
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        Gson gson = new Gson();
        String data = (String) msg;
        Message obj = gson.fromJson(data, Message.class);
        Integer code = obj.getCode();
        String command = obj.getCommand();
        String id = obj.getId();
        if (code.intValue() != CommandCode.HEARTBEAT.getCode()) {
            log.debug("agent receive process code {} request: id={}, sourceIp={}, code={}", code, id, ctx.channel().remoteAddress(), code);
        }

        Processor processor = processorMap.get(code.intValue());
        if (processor == null) {
            log.warn("agent receive process code {} request: id={}, sourceIp={}, code={} ,but it can not find process", code, id, ctx.channel().remoteAddress(), code);

            return;
        }
        processor.process(ctx, id, command);
    }
}
