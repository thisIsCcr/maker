package com.crsm.maker.socketService.socks5ProxyService.handler;

import com.crsm.maker.crypto.CryptoException;
import com.crsm.maker.crypto.CryptoFactory;
import com.crsm.maker.crypto.SSCrypto;
import com.crsm.maker.socketService.socks5ProxyService.ProxyConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.socksx.v5.*;
import lombok.extern.slf4j.Slf4j;

/**
 * creat by Ccr on 2019/4/10
 **/
@Slf4j
public class Socks5CmdRequestHandler extends SimpleChannelInboundHandler<DefaultSocks5CommandRequest> {

    private ProxyConfig proxyConfig;

    private SSCrypto ssCrypto;

    private EventLoopGroup boosGroup = new NioEventLoopGroup();

    public Socks5CmdRequestHandler() {
        try {
            ssCrypto = CryptoFactory.create(proxyConfig.getMethod(), proxyConfig.getPassword());
        } catch (CryptoException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DefaultSocks5CommandRequest msg) throws Exception {
        if (msg.type().equals(Socks5CommandType.CONNECT)) {
            log.info("connecting remote server");

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(boosGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new RemoteLocalHandler());
                        }
                    });
            ChannelFuture future=bootstrap.connect(proxyConfig.getServerAddress(),proxyConfig.getServerPort());
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()){
                        log.info("successfully connected remote server");
                        ctx.pipeline().addLast(new LocalRemoteHandler(ssCrypto,ctx));
                    }else{
                        Socks5CommandResponse commandResponse=new DefaultSocks5CommandResponse(Socks5CommandStatus.FAILURE,Socks5AddressType.IPv4);
                        ctx.writeAndFlush(commandResponse);
                    }
                }
            });
        }else {

        }
    }
}
