package com.matrix.proxy.module;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 记录线程某一时刻信息
 *
 * @Author qinqixuan
 * @Date 2021/04/06
 * @Version V1.0
 **/
@Data
@Builder
public class ThreadDetailsLog {

	@Id
	private String id;

	/**
	 * 预留应用唯一标示
	 */
	private String name;

	/**
	 * 服务id
	 */
	private String serviceId;

	private String timeBucket;
	/**
	 * 实例uuid
	 */
	private String uuid;

	/**
	 * 线程id
	 */
	private String threadId;

	/**
	 * 线程栈
	 */
	private String stack;

	/**
	 * 线程名称
	 */
	private String threadName;

	/**
	 * 线程状态
	 */
	private String threadState;

	/**
	 * cpu 占用时间
	 */
	private String cpuTime;

	private Date clearUpDate;


	/**
	 * 创建日期
	 */
	private Date createDate;


	@Tolerate
	ThreadDetailsLog() {
	}
}
