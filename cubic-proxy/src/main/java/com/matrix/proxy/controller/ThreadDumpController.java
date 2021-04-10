package com.matrix.proxy.controller;

import com.matrix.proxy.service.ThreadDumpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 应用数据监控
 *
 * @Author qinqixuan
 * @Date 2021/04/06
 * @Version V1.0
 **/
@RestController
@RequestMapping("/dump")
@CrossOrigin(maxAge = 3600)
public class ThreadDumpController {

    private static Logger log = LoggerFactory.getLogger(ThreadDumpController.class);

    @Resource
    private ThreadDumpService threadDumpService;


    /**
     * 获取某一分钟线程详情
     *
     * @param appId
     * @param dayTime     yyyyMmddHHmmss
     * @return
     */
    @RequestMapping(value = "/getThreadDumpByAppId")
    public String getThreadsDetailsByMin(@RequestParam String appId,   @RequestParam(required = false) String dayTime ) {
        return  threadDumpService.getThreadDumpByAppId(appId,dayTime);

    }
}
