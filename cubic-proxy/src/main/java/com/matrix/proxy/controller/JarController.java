package com.matrix.proxy.controller;


import com.matrix.proxy.service.JarService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName JarController
 * @Author 李家山竹
 * @Date 2021/4/23 9:16 下午
 * @Version 1.0
 */
@RestController
@RequestMapping("/jar")
@CrossOrigin
public class JarController {

    @Resource
    private JarService jarService;
    /**
     * 获取应用JAR列表信息
     * @return
     */
    @RequestMapping("/getList")
    public Map<Object, List<Object>> getJarList(String appId) {
        return jarService.getJarList(appId);

    }
}
