package com.matrix.proxy.auth.handlers;

import com.alibaba.fastjson.JSON;
import com.matrix.proxy.auth.module.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName ProxyAuthSuccessHandler
 * @Author QIANGLU
 * @Date 2020/12/4 10:34
 * @Version 1.0
 */
@Slf4j
public class ProxyAuthFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(DataResult.expired("登录失败:" + exception.getMessage())));
        } catch (Exception e) {
            log.error("ProxyAuthFailHandler process fail",e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
