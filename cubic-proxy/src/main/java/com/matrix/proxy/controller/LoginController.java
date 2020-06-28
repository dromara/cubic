package com.matrix.proxy.controller;

import com.alibaba.fastjson.JSON;
import com.matrix.proxy.module.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    @RequestMapping("/valid")
    public String valid(@RequestBody String data){
        ResponseBody<String> responseBody = new ResponseBody<>(0,"admin-token");
        return JSON.toJSONString(responseBody);
    }

    @RequestMapping("/logout")
    public String logout(){
        ResponseBody<String> responseBody = new ResponseBody<>(0,"success");
        return JSON.toJSONString(responseBody);
    }
}
