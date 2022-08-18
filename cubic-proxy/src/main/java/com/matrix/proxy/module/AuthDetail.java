package com.matrix.proxy.module;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

/**
 * @ClassName LoginDetail
 * @Author QIANGLU
 * @Date 2020/11/25 11:29
 * @Version 1.0
 */
@Data
@Builder
public class AuthDetail implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String captcha;


    @Tolerate
    public AuthDetail(){}

}
