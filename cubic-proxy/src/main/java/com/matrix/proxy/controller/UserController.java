package com.matrix.proxy.controller;

import com.alibaba.fastjson.JSON;
import com.matrix.proxy.module.ResponseBody;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @RequestMapping("/info")
    public String info(){
        Map<String,Object> info = new HashMap<>();
        info.put("roles",new String[]{"admin"});
        info.put("introduction"," am a super administrator");
        info.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        info.put("name","Super Admin");

        ResponseBody<Map> responseBody = new ResponseBody<>(20000,info);
        return JSON.toJSONString(responseBody);
    }
}
