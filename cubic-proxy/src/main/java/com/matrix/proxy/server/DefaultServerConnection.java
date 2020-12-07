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

package com.matrix.proxy.server;

import com.cubic.proxy.common.server.ServerConnection;
import com.cubic.proxy.common.server.AbstractConnection;
import io.netty.channel.Channel;

import java.util.Objects;

public class DefaultServerConnection extends AbstractConnection implements ServerConnection {

    private final String agentId;

    private final Channel channel;

    public DefaultServerConnection(String agentId, Channel channel) {
        super("agent", channel);
        this.agentId = agentId;
        this.channel = channel;
    }

    @Override
    public String getAgentId() {
        return agentId;
    }

    @Override
    public int getVersion() {
        return 0;
    }


    @Override
    public boolean isActive() {
        return channel.isActive();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultServerConnection that = (DefaultServerConnection) o;
        return
                Objects.equals(agentId, that.agentId) &&
                        Objects.equals(channel, that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agentId, channel);
    }
}
