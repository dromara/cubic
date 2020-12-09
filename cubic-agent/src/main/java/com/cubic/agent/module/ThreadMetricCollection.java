package com.cubic.agent.module;

/**
 * @Author qinqixuan
 * @Date 2020/12/08
 * @Version V1.0
 **/
public class ThreadMetricCollection {

	public static Builder newBuilder() {
		return new Builder();
	}

	private Builder builder;

	public Builder getBuilder() {
		return builder;
	}

	public static class Builder {

		private String threadDump;
		private String allThreadPools;
		private int serviceInstanceId;
		private long time;
		private String instanceUUID;

		public String getThreadDump() {
			return threadDump;
		}

		public String getAllThreadPools() {
			return allThreadPools;
		}

		public int getServiceInstanceId() {
			return serviceInstanceId;
		}

		public long getTime() {
			return time;
		}

		public String getInstanceUUID() {
			return instanceUUID;
		}

		public Builder setThreadDump(String threadDump) {
			this.threadDump = threadDump;
			return this;
		}

		public Builder setAllThreadPools(String allThreadPools) {
			this.allThreadPools = allThreadPools;
			return this;
		}

		public Builder setServiceInstanceId(int serviceInstanceId) {
			this.serviceInstanceId = serviceInstanceId;
			return this;
		}

		public Builder setTime(long time) {
			this.time = time;
			return this;
		}

		public Builder setInstanceUUID(String instanceUUID) {
			this.instanceUUID = instanceUUID;
			return this;
		}

		public ThreadMetricCollection build() {
			return new ThreadMetricCollection(this);
		}
	}

	public ThreadMetricCollection(Builder builder) {
		this.builder = builder;
	}
}
