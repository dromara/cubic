package com.matrix.proxy.service;

import com.matrix.proxy.vo.InstanceInfoVo;

import java.util.List;
import java.util.Map;

/**
 * 用于处理基础数据
 *
 * @ClassName BasicDataService
 * @Author QIANGLU
 * @Date 2020/6/10 11:28 上午
 * @Version 1.0
 */
public interface AppDataService {

    /**
     * 获取应用实例列表
     *
     * @return
     */
    Map getAppList(String date);


    /**
     * 获取应用实例详细信息
     *
     * @param intanceUid
     * @return
     */
    InstanceInfoVo getInstanceInfo(String intanceUid);


    /**
     * 根据应用名称获取实例列表
     *
     * @param name
     * @return
     */
    List<String> getInstanceNames(String name);
}
