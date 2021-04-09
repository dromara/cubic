package com.matrix.proxy.controller;

import com.matrix.proxy.auth.login.AuthDetail;
import com.matrix.proxy.auth.module.DataResult;
import com.matrix.proxy.auth.service.SystemService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author luqiang
 */
@RestController
@RequestMapping("/system")
@CrossOrigin
public class LoginController {

    @Resource
    private SystemService systemService;


    @RequestMapping("/logout")
    public String logout() {
        return "";
    }


    @RequestMapping("/login")
    public String login(@RequestBody AuthDetail loginDetail) {

        return systemService.login(loginDetail);
    }


    @RequestMapping("/loginFail")
    public DataResult loginFail() {
        return DataResult.fail("fail");
    }
}
