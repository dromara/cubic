package com.matrix.proxy.controller;

import com.alibaba.fastjson.JSON;
import com.matrix.proxy.db.entity.BasicInformation;
import com.matrix.proxy.module.ResponseBody;
import com.matrix.proxy.service.AppDataService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
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
    public String getList(){
        List<BasicInformation> list = appDataService.getAppList();
        Map<String,Object> datas = new HashMap<>();
        datas.put("items",list);
        datas.put("total",list.size());
        ResponseBody responseBody = new ResponseBody(0,datas);
        return JSON.toJSONString(responseBody);
    }
}
