package com.crsm.maker.socketService.socks5ProxyService.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.socksx.SocksVersion;
import io.netty.handler.codec.socksx.v5.DefaultSocks5InitialResponse;
import io.netty.handler.codec.socksx.v5.Socks5AuthMethod;
import io.netty.handler.codec.socksx.v5.Socks5InitialRequest;
import io.netty.handler.codec.socksx.v5.Socks5InitialResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * creat by Ccr on 2019/4/10
 **/
@Slf4j
public class Socks5InitialRequestHandler extends SimpleChannelInboundHandler<Socks5InitialRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Socks5InitialRequest msg) throws Exception {
            if (msg.decoderResult().isFailure()){
                log.info("current protocol is not socks5");
                ctx.fireChannelRead(msg);
            }else{
                if(msg.version().equals(SocksVersion.SOCKS5)){
                    Socks5InitialResponse initialResponse=new DefaultSocks5InitialResponse(Socks5AuthMethod.NO_AUTH);
                    ctx.write(initialResponse);
                }
            }

    }
}
