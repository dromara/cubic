package com.matrix.proxy.auth.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName ProxyAuthSuccessHandler
 * @Author QIANGLU
 * @Date 2020/12/4 10:34
 * @Version 1.0
 */
@Slf4j
public class ProxyAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功执行ProxyAuthSuccessHandler ");
    }
}
