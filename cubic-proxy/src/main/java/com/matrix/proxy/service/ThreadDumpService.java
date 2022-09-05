package com.matrix.proxy.service;

import com.cubic.proxy.common.module.DataResult;
import com.matrix.proxy.vo.ThreadDumpVo;

public interface ThreadDumpService {

    /**
     * 获取应用实例线程栈信息
     *
     * @param time
     * @param appId
     * @return
     */
    String getThreadDumpByAppId(String appId, String time);

    /**
     * 获取应用线程栈历史信息
     *
     * @return
     */
    DataResult getHistoryByAppId(ThreadDumpVo dumpVo);

    /**
     * 解析线程栈数据
     *
     * @param dumpId 线程栈ID
     * @return
     */
    String analyzer(Long dumpId);
}
