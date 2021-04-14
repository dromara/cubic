package com.matrix.proxy.constant;

/**
 * 处理线程栈信息展示
 *
 * @Author qinqixuan
 * @Date 2021/04/06
 * @Version V1.0
 **/
public interface MonitorCacheType {

	/**
	 * 全部应用对应信息
	 */
	String MATRIX_MONITOR_ALL = "monitor:all";

	/**
	 * 应用的吞吐数据
	 */
	String MATRIX_MONITOR_CPM = "monitor:cpm:";

	/**
	 * 应用Endpoint的吞吐数据
	 */
	String MATRIX_MONITOR_ENDPOINT = "monitor:endpoint:";


	/**
	 * 总耗时
	 */
	String MATRIX_MONITOR_DURATION = "monitor:duration:";
	/**
	 * 最大耗时
	 */
	String MATRIX_MONITOR_MAX_DURATION = "monitor:maxduration:";

	/**
	 * 最小耗时
	 */
	String MATRIX_MONITOR_MIN_DURATION = "monitor:minduration:";

	/**
	 * 错误率
	 */
	String MATRIX_MONITOR_ERROR = "monitor:error:";


	/**
	 * 每个应用包含的endpoint对应的id
	 */
	String MATRIX_MONITOR_ENDPOINT_IDS = "monitor:spids:";

	/**
	 * heartdata
	 */
	String MATRIX_MONITOR_HEART = "monitor:heart:";


	/**
	 * 应用JVM THREAD 信息
	 */
	String MATRIX_MONITOR_JVM_THREADS = "monitor:jvmthread:";

	/**
	 * 应用JVM THREAD 基础基础信息
	 */
	String MATRIX_MONITOR_JVM_THREAD_BASE = "monitor:threadbase:";

	/**
	 * 应用JVM THREAD 基础基础信息
	 */
	String MATRIX_MONITOR_JVM_THREAD_IDS = "monitor:threadid:";

	/**
	 * 应用JVM THREAD 状态基础信息
	 */
	String MATRIX_MONITOR_JVM_THREAD_STATE = "monitor:threadstate:";

	/**
	 * 应用JVM THREAD数据信息
	 */
	String MATRIX_MONITOR_JVM_THREAD_DATA = "monitor_threaddata/";

	/**
	 * 应用JVM THREAD POOL数据信息
	 */
	String MATRIX_MONITOR_JVM_THREAD_POOLS = "monitor:threadpool:";
}
