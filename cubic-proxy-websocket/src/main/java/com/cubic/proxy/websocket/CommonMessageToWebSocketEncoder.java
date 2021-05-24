package com.cubic.proxy.websocket;

import com.cubic.serialization.agent.v1.CommonMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

public class CommonMessageToWebSocketEncoder extends MessageToMessageEncoder<CommonMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CommonMessage msg, List<Object> out) throws Exception {
        out.add(new TextWebSocketFrame(msg.getBody()));
    }
}
