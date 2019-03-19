package com.crsm.maker.socketService.webSocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * creat by Ccr on 2018/11/27
 **/
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //HttpServerCode:将请求和应答消息解码为HTTP消息
        socketChannel.pipeline().addLast("http-codec",new HttpServerCodec());
        //HttpObjectAggregator：
        socketChannel.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));
        //ChunkedWriteHandler：向客户端发送HTML5文件
        socketChannel.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
        //在管道中添加我们自己的接收数据实现方法
        socketChannel.pipeline().addLast("handler",new MyWebSocketServerHandler());
    }
}
