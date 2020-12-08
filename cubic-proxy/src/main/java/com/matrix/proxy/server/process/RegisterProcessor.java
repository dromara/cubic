
package com.matrix.proxy.server.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.matrix.proxy.entity.Information;
import com.matrix.proxy.mapper.InformationMapper;
import com.matrix.proxy.module.Message;
import com.cubic.proxy.common.server.ServerConnectionStore;
import com.cubic.proxy.common.constant.ResponseCode;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author luqiang
 */
@Service
public class RegisterProcessor extends DefaultMessageProcess   {

    private static final Logger logger = LoggerFactory.getLogger(RegisterProcessor.class);

    @Resource
    private ServerConnectionStore connectionStore;

    @Resource
    private InformationMapper informationMapper;


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

        insertJvmInfo(msg, id);

        logger.info("应用实例：id {} ,channel :{}注册成功！", id, ctx.channel());

        ctx.channel().writeAndFlush(initRegisterResponse(id));
    }

    private void insertJvmInfo(Message msg, String id) {

        QueryWrapper<Information> wrapper = new QueryWrapper<>();
        wrapper.eq("app_id", id);
        Integer count = informationMapper.selectCount(wrapper);
        if (count != 0) {
            return;
        }



        Information.InformationBuilder builder = Information.builder().instanceId(msg.getInstanceUuid()).
                instanceName(msg.getInstanceName()).version(msg.getInstanceVersion());
        Map<String, String> osInfo = msg.getOsInfo();
        String info = osInfo.get("jvm_info");

        JSONObject jvmInfo = JSON.parseObject(info);
        builder.appId(id).startDate(new Date()).progress(osInfo.get("process_no")).host(osInfo.get("host_name"))
                .ip(osInfo.get("ipv4")).language(osInfo.get("language")).os(osInfo.get("os_name"))
                .jdkVersion(jvmInfo.getString("version")).jdkDir(jvmInfo.getString("jvmHome")).userDir(jvmInfo.getString("userDir"));


        JSONArray jsonArray = jvmInfo.getJSONArray("jarFileList");

        Integer initMemory = jvmInfo.getInteger("initMemory");
        Integer maxMemory = jvmInfo.getInteger("maxMemory");
        Integer processorNum = jvmInfo.getInteger("processorNum");
        JSONArray inputArguments = jvmInfo.getJSONArray("inputArguments");
        String osArch = jvmInfo.getString("osArch");
        String osVersion = jvmInfo.getString("osVersion");
        builder.initMemory(initMemory == null ? 0 : initMemory / 1024 / 1024).maxMemory(maxMemory == null ? 0 : maxMemory / 1024 / 1024).
                processorNum(processorNum).arguments(inputArguments == null ? null : inputArguments.toJSONString()).os(osArch).osVersion(osVersion).jars(jsonArray.toJSONString());

        Information information = builder.build();
        informationMapper.insert(information);

    }

    private String initRegisterResponse(String id) {

        Map<String, Object> result = new HashMap<>(16);
        result.put("code", ResponseCode.REGIST.getCode());
        result.put("id", id);
        result.put("command", true);
        return JSON.toJSONString(result);
    }
}
