package com.cubic.agent.core.conf;

import com.cubic.agent.core.dictionary.DictionaryUtil;

/**
 * @Author qinqixuan
 * @Date 2020/12/08
 * @Version V1.0
 **/
public class RemoteDownstreamConfig {
	public static class Agent {
		public volatile static int SERVICE_ID = DictionaryUtil.nullValue();

		public volatile static int SERVICE_INSTANCE_ID = DictionaryUtil.nullValue();

		public volatile static long INSTANCE_REGISTERED_TIME = DictionaryUtil.nullValue();
	}
}