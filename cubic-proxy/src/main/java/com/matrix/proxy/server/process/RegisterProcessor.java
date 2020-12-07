
package com.matrix.proxy.server.process;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.matrix.proxy.entity.Information;
import com.matrix.proxy.mapper.formationMapper;
import com.matrix.proxy.module.Message;
import com.cubic.proxy.common.server.ServerConnectionStore;
import com.cubic.proxy.common.constant.ResponseCode;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterProcessor extends DefaultMessageProcess {

    private static final Logger logger = LoggerFactory.getLogger(RegisterProcessor.class);

    @Resource
    private ServerConnectionStore connectionStore;

    @Resource
    private formationMapper formationMapper;

    public RegisterProcessor() {
    }

    @Override
    public Integer code() {
        return ResponseCode.REGIST.getCode();
    }

    @Override
    public void process(ChannelHandlerContext ctx, String message) {
        Message msg = JSON.parseObject(message, Message.class);
        String id = msg.getInstanceName() + "_" + msg.getInstanceUuid();
        connectionStore.register(id, ctx.channel());
        //进行数据注册
        Information.InformationBuilder builder = Information.builder().instanceId(msg.getInstanceUuid()).instanceName(msg.getInstanceName()).version(msg.getInstanceVersion());
        Map<String, String> osInfo = msg.getOsInfo();
        builder.appId(id).startDate(new Date()).progress(osInfo.get("process_no")).host(osInfo.get("host_name")).ip(osInfo.get("ipv4")).language(osInfo.get("language")).os(osInfo.get("os_name"));

        QueryWrapper<Information> wrapper = new QueryWrapper<>();
        wrapper.eq("app_id", id);
        Integer count = formationMapper.selectCount(wrapper);
        if (count != null || count == 0) {
            formationMapper.insert(builder.build());
            logger.info("应用实例：id {} ,channel :{}注册成功！", id, ctx.channel());
        }

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
