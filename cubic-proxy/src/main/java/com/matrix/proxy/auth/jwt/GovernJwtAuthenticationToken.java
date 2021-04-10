package com.matrix.proxy.auth.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @ClassName GovernJwtAuthenticationToken
 * @Author QIANGLU
 * @Date 2020/11/25 14:16
 * @Version 1.0
 */
public class GovernJwtAuthenticationToken extends UsernamePasswordAuthenticationToken {



    public GovernJwtAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public GovernJwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
