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

package com.cubic.proxy.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cubic.proxy.common.constant.CommandCode;
import com.cubic.proxy.common.server.ServerConnection;
import com.cubic.proxy.common.server.ServerConnectionStore;
import com.cubic.proxy.common.module.Command;
import com.cubic.proxy.common.session.Session;
import com.cubic.proxy.common.session.SessionManager;
import com.cubic.proxy.common.webserver.WebConnection;
import com.cubic.proxy.common.webserver.WebConnectionStore;
import com.cubic.proxy.common.webserver.WebProcess;
import com.google.common.collect.ImmutableMap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author zhenyu.nie created on 2019 2019/5/16 16:12
 */
@ChannelHandler.Sharable
public class WebRequestHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    private Logger log = LoggerFactory.getLogger(WebRequestHandler.class);

    @Resource
    private SessionManager sessionManager;

    private WebConnectionStore webConnectionStore;

    private ServerConnectionStore serverConnectionStore;

    private final Map<String, WebProcess> processMap;

    public WebRequestHandler(WebConnectionStore webConnectionStore, ServerConnectionStore serverConnectionStore, List<WebProcess> webProcesses) {
        this.webConnectionStore = webConnectionStore;
        this.serverConnectionStore = serverConnectionStore;

        ImmutableMap.Builder<String, WebProcess> builder = new ImmutableMap.Builder();
        for (WebProcess processor : webProcesses) {
            builder.put(processor.command(), processor);
        }
        processMap = builder.build();

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {

        String data = msg.text();
        JSONObject params = JSON.parseObject(data);

        String instanceUuid = params.getString("instanceUuid");
        String command = params.getString("command");
        Integer type = params.getInteger("type");


        WebConnection webConnection = webConnectionStore.register(ctx.channel());


        if (type.intValue() == CommandCode.HEARTBEAT.getCode()) {
            return;
        }

        log.info("uid :{}  type:{} execute command:  {}", instanceUuid, type, command);

        if (type == null) {
            ctx.writeAndFlush(WebResponses.typeIsNullResponse());
            cancelRequest(webConnection);
            return;
        }


        if (StringUtils.isEmpty(command)) {
            ctx.writeAndFlush(WebResponses.nullResponse());
            return;
        }

        String cmd = baseCommandSupport(command, type);
        if (StringUtils.isNotEmpty(cmd)) {
            ctx.writeAndFlush(WebResponses.normalResponse(cmd));
            return;
        }

        Optional<ServerConnection> connection = serverConnectionStore.getConnection(instanceUuid);
        if (!connection.isPresent()) {
            ctx.channel().writeAndFlush(WebResponses.createNoConnectionResponse(instanceUuid));
            return;
        }


        Command.CommandBuilder commandBuilder = Command.builder();
        commandBuilder.id(UUID.randomUUID().toString()).code(type).command(command).instanceUuid(instanceUuid);
        int index = command.indexOf("\r");
        if (index != -1) {
            commandBuilder.command(command.substring(index));
        }

        writeAgent(commandBuilder.build(), webConnection, connection.isPresent() ? connection.get() : null);

    }


    /**
     * 执行部分基础命令
     *
     * @param command
     * @param type
     * @return
     */
    private String baseCommandSupport(String command, Integer type) {

        try {
            if (type.intValue() == CommandCode.COMMAND.getCode()) {
                String cmd = command;
                String rs = BaseCommandUtils.COMMAND_LIST.get(cmd);
                if (StringUtils.isNotEmpty(rs)) {
                    return rs;
                }
                String[] data = cmd.trim().split(" ");
                return processMap.get(data[0]).process(data[1]);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return "";
    }


    private Session writeAgent(Command cmd, WebConnection webConnection, ServerConnection serverConnection) {
        Session session = sessionManager.create(webConnection, serverConnection);
        cmd.setId(session.getId());
        session.writeToAgent(JSON.toJSONString(cmd));
        log.info("request command :{}  type:{}  id:{}", cmd.getCommand(), cmd.getCode(), cmd.getId());
        return session;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("服务器发生了异常:", cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete handshakeComplete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            String requestUri = handshakeComplete.requestUri();
            String subproTocol = handshakeComplete.selectedSubprotocol();
            log.info("web socket ctx {},requestUri:{},subproTocol:{}握手成功。", ctx, requestUri, subproTocol);
//            ctx.writeAndFlush(WebResponses.initResponse());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }


    private void cancelRequest(WebConnection webConnection) {
        webConnection.close();
    }

}
