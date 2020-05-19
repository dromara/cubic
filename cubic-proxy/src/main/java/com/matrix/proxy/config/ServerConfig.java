package com.matrix.proxy.config;

import com.matrix.proxy.server.MatrixNettyServer;
import com.matrix.proxy.server.handler.MessageHandler;
import com.matrix.proxy.server.handler.ServerMessgaeProcess;
import com.matrix.proxy.webscoket.MatrixNettyWebServer;
import com.matrix.proxy.webscoket.WebRequestHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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


    @Bean(initMethod = "start")
    public MatrixNettyServer nettyServerForAgent(ServerProperties serverProperties, List<ServerMessgaeProcess> processors) {
        MessageHandler messageHandler = new MessageHandler(processors);
        return new MatrixNettyServer(serverProperties.getAgentPort(), messageHandler);
    }


    @Bean(initMethod = "start")
    public MatrixNettyWebServer matrixNettyWebServer(ServerProperties serverProperties, WebRequestHandler webRequestHandler) {
        return new MatrixNettyWebServer(serverProperties.getAgentPort() + 1, webRequestHandler);
    }
}
