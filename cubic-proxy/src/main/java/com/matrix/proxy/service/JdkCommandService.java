package com.matrix.proxy.service;

/**
 * @ClassName JdkCommandService
 * @Author QIANGLU
 * @Date 2020/3/23 5:22 下午
 * @Version 1.0
 */
public interface JdkCommandService {

    /**
     * 获取应用当前参数信息
     * @param instanceUuid
     * @return
     */
    public String exeCommand(String instanceUuid,Integer type,String command);

}
