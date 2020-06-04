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

package com.matrix.proxy.webscoket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.matrix.proxy.module.Command;
import com.matrix.proxy.server.ServerConnection;
import com.matrix.proxy.server.ServerConnectionStore;
import com.matrix.proxy.session.Session;
import com.matrix.proxy.session.SessionManager;
import com.matrix.proxy.util.CommandCode;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author zhenyu.nie created on 2019 2019/5/16 16:12
 */
@ChannelHandler.Sharable
@Slf4j
@Service
public class WebRequestHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


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


        if (type.intValue() == CommandCode.HEART.getCode()) {
            return;
        }

        log.info("uid :{}  type:{} execute command:  {}",instanceUuid,type,command);

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
        log.info("request command :{}  type:{}  id:{}",cmd.getCommand(),cmd.getCode(),cmd.getId());
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
