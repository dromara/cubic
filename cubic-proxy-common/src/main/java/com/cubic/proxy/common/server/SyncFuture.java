package com.cubic.proxy.common.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.*;

/**
 * @ClassName SyncFuture
 * @Author QIANGLU
 * @Date 2020/3/24 3:44 下午
 * @Version 1.0
 */
public class SyncFuture implements Future<String> {

    private CountDownLatch latch = new CountDownLatch(1);

    private String response;

    private long beginTime = System.currentTimeMillis();

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        if(response != null){
            return true;
        }
        return false;
    }

    @Override
    public String get() throws InterruptedException, ExecutionException {
        latch.await();
        return this.response;
    }

    @Override
    public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if(latch.await(timeout,unit)){
            JSONObject object = JSON.parseObject(this.response);
            return object.getString("body");
        }
        return null;
    }

    public void setResponse(String response) {
        this.response = response;
        latch.countDown();
    }

    public long getBeginTime() {
        return beginTime;
    }
}
