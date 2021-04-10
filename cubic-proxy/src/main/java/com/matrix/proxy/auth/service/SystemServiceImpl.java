package com.matrix.proxy.auth.service;

import com.matrix.proxy.auth.jwt.GovernJwtAuthenticationToken;
import com.matrix.proxy.auth.login.AuthDetail;
import com.matrix.proxy.auth.utils.JwtDetail;
import com.matrix.proxy.auth.utils.JwtTokenUtil;
import com.matrix.proxy.util.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName UserServiceImpl
 * @Author QIANGLU
 * @Date 2020/11/18 2:25 下午
 * @Version 1.0
 */
@Slf4j
@Service
public class SystemServiceImpl implements SystemService {


    private final ConcurrentHashMap<String, String> TOKEN_CACHE = new ConcurrentHashMap<>();
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public String login(AuthDetail loginDetail) {

        GovernJwtAuthenticationToken token = new GovernJwtAuthenticationToken(loginDetail.getUsername(), loginDetail.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return getToken(loginDetail);
    }

    /**
     * 获取token
     *
     * @param authDetail
     * @return
     */
    @Override
    public String getToken(AuthDetail authDetail) {

        try {

            String token = getCurrToken(authDetail);

            if (StringUtils.isNotEmpty(token)) {
                return token;
            }

            //生成新token
            Claims claims = new DefaultClaims();
            claims.put("username", authDetail.getUsername());
            Date expireDate = DateUtils.localDateToDate(LocalDate.now().plusDays(JwtTokenUtil.getJwtProperties().getExpire()));
            JwtDetail detail = JwtDetail.builder().exDate(expireDate).username(authDetail.getUsername()).claims(claims).build();

            String createToken = JwtTokenUtil.generateToken(detail);

            //更新存储
            updateCache(authDetail.getUsername(), createToken);

            return createToken;
        } catch (Exception e) {
            log.error("generateToken error", e);
        }

        return "";
    }


    private void updateCache(String username, String token) {
        TOKEN_CACHE.put(username, token);
    }


    /**
     * 线程内存和数据库进行token检索
     *
     * @param authDetail
     * @return
     */
    private String getCurrToken(AuthDetail authDetail) {

        if (authDetail == null || StringUtils.isEmpty(authDetail.getUsername())) {
            log.warn("get client token is fail, request param is null or id is null");
            return "";
        }

        String token = TOKEN_CACHE.get(authDetail.getUsername());

        if (StringUtils.isEmpty(token)) {
            return "";
        }
        JwtDetail detail = JwtTokenUtil.getInfoFromToken(token);

        if (detail != null && !detail.isExpired()) {
            return token;
        }
        log.info("未获取到 Username :{} 有效token", authDetail.getUsername());
        return "";

    }

}
