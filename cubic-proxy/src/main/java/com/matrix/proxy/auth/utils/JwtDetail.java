package com.matrix.proxy.auth.utils;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

/**
 * @ClassName JwtDetail
 * @Author QIANGLU
 * @Date 2020/11/18 5:03 下午
 * @Version 1.0
 */
@Data
@Builder
public class JwtDetail {

    /**
     * 或username
     */
    private String username;


    private Claims claims;

    /**
     * 过期时间
     */
    private Date exDate;


    /**
     * token是否过期
     */
    private boolean isExpired;

    /**
     * 签名
     */
    private String sign;

    @Tolerate
    JwtDetail() {
    }
}
