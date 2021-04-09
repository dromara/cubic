package com.cubic.agent.module;


/**
 * jvm 线程栈传输对象
 *
 * @Author qinqixuan
 * @Date 2020/12/08
 * @Version V1.0
 **/
public class ThreadMetricCollection extends Message {

    private Builder builder;

    public Builder getBuilder() {
        return builder;
    }

    public static class Builder {

        private String threadDump;
        private String allThreadPools;
        private String serviceName;
        private long time;
        private String instanceUUID;

        public String getThreadDump() {
            return threadDump;
        }

        public String getAllThreadPools() {
            return allThreadPools;
        }

        public String getServiceName() {
            return serviceName;
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

        public Builder setServiceName(String serviceName) {
            this.serviceName = serviceName;
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

        public ThreadMetricCollection build(Integer code, String body, String id) {
            return new ThreadMetricCollection(this, code, body, id);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "threadDump='" + threadDump + '\'' +
                    ", allThreadPools='" + allThreadPools + '\'' +
                    ", serviceName='" + serviceName + '\'' +
                    ", time=" + time +
                    ", instanceUUID='" + instanceUUID + '\'' +
                    '}';
        }
    }

    public ThreadMetricCollection(Builder builder, Integer code, String body, String id) {
        super(code, body, id);
        this.builder = builder;
    }
}
