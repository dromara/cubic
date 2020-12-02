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

package com.cubic.agent.remote;

import com.cubic.agent.arthas.ArthasTaskFactory;
import com.cubic.agent.conf.AgentConfig;
import com.cubic.agent.process.*;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
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
        init();
    }

    private void init() {

        processors = ImmutableList.of(new RegisterProcessor(),new HeartbeatProcessor(), new CommandProcessor(), new ThreadDumpProcessor(), new ArthasProcessor(ArthasTaskFactory.getInstance()));

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
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        String delimiter = "_$";
                        socketChannel.pipeline()
                                .addLast(new DelimiterBasedFrameDecoder(10240, Unpooled.wrappedBuffer(delimiter.getBytes())))
                                .addLast(new StringDecoder())
                                .addLast(new DelimiterBasedFrameEncoder(delimiter))
                                .addLast(idleStateHandler)
                                .addLast(requestHandler)
                                .addLast(connectionManagerHandler);
                    }
                });
        log.info("will connection server:{}...", server);
        bootstrap.connect(ipAndPort[0], Integer.parseInt(ipAndPort[1])).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    log.info("cubic netty client start success, {}");
                    channel = future.channel();

//                    closeFuture(taskStore);
                    running.compareAndSet(false, true);
                    started.set(null);
                } else {
                    started.set(null);
//                    log.warn("cubic netty client start fail");
//                    log.info("agent client start error will restart  10s...");
//                    future.channel().eventLoop().schedule(new Runnable() {
//                        @Override
//                        public void run() {
//                            AgentNettyClient client = new AgentNettyClient();
//                            client.start();
//                        }
//                    }, 10L, TimeUnit.SECONDS);
                }
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

//    private void closeFuture(final DefaultTaskStore taskStore) {
//        channel.closeFuture().addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                taskStore.close();
//            }
//        });
//    }

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

//        private void restart(EventLoop eventLoop) {
//            log.info("agent client hert error will restart  10s...");
//            eventLoop.schedule(new Runnable() {
//                @Override
//                public void run() {
//                    AgentNettyClient client = new AgentNettyClient();
//                    client.start();
//                }
//            }, 10L, TimeUnit.SECONDS);
//        }

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
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
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
