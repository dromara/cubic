package com.matrix.proxy.module;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 记录异常日志信息
 *
 * @Author qinqixuan
 * @Date 2021/04/06
 * @Version V1.0
 **/
@Data
@Builder
public class ThreadPoolLog {

	@Id
	private String id;


	private String name;

	/**
	 * 服务id
	 */
	private String serviceId;


	/**
	 *uuid
	 */
	private String uuid;

	/**
	 * 详情数据
	 */
	private String threadPools;

	/**
	 * easy
	 */
	private String details;


	private Date clearUpDate;


	/**
	 * 创建日期
	 */
	private Date createDate;


	@Tolerate
	ThreadPoolLog(){}
}
