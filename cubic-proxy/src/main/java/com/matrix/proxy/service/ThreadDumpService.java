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
//
//	/**
//	 * 根据 uid查询 threadpool
//	 *
//	 * @param uid
//	 * @param pageIndex
//	 * @param pageSize
//	 * @param startDate
//	 * @param endDate
//	 * @return
//	 */
//	Page<ThreadPoolLog> getThreadPoolByUid(String uid, int pageIndex, int pageSize, String startDate, String endDate);
}
