package com.matrix.agent.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author qianglu
 */
@SpringBootApplication
public class AgentDemoStartup extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AgentDemoStartup.class);
    }

    public static void main(String[] args) {

//
        SpringApplication.run(AgentDemoStartup.class, args);

    }
}
