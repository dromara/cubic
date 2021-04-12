package com.matrix.proxy.service;

import com.matrix.proxy.module.ThreadPoolLog;
import org.springframework.data.domain.Page;

public interface ThreadDumpService {

	/**
	 * 获取应用实例线程栈信息
	 *
	 * @param time
	 * @param appId
	 * @return
	 */
	String getThreadDumpByAppId(String appId, String time );
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
