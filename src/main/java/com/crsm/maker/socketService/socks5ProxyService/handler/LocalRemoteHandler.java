package com.crsm.maker.socketService.socks5ProxyService.handler;

import com.crsm.maker.crypto.SSCrypto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * creat by Ccr on 2019/4/12
 **/
public class LocalRemoteHandler extends ChannelInboundHandlerAdapter {

    private SSCrypto ssCrypto;

    private ChannelHandlerContext channelHandlerContext;


    public LocalRemoteHandler(SSCrypto ssCrypto, ChannelHandlerContext channelHandlerContext) {
        this.ssCrypto = ssCrypto;
        this.channelHandlerContext = channelHandlerContext;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }


}
