/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.cubic.agent.remote;


import com.cubic.agent.module.Message;
import com.cubic.agent.process.Processor;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author luqiang
 * 处理业务命令
 */
@ChannelHandler.Sharable
public class AgentRequestHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(AgentRequestHandler.class);


    private final Map<Integer, Processor> processorMap;

    public AgentRequestHandler(List<Processor> processors) {
        ImmutableMap.Builder<Integer, Processor> builder = new ImmutableMap.Builder<Integer, Processor>();
        for (Processor processor : processors) {
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
        String body = obj.getBody();

        if (code.intValue() != CommandCode.HEARTBEAT.getCode()) {
            log.debug("agent receive process code {} request: id={}, sourceIp={}, code={}", code, id, ctx.channel().remoteAddress(), code);
        }

        Processor processor = processorMap.get(code.intValue());
        if (processor == null) {
            log.warn("agent receive process code {} request: id={}, sourceIp={}, code={} ,but it can not find process", code, id, ctx.channel().remoteAddress(), code);

            return;
        }
        processor.process(ctx, id, command, body);
    }
}
