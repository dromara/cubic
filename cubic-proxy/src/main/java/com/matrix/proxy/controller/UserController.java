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
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @RequestMapping("/info")
    public Map<String, Object> info() {

        Map<String, Object> info = new HashMap<>();
        info.put("roles", new String[]{"admin"});
        info.put("introduction", " am a super administrator");
        info.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        info.put("name", "Super Admin");

        return info;
    }


}
