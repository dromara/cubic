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
package com.matrix.proxy.server.process;

import com.cubic.proxy.common.handler.ServerMessageProcess;
import com.cubic.proxy.common.server.SyncFuture;
import com.cubic.proxy.common.session.Session;
import com.cubic.proxy.common.session.SessionManager;
import com.cubic.serialization.agent.v1.CommonMessage;
import com.google.common.cache.*;
import com.matrix.proxy.util.CubicContextHolder;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName AbstractMessageProcess
 * @Author QIANGLU
 * @Date 2020/4/7 2:44 下午
 * @Version 1.0
 */
@Slf4j
public class DefaultMessageProcess implements ServerMessageProcess {

    private final static LoadingCache<String, SyncFuture> futureCache;
    private SessionManager sessionManager;


    static {
        futureCache = CacheBuilder.newBuilder()
                .initialCapacity(100)
                .maximumSize(10000)
                .concurrencyLevel(20)
                .expireAfterWrite(8, TimeUnit.SECONDS)
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
                        log.info("LoadingCache: {} was removed, cause is {}", notification.getKey(), notification.getCause());
                    }
                })
                .build(new CacheLoader<String, SyncFuture>() {
                    @Override
                    public SyncFuture load(String key) throws Exception {
                        // 当获取key的缓存不存在时，不需要自动添加
                        return null;
                    }
                });
    }

    public DefaultMessageProcess() {
    }

    public static void putSyncFuture(String id, SyncFuture syncFuture) {
        futureCache.put(id, syncFuture);
    }

    @Override
    public boolean ackSync(CommonMessage msg) {
        if(sessionManager == null){
            this.sessionManager = CubicContextHolder.getCache(SessionManager.class);
        }
        log.info("接收到服务返回数据 length: {}", msg.getSerializedSize());
        boolean rs = false;
        try {
            String id = msg.getId();
            SyncFuture syncFuture = futureCache.getIfPresent(id);
            if (syncFuture == null) {
                log.warn("ackSync command data length:{},but can not found SyncFuture by cache", msg.getSerializedSize());
                return ackToWeb(msg);
            }
            syncFuture.setResponse(msg.toString());
            futureCache.invalidate(id);
            rs = true;
        } catch (Exception e) {
            log.error("ackSync data error", e);
            rs = false;
        }
        return rs;
    }

    public boolean ackToWeb(CommonMessage msg) {

        if (sessionManager == null) {
            return false;
        }
        Session session = sessionManager.getSession(msg.getId());
        if (session == null) {
            log.warn("receive message ,can not get websocket session ");
            return false;
        }

        log.info("接收到 arthas 返回数据，id:{}", msg.getId());

        if (StringUtils.isNotEmpty(msg.getBody())) {
            session.writeToWeb(msg);
        }
        return true;
    }

    @Override
    public Integer code() {
        return 999;
    }

    @Override
    public void process(ChannelHandlerContext ctx, CommonMessage datagram) {

    }
}
