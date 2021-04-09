package com.matrix.proxy.controller;

import com.cubic.proxy.common.server.ServerConnection;
import com.cubic.proxy.common.server.ServerConnectionStore;
import com.cubic.proxy.common.webserver.WebConnection;
import com.cubic.proxy.common.webserver.WebConnectionStore;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * 代理服务状态
 * @author luqiang
 */
@RestController
@RequestMapping("/proxy")
@CrossOrigin
@Slf4j
public class ProxyStatusController {

    @Resource
    private ServerConnectionStore serverConnectionStore;

    @Resource
    private WebConnectionStore webConnectionStore;


    @ResponseBody
    @RequestMapping("/getConnectMetric")
    public Map<String, ServerConnection> getMetric() {
        return serverConnectionStore.getAgentConnection();
    }

    @ResponseBody
    @RequestMapping("/getWebConnectionStore")
    public Map<Channel, WebConnection> getWebConnectionStore() {
        return webConnectionStore.getAllConnection();
    }

    @RequestMapping("/checkEAgentxist")
    public boolean checkEAgentxist(@RequestParam String agentId) {
         Optional<ServerConnection> connectionOption =  serverConnectionStore.getConnection(agentId);
         log.info("查询 agentId:{} 是否存在：{}",agentId,connectionOption.isPresent());
        return connectionOption.isPresent();
    }



}
