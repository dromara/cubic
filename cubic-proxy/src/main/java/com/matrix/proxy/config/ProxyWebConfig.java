package com.matrix.proxy.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 如果开启此配置 MVC默认自动配置将失效，所有特性需要自己进行控制
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
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 的路由拦截器
        registry.addInterceptor(new SaRouteInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/system/login");
    }


}
