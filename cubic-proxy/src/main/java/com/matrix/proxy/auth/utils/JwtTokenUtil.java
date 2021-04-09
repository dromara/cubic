package com.matrix.proxy.auth.utils;

import com.matrix.proxy.auth.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author luqiang
 */
@Component
@Slf4j
public class JwtTokenUtil {

    private static JwtProperties jwtProperties;

    public JwtTokenUtil(JwtProperties jwtProperties) {
        JwtTokenUtil.jwtProperties = jwtProperties;
    }


    public static JwtProperties getJwtProperties() {
        return jwtProperties;
    }

    public static String generateToken(JwtDetail jwtDetail) {
        jwtDetail.setSign(jwtProperties.getSign());
        return JwtHelper.generateToken(jwtDetail);
    }

    public static JwtDetail getInfoFromToken(String token) {

        if (token.startsWith("Bearer")) {
            token = token.replace("Bearer ", "");
        }

        JwtDetail.JwtDetailBuilder builder = JwtDetail.builder();
        try {
            Jws<Claims> claimsJws = JwtHelper.parserToken(token, jwtProperties.getSign());
            Claims body = claimsJws.getBody();
            builder.claims(body);
        } catch (ExpiredJwtException e) {
            log.warn("token :{} is isExpired", token);
            builder.isExpired(true);
        }


        return builder.build();
    }


}
