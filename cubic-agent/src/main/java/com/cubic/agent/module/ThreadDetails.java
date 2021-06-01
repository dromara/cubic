package com.cubic.agent.module;

@Deprecated
public class ThreadDetails {

	public static Builder newBuilder() {
		return new Builder();
	}

	private Builder builder;

	public Builder getBuilder() {
		return builder;
	}

	public static class Builder {
		private int count;
		private int peak;
		private long totalStarted;
		private int daemon;

		public int getCount() {
			return count;
		}

		public int getPeak() {
			return peak;
		}

		public long getTotalStarted() {
			return totalStarted;
		}

		public int getDaemon() {
			return daemon;
		}

		public Builder setCount(int count) {
			this.count = count;
			return this;
		}
		public Builder setPeak(int peak) {
			this.peak = peak;
			return this;
		}
		public Builder setTotalStarted(long totalStarted) {
			this.totalStarted = totalStarted;
			return this;
		}
		public Builder setDaemon(int daemon) {
			this.daemon = daemon;
			return this;
		}

		public ThreadDetails build(){
			return new ThreadDetails(this);
		}
	}

	public ThreadDetails(Builder builder) {
		this.builder = builder;
	}
}
