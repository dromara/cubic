package com.matrix.proxy.config;

import com.cubic.proxy.common.server.ServerConnectionStore;
import com.cubic.proxy.common.webserver.WebConnectionStore;
import com.cubic.proxy.common.webserver.WebProcess;
import com.cubic.proxy.websocket.DefaultWebConnectionStore;
import com.cubic.proxy.websocket.MatrixNettyWebServer;
import com.cubic.proxy.websocket.WebRequestHandler;
import com.cubic.proxy.websocket.process.SearchWebProcess;
import com.matrix.proxy.server.MatrixNettyServer;
import com.cubic.proxy.common.handler.MessageHandler;
import com.cubic.proxy.common.handler.ServerMessageProcess;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ServerConfig
 * @Author QIANGLU
 * @Date 2020/3/19 3:31 下午
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(value = ServerProperties.class)
public class ServerConfig {

    @Resource
    private List<WebProcess> webProcesses;

    @Bean(initMethod = "start")
    public MatrixNettyServer nettyServerForAgent(ServerProperties serverProperties, List<ServerMessageProcess> processors) {
        MessageHandler messageHandler = new MessageHandler(processors);
        return new MatrixNettyServer(serverProperties.getAgentPort(), messageHandler);
    }


    @Bean(initMethod = "start")
    public MatrixNettyWebServer matrixNettyWebServer(ServerProperties serverProperties, WebRequestHandler webRequestHandler) {
        return new MatrixNettyWebServer(serverProperties.getAgentPort() + 1, webRequestHandler);
    }

    @Bean
    public WebRequestHandler webRequestHandler(WebConnectionStore webConnectionStore, ServerConnectionStore serverConnectionStore){
        return new WebRequestHandler(webConnectionStore,serverConnectionStore,webProcesses);
    }


    @Bean
    public DefaultWebConnectionStore defaultWebConnectionStore(){

        return new DefaultWebConnectionStore();
    }


    @Bean
    public SearchWebProcess searchWebProcess(){
        return new SearchWebProcess();
    }
}
