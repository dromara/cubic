/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.cubic.agent.core.cmd.jvm.thread;

import com.cubic.agent.core.cmd.jstack.CommandExecutor;
import com.cubic.agent.core.module.ThreadDetails;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * jvm线程栈采集
 *
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
		return executor.execute(pid, "threadDump");
	}
}
