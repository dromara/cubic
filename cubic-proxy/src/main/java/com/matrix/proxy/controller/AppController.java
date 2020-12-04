package com.matrix.proxy.controller;

import com.matrix.proxy.auth.module.DataResult;
import com.matrix.proxy.service.AppDataService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    public DataResult getList(@RequestParam(defaultValue = "", required = false) String date){
        Map data = appDataService.getAppList(date);
        return DataResult.ok("OK",data);

    }
}
