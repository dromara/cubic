package com.cubic.agent.cmd.jvm.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ThreadPoolProvider {

	INSTANCE;

	private static final Logger logger = LoggerFactory.getLogger(ThreadPoolProvider.class);


	public String getDubboThreadPool() {
		return "";
	}
}
