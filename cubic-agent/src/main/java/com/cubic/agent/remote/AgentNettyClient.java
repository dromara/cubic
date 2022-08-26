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

import com.cubic.agent.conf.AgentConfig;
import com.cubic.agent.process.Processor;
import com.cubic.serialization.agent.v1.CommonMessage;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author luqiang
 */
public class AgentNettyClient {

    private static final Logger log = LoggerFactory.getLogger(AgentNettyClient.class);

    private final Bootstrap bootstrap = new Bootstrap();

    private final EventLoopGroup WORKER_GROUP = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() - 1, new ThreadFactoryBuilder().setNameFormat("oap-netty-server-worker").build());

    private final AtomicBoolean running = new AtomicBoolean(false);

    private final SettableFuture<Void> started = SettableFuture.create();

    private volatile Channel channel;

    private final List<String> tcpServers;

    private final Random random = new Random();

    private List<Processor> processors;


    public AgentNettyClient() {
        tcpServers = Arrays.asList(AgentConfig.Agent.TCP_SERVERS.split(","));
    }

    public void init(List<Processor> processors) {

        this.processors = processors;
        log.info("AgentNettyClient init process size:{}", processors.size());

    }


    public void start() {
        final IdleStateHandler idleStateHandler = new IdleStateHandler(0, 0, 2, TimeUnit.MINUTES);

        final AgentRequestHandler requestHandler = new AgentRequestHandler(processors);

        final ConnectionManagerHandler connectionManagerHandler = new ConnectionManagerHandler();

        int index = Math.abs(random.nextInt()) % tcpServers.size();

        String server = tcpServers.get(index);
        String[] ipAndPort = server.split(":");
        bootstrap.group(WORKER_GROUP)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline()
                                .addLast(new ProtobufVarint32FrameDecoder())
                                .addLast(new ProtobufDecoder(CommonMessage.getDefaultInstance()))
                                .addLast(new ProtobufVarint32LengthFieldPrepender())
                                .addLast(new ProtobufEncoder())
                                .addLast(idleStateHandler)
                                .addLast(requestHandler)
                                .addLast(connectionManagerHandler);
                    }
                });
        log.info("will connection server:{}...", server);
        bootstrap.connect(ipAndPort[0], Integer.parseInt(ipAndPort[1])).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.info("cubic netty client start success");
                channel = future.channel();
                running.compareAndSet(false, true);
                started.set(null);
            } else {
                log.info("cubic netty client connect failure: {}", future);
                started.set(null);
            }
        });

        try {
            started.get();
        } catch (InterruptedException e) {
            log.error("start cubic netty client error", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("start cubic netty client error", e);
        }

    }


    public boolean isRunning() {
        return running.get();
    }

    public Channel getChannel() {
        return this.channel;
    }

    @ChannelHandler.Sharable
    private class ConnectionManagerHandler extends ChannelDuplexHandler {

        @Override
        public void disconnect(ChannelHandlerContext ctx, ChannelPromise future) throws Exception {
            log.warn("agent netty client channel disconnect, {}", ctx.channel());
            destroyAndSync();
            super.disconnect(ctx, future);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info("agent netty client channel active, {}", ctx.channel());
            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            log.warn("agent netty client channel inactive, {}", ctx.channel());
            destroyAndSync();
            //如果运行过程中服务端挂了,执行重连机制
            super.channelInactive(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                log.warn("agent netty client idle, {}", ctx.channel());
                destroyAndSync();
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            destroyAndSync();
        }
    }

    public synchronized void destroyAndSync() {
        if (running.compareAndSet(true, false)) {
            log.warn("agent netty client destroy, {}", channel);
            try {
                channel.close().syncUninterruptibly();
            } catch (Exception e) {
                log.error("close channel error", e);
            }
        }
    }

}
