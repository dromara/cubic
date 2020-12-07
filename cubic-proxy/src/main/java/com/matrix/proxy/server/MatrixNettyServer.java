package com.matrix.proxy.server;

import com.cubic.proxy.common.server.NettyServer;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.matrix.proxy.encoder.DelimiterBasedFrameEncoder;
import com.matrix.proxy.encoder.GzipDecoder;
import com.cubic.proxy.common.handler.ChannelCloseHandler;
import com.cubic.proxy.common.handler.ConnectionCounterHandler;
import com.cubic.proxy.common.handler.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName NettyServer
 * @Author QIANGLU
 * @Date 2020/3/18 5:39 下午
 * @Version 1.0
 */
@Slf4j
public class MatrixNettyServer implements NettyServer {


    private static final int DEFAULT_WRITE_LOW_WATER_MARK = 64 * 1024;

    private static final int DEFAULT_WRITE_HIGH_WATER_MARK = 128 * 1024;

    private final EventLoopGroup BOSS_GROUP = new NioEventLoopGroup(1, new ThreadFactoryBuilder().setNameFormat("agent-netty-server-boss").build());

    private final EventLoopGroup WORKER_GROUP = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() - 1, new ThreadFactoryBuilder().setNameFormat("oap-netty-server-worker").build());
    private volatile Channel channel;
    private final int port;
    private MessageHandler messageHandler;
    private Map<String, ChannelHandler> handlers = new HashMap<>();

    public MatrixNettyServer(int port, MessageHandler messageHandler) {
        this.port = port;
        this.messageHandler = messageHandler;
    }


    @Override
    public void start() {
        ConnectionCounterHandler connectionCounterHandler = new ConnectionCounterHandler("sky agent");
        ServerBootstrap bootstrap = new ServerBootstrap()
                .option(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .group(BOSS_GROUP, WORKER_GROUP)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        String delimiter = "_$";

                        ch.pipeline()
                                .addLast(new DelimiterBasedFrameDecoder(102400, Unpooled.wrappedBuffer(delimiter.getBytes())))
                                .addLast(new StringDecoder())
                                .addLast(new GzipDecoder())
                                .addLast("connectionCounter", connectionCounterHandler)
                                .addLast(new DelimiterBasedFrameEncoder(delimiter))
                                .addLast("messageHandler", messageHandler)
                                .addLast("idleHandler", new IdleStateHandler(0, 0, 2, TimeUnit.DAYS))
                                .addLast("closeHandler", new ChannelCloseHandler("agent"));
                        handlers.entrySet().forEach(entry -> {
                            ch.pipeline().addLast(entry.getKey(), entry.getValue());
                        });
                    }
                });

        try {
            this.channel = bootstrap.bind(port).sync().channel();
            log.info("netty server for agent, port {}", port);
        } catch (Exception e) {
            log.error("netty server for agent start fail", e);
        }
    }

    @Override
    public boolean isActive() {
        return channel.isActive();
    }

    @Override
    public void stop() {
        try {
            BOSS_GROUP.shutdownGracefully().sync();
            WORKER_GROUP.shutdownGracefully().sync();
            channel.close();
        } catch (InterruptedException e) {
            log.error("agent client close error", e);
        }
    }


    public void addHander(String name, ChannelHandler handler) {
        handlers.put(name, handler);
    }


}
