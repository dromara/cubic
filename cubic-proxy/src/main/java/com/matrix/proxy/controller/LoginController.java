package com.matrix.proxy.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.matrix.proxy.module.AuthDetail;
import com.cubic.proxy.common.module.DataResult;
import com.matrix.proxy.service.SystemService;
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
    public void logout() {
        StpUtil.logout();
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
