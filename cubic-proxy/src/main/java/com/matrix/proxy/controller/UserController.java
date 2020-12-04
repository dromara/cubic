package com.matrix.proxy.controller;

import com.matrix.proxy.auth.module.DataResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    public DataResult info(){

        Map<String,Object> info = new HashMap<>();
        info.put("roles",new String[]{"admin"});
        info.put("introduction"," am a super administrator");
        info.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        info.put("name","Super Admin");

//        ResponseBody<Map> responseBody = new ResponseBody<>(200,info);
//        return JSON.toJSONString(responseBody);

        return DataResult.ok("OK",info);
    }
}
