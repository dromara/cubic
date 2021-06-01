package com.matrix.proxy.auth.service;

import com.matrix.proxy.auth.entity.CubicUser;
import com.matrix.proxy.auth.mapper.CubicUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName UserServiceImpl
 * @Author QIANGLU
 * @Date 2020/11/18 2:25 下午
 * @Version 1.0
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private CubicUserMapper cubicUserMapper;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CubicUser u = cubicUserMapper.selectByUsername(username);
        if(u == null){
           throw new UsernameNotFoundException("username not found");
        }
        UserDetails user = org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getSecret())
                .roles("USER")
                .build();

        return user;
    }


}
