
package com.matrix.proxy.server.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.matrix.proxy.server.ServerConnectionStore;
import com.matrix.proxy.session.DefaultSession;
import com.matrix.proxy.util.ResponseCode;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterProcessor extends DefaultMessageProcess {

    private static final Logger logger = LoggerFactory.getLogger(RegisterProcessor.class);

    @Resource
    private ServerConnectionStore connectionStore;


    public RegisterProcessor() {
    }

    @Override
    public Integer code() {
        return ResponseCode.REGIST.getCode();
    }
    @Override
    public void process(ChannelHandlerContext ctx, String message) {
        //TODO 进行数据注册
        JSONObject obj = JSON.parseObject(message);
        String instanceUuid = obj.getString("instanceUuid");
        String instanceName = obj.getString("instanceName");
        String id = instanceName + "_" + instanceUuid;
        connectionStore.register(id, ctx.channel());
        logger.info("应用实例：id {} ,channel :{}注册成功！",id,ctx.channel());
        ctx.channel().writeAndFlush(initRegisterResponse(id));
    }


    private String initRegisterResponse(String id) {

        Map<String, Object> result = new HashMap<>(16);
        result.put("code", ResponseCode.REGIST.getCode());
        result.put("id", id);
        result.put("command", true);
        return JSON.toJSONString(result);
    }
}
