package com.matrix.proxy.server.process;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.*;
import com.matrix.proxy.module.Command;
import com.matrix.proxy.server.SyncFuture;
import com.matrix.proxy.server.handler.ServerMessgaeProcess;
import com.matrix.proxy.session.Session;
import com.matrix.proxy.session.SessionManager;
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
public class DefaultMessageProcess implements ServerMessgaeProcess {

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
    public boolean ackSync(String msg) {

        if(sessionManager == null){
            this.sessionManager = CubicContextHolder.getCache(SessionManager.class);
        }
        log.info("接收到服务返回数据 length: {}", msg.length());
        boolean rs = false;
        try {
            Command cmd = JSON.parseObject(msg, Command.class);
            String id = cmd.getId();

            SyncFuture syncFuture = futureCache.getIfPresent(id);

            if (syncFuture == null) {
                log.warn("ackSync command data length:{},but can not found SyncFuture by cache", msg.length());

                return ackToWeb(cmd);
            }
            syncFuture.setResponse(msg);
            futureCache.invalidate(id);
            rs = true;
        } catch (Exception e) {
            log.error("ackSync data error", e);
            rs = false;
        }
        return rs;
    }

    public boolean ackToWeb(Command command) {

        if (sessionManager == null) {
            return false;
        }
        Session session = sessionManager.getSession(command.getId());
        if (session == null) {
            log.warn("receive message ,can not get websocket session ");
            return false;
        }

        log.info("接收到 arthas 返回数据，id:{}", command.getId());

        if (StringUtils.isNotEmpty(command.getBody())) {
            session.writeToWeb(command.getBody());
        }
        return true;
    }

    @Override
    public Integer code() {
        return 999;
    }

    @Override
    public void process(ChannelHandlerContext ctx, String datagram) {

    }


}
