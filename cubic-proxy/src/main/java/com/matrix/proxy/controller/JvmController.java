package com.matrix.proxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cubic.proxy.common.constant.CommandCode;
import com.matrix.proxy.service.JdkCommandService;
import com.matrix.proxy.service.JvmDataService;
import com.matrix.proxy.vo.ThreadPoolCommandVo;
import com.matrix.proxy.vo.ThreadPoolQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
     * @param instanceName
     * @return
     */
    @RequestMapping("/threadPoolList")
    public Page threadPoolPage(@RequestParam String instanceName) {
        return jvmDataService.threadPoolDataPage(instanceName);
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
