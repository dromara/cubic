package com.matrix.proxy.controller;

import com.matrix.proxy.auth.module.DataResult;
import com.matrix.proxy.service.AppDataService;
import com.matrix.proxy.vo.InstanceInfoVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AppController
 * @Author QIANGLU
 * @Date 2020/6/22 6:44 下午
 * @Version 1.0
 */
@RestController
@RequestMapping("/app")
@CrossOrigin
public class AppController {

    @Resource
    private AppDataService appDataService;

    @RequestMapping("/getList")
    public DataResult getList(@RequestParam(defaultValue = "", required = false) String date) {
        Map data = appDataService.getAppList(date);
        return DataResult.success("OK", data);

    }

    @RequestMapping("/getInstanceInfo")
    public DataResult getInstanceInfo(String appId) {
        if(!StringUtils.hasText(appId)){
            return DataResult.success();
        }
        InstanceInfoVo vo = appDataService.getInstanceInfo(appId);
        return DataResult.success("OK", vo);

    }

    @RequestMapping("/getInstanceNames")
    public DataResult getInstanceNames(String name) {
        if(!StringUtils.hasText(name)){
            return DataResult.success();
        }
        List list = appDataService.getInstanceNames(name);
        return DataResult.success("OK", list);

    }
}
