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
package com.cubic.proxy.common.server;

import com.cubic.serialization.agent.v1.CommonMessage;
import com.google.protobuf.TextFormat;

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
        if(latch.await(timeout, unit)){
            CommonMessage.Builder message = CommonMessage.newBuilder();
            try {
                TextFormat.merge(this.response, message);
            } catch (TextFormat.ParseException e) {
                e.printStackTrace();
            }
            return message.getBody();
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
