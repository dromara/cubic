package com.matrix.proxy.auth.filter;

import com.matrix.proxy.auth.jwt.GovernJwtAuthenticationToken;
import com.matrix.proxy.auth.service.UserService;
import com.matrix.proxy.auth.utils.JwtDetail;
import com.matrix.proxy.auth.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName JwtTokenFilter
 * @Author QIANGLU
 * @Date 2020/11/18 2:22 下午
 * @Version 1.0
 */
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String HEADER_STRING = "Authorization";

    private final UserService userService;

    public JwtTokenFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(HEADER_STRING);

        log.info("JwtTokenFilter 拦截请求准备验证token :{}", token);
        if (StringUtils.isNotEmpty(token)) {
            try {
                JwtDetail detail = JwtTokenUtil.getInfoFromToken(token);
                Claims claims = detail.getClaims();
                String username = claims.getSubject();
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (username.equals(userDetails.getUsername()) && !detail.isExpired()) {
                    GovernJwtAuthenticationToken tk = new GovernJwtAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(tk);
                }

            } catch (Exception e) {
                log.warn("checkAuthentication filter token fail，{}", e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }

}
