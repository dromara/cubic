package com.matrix.proxy.auth.service;


import com.matrix.proxy.auth.login.AuthDetail;

/**
 * @author luqiang
 */
public interface SystemService {

    /**
     * 验证登录
     *
     * @param loginDetail
     * @return
     */
    String login(AuthDetail loginDetail);


    /**
     * 获取token
     *
     * @param authDetail
     * @return
     */
    String getToken(AuthDetail authDetail);
}
