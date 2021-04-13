package com.matrix.proxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.cubic.proxy.common.constant.CommandCode;
import com.matrix.proxy.service.JdkCommandService;
import com.matrix.proxy.service.JvmDataService;
import com.matrix.proxy.vo.ThreadPoolCommandVo;
import com.matrix.proxy.vo.ThreadPoolQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private JvmDataService jvmDataService;
    @Autowired
    private JdkCommandService jdkCommandService;

    /**
     * 线程池列表
     *
     * @param query
     * @return
     */
    @RequestMapping("/threadpool/page")
    public Object threadPoolPage(@RequestBody ThreadPoolQueryVo query) {
        return jvmDataService.threadPoolDataPage(query);
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
