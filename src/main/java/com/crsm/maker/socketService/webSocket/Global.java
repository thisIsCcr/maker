package com.crsm.maker.socketService.webSocket;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * creat by Ccr on 2018/11/27
 * 存储用户连接信息
 **/
public class Global {

    public static ChannelGroup group = new DefaultChannelGroup("webSocket",GlobalEventExecutor.INSTANCE);

}
