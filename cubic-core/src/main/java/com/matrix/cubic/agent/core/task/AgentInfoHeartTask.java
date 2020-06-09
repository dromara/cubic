/*
 * Copyright (C) 2019 Qunar, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.matrix.cubic.agent.core.task;


import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.matrix.cubic.agent.core.remote.CommandCode;
import com.matrix.cubic.agent.core.module.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AgentInfoHeartTask {

    private static final Logger log = LoggerFactory.getLogger(AgentInfoHeartTask.class);

    private static final ScheduledExecutorService executor = MoreExecutors.listeningDecorator(Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory("sky-agent-heartbeat")));

    public AgentInfoHeartTask() {
    }

    public void start(final Channel channel, final AtomicBoolean running) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                if (running.get()) {
                    Gson gson = new Gson();
                    channel.writeAndFlush(gson.toJson(new Message(CommandCode.HEARTBEAT.getCode(), "heart beat", "0000"))).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (!future.isSuccess()) {
                                log.error("send heartbeat error, {}");
                            } else {
                                log.debug("send heartbeat, {}", channel);
                            }
                        }
                    });
                    executor.schedule(this, 30, TimeUnit.SECONDS);
                }
            }
        });
    }
}
