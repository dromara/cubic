package com.matrix.proxy.controller;

import com.matrix.proxy.auth.module.DataResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luqiang
 */
@RestController
@RequestMapping("/system")
@CrossOrigin
public class TestController {


    /**
     * 测试统一返回
     *
     * @return
     */
    @RequestMapping("/test")
    public String test() {

        return "我是测试的";
    }

    @RequestMapping("/test2")
    public DataResult test2() {

        Map<String, Object> info = new HashMap<>();
        info.put("roles", new String[]{"admin"});
        info.put("introduction", " am a super administrator");
        info.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        info.put("name", "Super Admin");

        return DataResult.success("OK", info);
    }

    /**
     * 测试异常包装
     *
     * @return
     */
    @RequestMapping("/test1")
    public String test1() {


        int i = 0;
        int x = 3 / i;
        return "我是测试的";
    }
}
