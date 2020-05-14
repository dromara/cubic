package com.matrix.proxy.server.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.*;
import com.matrix.proxy.server.SyncFuture;
import com.matrix.proxy.server.handler.ServerMessgaeProcess;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName AbstractMessageProcess
 * @Author QIANGLU
 * @Date 2020/4/7 2:44 下午
 * @Version 1.0
 */
@Slf4j
public class DefaultMessageProcess implements ServerMessgaeProcess {

    private final static LoadingCache<String, SyncFuture> futureCache;

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
    public void ackSync(String msg) {

        log.info("接收到服务返回数据 length: {}", msg.length());

        try {
            JSONObject object = JSON.parseObject(msg);
            String id = object.getString("id");

            SyncFuture syncFuture = futureCache.getIfPresent(id);

            if (syncFuture == null) {
                log.warn("ackSync command data length:{},but can not found SyncFuture by cache", msg.length());
                return;
            }
            syncFuture.setResponse(msg);
            futureCache.invalidate(id);
        } catch (Exception e) {
            log.error("ackSync data error", e);
        }

    }

    @Override
    public Integer code() {
        return 999;
    }

    @Override
    public void process(ChannelHandlerContext ctx, String datagram) {

    }


}
