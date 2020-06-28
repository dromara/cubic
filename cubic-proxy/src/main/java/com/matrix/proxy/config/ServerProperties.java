package com.matrix.proxy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName ServerProperties
 * @Author QIANGLU
 * @Date 2020/3/19 3:32 下午
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "matrix.server")
@Component
public class ServerProperties {
    @Getter
    @Setter
    private int agentPort = 10080;
}
