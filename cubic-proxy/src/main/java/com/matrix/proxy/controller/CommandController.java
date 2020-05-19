package com.matrix.proxy.controller;

import com.matrix.proxy.service.JdkCommandService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author luqiang
 */
@RestController
@RequestMapping("/command")
public class CommandController {

    @Resource
    private JdkCommandService jdkCommandService;

    @ResponseBody
    @RequestMapping("/jdkCommand")
    public String jdkCommand(@RequestParam final String instanceUuid,@RequestParam Integer type,@RequestParam(required = false,defaultValue = "") String command) {
        return jdkCommandService.exeCommand(instanceUuid,type,command);
    }


}
