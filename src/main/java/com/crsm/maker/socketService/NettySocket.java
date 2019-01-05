package com.crsm.maker.socketService;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * creat by Ccr on 2018/11/27
 **/
@Slf4j
@Component
public class NettySocket {

    private int port = 10010;

    @PostConstruct
    public void initNetty() {
        new Thread() {
            @Override
            public void run() {
                new NettySocket().run();
            }
        }.start();
    }

    // Boss线程：由这个线程池提供的线程是boss种类的，用于创建、连接、绑定socket， （有点像门卫）然后把这些socket传给worker线程池。
    // 在服务器端每个监听的socket都有一个boss线程来处理。在客户端，只有一个boss线程来处理所有的socket。
    private EventLoopGroup boss = new NioEventLoopGroup();
    // Worker线程：Worker线程执行所有的异步I/O，即处理操作
    private EventLoopGroup work = new NioEventLoopGroup();

    public void run() {
        log.info("========================Netty端口启动======");
        try {
            // ServerBootstrap 启动NIO服务的辅助启动类,负责初始话netty服务器，并且开始监听端口的socket请求
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work);
            //设置非阻塞模式，用它来建立新accept，用于构建serversocketChannel的工厂类
            bootstrap.channel(NioServerSocketChannel.class);
            //ChildChannelHandler 对出入的数据进行的业务操作,其继承ChannelInitializer
            bootstrap.childHandler(new ChildChannelHandler());
            //添加日志
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));
            log.info("等待客户端连接···············");
            ChannelFuture channel = bootstrap.bind(port).sync();
            channel.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.info("[出现异常] 释放资源");
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    /**
     * 关闭服务器方法
     */
    @PreDestroy
    public void colse() {
        log.info("关闭Netty1------------");
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }


}
