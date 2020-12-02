package com.matrix.proxy.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ClassName WebSevurityConfig
 * @Author QIANGLU
 * @Date 2020/6/1 5:37 下午
 * @Version 1.0
 */
//@Configuration
public class ProxyWebConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .maxAge(16800)
                .allowedHeaders("*")
                .allowCredentials(true);
    }


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(getExcludePathPatterns());
    }

    /**
     * 需要用户和服务认证判断的路径
     *
     * @return
     */
    private String[] getExcludePathPatterns() {
        String[] urls = {
                "/js/**",
                "/css/**",
                "/fonts/**",
                "/img/**",
                "/index.html",
                "/favicon.ico"

        };
        return urls;
    }
}
