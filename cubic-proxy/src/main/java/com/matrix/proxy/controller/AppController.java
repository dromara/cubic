package com.matrix.proxy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.matrix.proxy.db.entity.BasicInformation;
import com.matrix.proxy.module.ResponseBody;
import com.matrix.proxy.service.AppDataService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String getList(@RequestParam(defaultValue = "", required = false) String date){
        Map data = appDataService.getAppList(date);
        ResponseBody response = new ResponseBody(0,data);
        return JSONObject.toJSONString(response, SerializerFeature.WriteDateUseDateFormat);
    }
}
