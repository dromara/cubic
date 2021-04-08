package com.cubic.agent.module;

/**
 * @Description: TODO
 * @Author qinqixuan
 * @Date 2020/12/09
 * @Version V1.0
 **/
public class KeyStringValuePair {

	public static Builder newBuilder() {
		return new Builder();
	}

	private Builder builder;

	public Builder getBuilder() {
		return builder;
	}

	public static class Builder {
		private String key;
		private String value;

		public String getKey() {
			return key;
		}

		public Builder setKey(String key) {
			this.key = key;
			return this;
		}

		public String getValue() {
			return value;
		}

		public Builder setValue(String value) {
			this.value = value;
			return this;
		}

		public KeyStringValuePair build(){
			return new KeyStringValuePair(this);
		}
	}

	public KeyStringValuePair(Builder builder) {
		this.builder = builder;
	}
}
