package com.matrix.proxy.controller;

import com.matrix.proxy.service.AppDataService;
import com.matrix.proxy.vo.InstanceInfoVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public Map getList(@RequestParam(defaultValue = "", required = false) String date) {
        return appDataService.getAppList(date);

    }

    @RequestMapping("/getInstanceInfo")
    public InstanceInfoVo getInstanceInfo(String appId) {
        if(!StringUtils.hasText(appId)){
            return InstanceInfoVo.builder().build();
        }
        return appDataService.getInstanceInfo(appId);

    }

    @RequestMapping("/getInstanceNames")
    public List getInstanceNames(String name) {
        if(!StringUtils.hasText(name)){
            return new ArrayList();
        }
        return appDataService.getInstanceNames(name);

    }


    /**
     * 获取应用列表信息
     * @return
     */
    @RequestMapping("/getAppNames")
    public List getAppNames() {

        return appDataService.getAppNames();

    }
}
