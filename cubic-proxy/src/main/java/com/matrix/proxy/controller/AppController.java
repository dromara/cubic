package com.matrix.proxy.controller;

import com.alibaba.fastjson.JSON;
import com.matrix.proxy.module.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/getList")
    public String getList(){

        ResponseBody<List> responseBody = new ResponseBody<>(20000,new LinkedList<>());
        return JSON.toJSONString(responseBody);
    }
}
