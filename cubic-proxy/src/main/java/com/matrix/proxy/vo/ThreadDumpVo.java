package com.matrix.proxy.vo;

import com.cubic.proxy.common.module.PageQuery;
import lombok.Data;

import java.util.Date;

/**
 * @Author qinqixuan
 * @Date 2020/12/16
 * @Version V1.0
 **/
@Data
public class ThreadDumpVo extends PageQuery {

	private Long id;

	/**
	 * 应用标识
	 */
	private String appId;

	/**
	 * 实例名称
	 */
	private String instanceName;

	/**
	 * 实例ID
	 */
	private String instanceId;

	/**
	 * 线程栈
	 */
	private String threadDump;

	/**
	 * 创建日期
	 */
	private Date createTime;

}
