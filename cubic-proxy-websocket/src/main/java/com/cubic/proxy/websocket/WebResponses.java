package com.cubic.proxy.websocket;

import com.cubic.proxy.common.webserver.TextDetails;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * @ClassName WebResponses
 * @Author QIANGLU
 * @Date 2020/4/7 1:48 下午
 * @Version 1.0
 */
public class WebResponses {

    public static WebSocketFrame createNoConnectionResponse(String agentId) {
        return new TextWebSocketFrame(String.format("无法连接到  agentId ： %s 的服务  \n   ", agentId));
    }

    public static WebSocketFrame createNoProcessResponse(String command) {
        return new TextWebSocketFrame(String.format("command %s process not found \n ", command));
    }

    public static WebSocketFrame initResponse() {
        return new TextWebSocketFrame("INIT_HELP \n");
    }

    public static WebSocketFrame typeIsNullResponse() {
        return new TextWebSocketFrame(TextDetails.TYPE_DETAILS);
    }

    public static WebSocketFrame nullResponse() {
        return new TextWebSocketFrame(" ");
    }

    public static WebSocketFrame normalResponse(String data) {
        return new TextWebSocketFrame(data);
    }

    public static WebSocketFrame noSupportTypeResponse(Integer type) {
        return new TextWebSocketFrame(String.format("No Support Type %d \r\n %s,", type, TextDetails.TYPE_DETAILS));
    }

    public static WebSocketFrame commandHelpResponse() {
        return new TextWebSocketFrame(("command help"));
    }

}
