package com.matrix.proxy.config;

import com.matrix.proxy.auth.filter.JwtTokenFilter;
import com.matrix.proxy.auth.handlers.ProxyAuthFailHandler;
import com.matrix.proxy.auth.handlers.ProxyAuthSuccessHandler;
import com.matrix.proxy.auth.service.UserService;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @ClassName MatrixSecurityConfig
 * @Author QIANGLU
 * @Date 2020/11/17 4:09 下午
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
public class ProxySecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserService userService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/system/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/index.html")
                .defaultSuccessUrl("/")
                .successHandler(new ProxyAuthSuccessHandler())
                .failureHandler(proxyAuthFailHandler())
                .usernameParameter("username")
                .passwordParameter("password").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .cors();


        //使用自定义的 Token过滤器 验证请求的Token是否合法
        http.addFilterBefore(new JwtTokenFilter(userService), UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

    @Bean
    public ProxyAuthFailHandler proxyAuthFailHandler() {
        return new ProxyAuthFailHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
        auth.eraseCredentials(false);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 忽略拦截url或静态资源文件夹 - web.ignoring(): 会直接过滤该url - 将不会经过Spring Security过滤器链
     * http.permitAll(): 不会绕开springsecurity验证，相当于是允许该路径通过
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web)   {
        web.ignoring().antMatchers(HttpMethod.GET,
                "/*.html",
                "/favicon.ico",
                "/**/*.png",
                "/**/*.ttf",
                "/**/*.css",
                "/**/*.js");
    }
}
