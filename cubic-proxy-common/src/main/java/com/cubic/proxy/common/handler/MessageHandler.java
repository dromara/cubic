package com.cubic.proxy.common.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cubic.proxy.common.constant.CommandCode;
import com.google.common.collect.ImmutableMap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 用于集中管理处理器
 *
 * @ClassName MessageHandler
 * @Author QIANGLU
 * @Date 2020/3/19 11:12 上午
 * @Version 1.0
 */
@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    private final Map<Integer, ServerMessageProcess> processorMap;

    public MessageHandler(List<ServerMessageProcess> processors) {
        ImmutableMap.Builder<Integer, ServerMessageProcess> builder = new ImmutableMap.Builder<>();
        for (ServerMessageProcess processor : processors) {
            builder.put(processor.code(), processor);
        }
        processorMap = builder.build();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) {
        JSONObject obj = JSON.parseObject(message);
        int code = obj.getInteger("code");
        String instanceUuid = obj.getString("instanceUuid");
        if (code != CommandCode.HEARTBEAT.getCode()) {
            logger.debug("接收到 id:{}, code:{} 数据请求 ctx：{},message size:{}", instanceUuid, code, ctx.channel(), message.length());
        }
        ServerMessageProcess messageProcessor = processorMap.get(code);
        if (messageProcessor == null) {
            logger.warn("can not process message code [{}], {}", code, ctx.channel());
            return;
        }

        messageProcessor.process(ctx, message);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("MessageHandler active [{}], {}", ctx.channel());
    }
}
