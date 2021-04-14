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

package com.matrix.proxy.server;

import com.cubic.proxy.common.server.ServerConnection;
import com.cubic.proxy.common.server.ServerConnectionStore;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.MoreExecutors;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

/**
 * 存储应用连接
 * @author luqiang
 */
@Slf4j
@Service
public class DefaultServerConnectionStore implements ServerConnectionStore {

    private final ConcurrentMap<String, ServerConnection> connections = Maps.newConcurrentMap();

    @Override
    public ServerConnection register(String agentId, Channel channel) {
        DefaultServerConnection connection = new DefaultServerConnection(agentId, channel);
        ServerConnection oldConnection = connections.putIfAbsent(agentId, connection);
        if (oldConnection != null) {
            return oldConnection;
        }
        connection.init();
        connection.closeFuture().addListener(() -> connections.remove(agentId, connection), MoreExecutors.directExecutor());
        return connection;
    }

    @Override
    public Optional<ServerConnection> getConnection(String agentId) {

        if(agentId == null || connections == null){
            return Optional.empty();
        }
        ServerConnection agentConnection = connections.get(agentId);
        if (agentConnection != null) {
            return Optional.of(agentConnection);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Map<String, ServerConnection> getAgentConnection() {
        return ImmutableMap.copyOf(connections);
    }

    @Override
    public Map<String, ServerConnection> searchConnection(String agentId) {
        Map<String, ServerConnection> connection = getAgentConnection();
        Map<String, ServerConnection> result = Maps.filterKeys(connection, key -> key.indexOf(agentId) >= 0);
        return result;
    }
}
