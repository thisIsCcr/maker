package com.crsm.maker.socketService.socks5ProxyService;

import com.crsm.maker.socketService.socks5ProxyService.handler.Socks5CmdRequestHandler;
import com.crsm.maker.socketService.socks5ProxyService.handler.Socks5InitialRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.socksx.v5.Socks5ClientEncoder;
import io.netty.handler.codec.socksx.v5.Socks5CommandRequestDecoder;
import io.netty.handler.codec.socksx.v5.Socks5InitialRequestDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * creat by Ccr on 2019/4/10
 * socks5代理服务
 **/
@Slf4j
@Component
public class socks5Service {

    private final static int port = 1080;

    @PostConstruct
    public void initService() {
        new Thread(() -> new socks5Service().start()).start();
    }

    public void start() {
        EventLoopGroup worker = new NioEventLoopGroup();
        EventLoopGroup boos = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            bootstrap.group(worker, boos)
                    .localAddress(port)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //使用socks5解码
                            ch.pipeline().addLast(Socks5ClientEncoder.DEFAULT)
                                    .addLast(new Socks5InitialRequestDecoder())
                                    .addLast(new Socks5CommandRequestDecoder())
                                    .addLast(new Socks5InitialRequestHandler())
                                    .addLast(new Socks5CmdRequestHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();
            log.info("开启socks5代理服务···········");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

}
