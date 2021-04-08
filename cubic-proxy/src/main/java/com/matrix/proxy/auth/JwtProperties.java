package com.matrix.proxy.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName JwtProperties
 * @Author QIANGLU
 * @Date 2020/11/18 6:32 下午
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtProperties {

    private String tokenHeader;

    private Integer expire;

    private String sign;

    private String rsaSecret;

    private byte[] privateKey;

    private  byte[] publicKey;

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(byte[] privateKey) {
        this.privateKey = privateKey;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    public String getRsaSecret() {
        return rsaSecret;
    }

    public void setRsaSecret(String rsaSecret) {

        this.rsaSecret = rsaSecret;
    }


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
