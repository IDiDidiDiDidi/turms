/*
 * Copyright (C) 2019 The Turms Project
 * https://github.com/turms-im/turms
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.turms.gateway.access.common.handler;

import im.turms.server.common.healthcheck.ServerStatusManager;
import im.turms.server.common.service.blocklist.BlocklistService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.CorruptedWebSocketFrameException;
import io.netty.util.internal.OutOfDirectMemoryError;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author James Chen
 */
@ChannelHandler.Sharable
public class ServiceAvailabilityHandler extends ChannelInboundHandlerAdapter {

    private final BlocklistService blocklistService;
    private final ServerStatusManager serverStatusManager;

    public ServiceAvailabilityHandler(BlocklistService blocklistService, ServerStatusManager serverStatusManager) {
        this.blocklistService = blocklistService;
        this.serverStatusManager = serverStatusManager;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        if (serverStatusManager.isActive()) {
            SocketAddress socketAddress = ctx.channel().remoteAddress();
            if (socketAddress instanceof InetSocketAddress address
                    && blocklistService.isIpBlocked(address.getAddress().getAddress())) {
                ctx.close();
                return;
            }
            ctx.fireChannelRegistered();
        } else {
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof CorruptedWebSocketFrameException) {
            InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
            blocklistService.tryBlockIpForCorruptedFrame(address.getAddress().getAddress());
        } else if (cause instanceof OutOfDirectMemoryError) {
            ctx.close();
        }
        ctx.fireExceptionCaught(cause);
    }

}