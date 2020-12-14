package com.cubic.agent.cmd.jvm.thread;

import com.cubic.agent.cmd.jstack.CommandExecutor;
import com.cubic.agent.module.ThreadDetails;
import com.cubic.agent.utils.GzipUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * @Description: TODO
 * @Author qinqixuan
 * @Date 2020/12/08
 * @Version V1.0
 **/
public enum ThreadProvider {

	INSTANCE;

	private final ThreadMXBean memoryMXBean;

	ThreadProvider() {
		this.memoryMXBean = ManagementFactory.getThreadMXBean();
	}

	/**
	 * 获取线程基础信息
	 *
	 * @return 线程信息
	 */
	public ThreadDetails getThreadDetails() {
		ThreadDetails.Builder builder = ThreadDetails.newBuilder();
		builder.setCount(memoryMXBean.getThreadCount());
		builder.setPeak(memoryMXBean.getPeakThreadCount());
		builder.setTotalStarted(memoryMXBean.getTotalStartedThreadCount());
		builder.setDaemon(memoryMXBean.getDaemonThreadCount());
		return builder.build();
	}

	/**
	 * 获取线程栈完整消息
	 *
	 * @return
	 */
	public String getThreadDump() {
		CommandExecutor executor = new CommandExecutor();
		String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
		String data = executor.execute(pid, "threadDump");
		return GzipUtils.compress(data);
	}
}
