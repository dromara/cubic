package com.matrix.proxy.auth.utils;


import com.matrix.proxy.util.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author luqiang
 */
public class JwtHelper {

    /**
     * 密钥加密token
     *
     * @param jwtDetail
     * @return
     * @throws Exception
     */
    public static String generateToken(JwtDetail jwtDetail) {
        String compactJws = Jwts.builder()
                .setSubject(jwtDetail.getUsername()).addClaims(jwtDetail.getClaims())
                .setExpiration(jwtDetail.getExDate())
                .signWith(SignatureAlgorithm.HS256, jwtDetail.getSign())
                .compact();
        return compactJws;
    }

    /**
     * 密钥加密token
     *
     * @param jwtDetail
     * @param priKey
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateRsaToken(JwtDetail jwtDetail, byte[] priKey, int expire) {
        String compactJws = Jwts.builder()
                .setSubject(jwtDetail.getUsername()).addClaims(jwtDetail.getClaims())
                .setExpiration(DateUtils.localDateToDate(LocalDateTime.now().plusMinutes(expire).toLocalDate()))
                .signWith(SignatureAlgorithm.RS256, RsaKeyHelper.getPrivateKey(priKey))
                .compact();
        return compactJws;
    }


    /**
     * 公钥解析token
     *
     * @param token
     * @return
     */
    public static Jws<Claims> parserToken(String token, String sign) {

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(sign).parseClaimsJws(token);

        return claimsJws;
    }


    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserRsaToken(String token, byte[] pubKey) {

        Jws<Claims> claimsJws = null;
        Optional<PublicKey> optional = RsaKeyHelper.getPublicKey(pubKey);
        if (optional.isPresent()) {
            claimsJws = Jwts.parser().setSigningKey(optional.get()).parseClaimsJws(token);

        }
        return claimsJws;
    }



}
