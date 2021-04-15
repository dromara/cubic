package com.matrix.proxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.cubic.proxy.common.constant.CommandCode;
import com.matrix.proxy.service.JdkCommandService;
import com.matrix.proxy.service.JvmDataService;
import com.matrix.proxy.vo.ThreadPoolCommandVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 应用jvm相关接口
 *
 * @author zhanghao
 * @date 2021/4/89:35 上午
 */
@CrossOrigin
@RestController
@RequestMapping("/jvm")
public class JvmController {
    @Resource
    private JvmDataService jvmDataService;
    @Resource
    private JdkCommandService jdkCommandService;

    /**
     * 线程池列表
     *
     * @param instanceUid
     * @return
     */
    @RequestMapping("/threadPoolList")
    public Map<String, Object> threadPoolPage(@RequestParam(required = false) String instanceUid, @RequestParam String dayTime) {

        if (StringUtils.isEmpty(instanceUid)) {
            return new HashMap<>();
        }
        return jvmDataService.threadPoolDataPage(instanceUid, dayTime);
    }


    /**
     * 线程池命令下发
     *
     * @param commandVo
     * @return
     */
    @RequestMapping("/threadpool/command")
    public Object threadPoolCMD(@RequestBody ThreadPoolCommandVo commandVo) {
        JSONObject command = new JSONObject();
        command.put("key", commandVo.getKey());
        command.put("name", commandVo.getName());
        command.put("arg", commandVo.getArg());
        return jdkCommandService.exeCommand(commandVo.getInstanceUuid(), CommandCode.JVM_THREAD_POOL.getCode(), command.toJSONString());
    }
}
