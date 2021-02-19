package com.matrix.proxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.matrix.proxy.entity.ThreadDump;

import java.util.Date;
import java.util.List;

/**
 * 线程栈信息数据库处理
 *
 * @Author qinqixuan
 * @Date 2020/12/16
 * @Version V1.0
 **/
public interface ThreadDumpMapper extends BaseMapper<ThreadDump> {


    /**
     * 根据名称查询app对应的线程栈信息
     *
     * @param instanceName
     * @return
     */
    List<ThreadDump> selectThreadDumpByName(String instanceName);

    /**
     * 根据时间维度查询app线程栈信息
     *
     * @param instanceName
     * @param date
     * @return
     */
    List<String> selectThreadDumpByTime(String instanceName, Date date);

    /**
     * 根据appId查询应用实例信息
     *
     * @param appId
     * @return
     */
    ThreadDump selectThreadDumpByAppId(String appId);
}
