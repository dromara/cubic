package com.matrix.proxy.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cubic.proxy.common.enums.StatusEnum;
import com.cubic.proxy.common.exception.CubicServiceException;
import com.matrix.proxy.mapper.CubicUserMapper;
import com.matrix.proxy.module.AuthDetail;
import com.matrix.proxy.module.CubicUser;
import lombok.extern.slf4j.Slf4j;
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
public class SystemServiceImpl implements SystemService {


    @Resource
    private CubicUserMapper cubicUserMapper;

    @Override
    public String login(AuthDetail loginDetail) {

        CubicUser user = cubicUserMapper.selectOne(new QueryWrapper<CubicUser>().eq("username", loginDetail.getUsername()).eq("status", StatusEnum.YES.getCode()));
        if (user == null || !user.getSecret().equals(loginDetail.getPassword()) || !"123456".equals(loginDetail.getPassword())) {
            throw new CubicServiceException("登录失败，账号密码不匹配");
        }
        StpUtil.login(user.getId());

        return StpUtil.getLoginIdAsString();
    }


}
