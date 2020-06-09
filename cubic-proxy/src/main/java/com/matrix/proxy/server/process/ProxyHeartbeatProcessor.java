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

package com.matrix.proxy.server.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.matrix.proxy.server.ServerConnectionStore;
import com.matrix.proxy.util.ResponseCode;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProxyHeartbeatProcessor extends DefaultMessageProcess {

    private static final Logger logger = LoggerFactory.getLogger(ProxyHeartbeatProcessor.class);

    @Resource
    private ServerConnectionStore connectionStore;


    private final String heartbeatResponse = initHeartbeatResponse();

    public ProxyHeartbeatProcessor() {
    }

    @Override
    public Integer code() {
        return ResponseCode.HEARTBEAT.getCode();
    }

    @Override
    public void process(ChannelHandlerContext ctx, String message) {
        if (logger.isDebugEnabled()) {
            logger.info("receive  client heartbeat, {}", message);
        }
        ctx.channel().writeAndFlush(heartbeatResponse);
    }


    private String initHeartbeatResponse() {

        Map<String, Object> result = new HashMap<>(16);
        result.put("code", ResponseCode.HEARTBEAT.getCode());
        result.put("command", "heartbeat");
        return JSON.toJSONString(result);
    }
}
