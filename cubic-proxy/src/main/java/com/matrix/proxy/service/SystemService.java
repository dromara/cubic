package com.matrix.proxy.service;


import com.matrix.proxy.module.AuthDetail;

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


}
