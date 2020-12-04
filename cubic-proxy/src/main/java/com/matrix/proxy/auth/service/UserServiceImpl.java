package com.matrix.proxy.auth.service;

import com.matrix.proxy.auth.entity.User;
import com.matrix.proxy.auth.mapper.UserMapper;
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
    private UserMapper userMapper;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User u = userMapper.selectByUsername(username);
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


//    public static void main(String[] args) {
//        System.out.println(new BCryptPasswordEncoder().encode("123456"));
//    }



}
