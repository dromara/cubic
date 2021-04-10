package com.matrix.proxy.controller;

import com.matrix.proxy.service.JvmDataService;
import com.matrix.proxy.vo.ThreadPoolQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用jvm相关接口
 *
 * @author zhanghao
 * @date 2021/4/89:35 上午
 */
@CrossOrigin
@RestController
@RequestMapping("/jvm")
public class JvmController {
    @Autowired
    private JvmDataService jvmDataService;

    @RequestMapping("/threadpool/page")
    public Object threadPoolPage(@RequestBody ThreadPoolQuery query) {
        return jvmDataService.threadPoolDataPage(query);
    }
}
