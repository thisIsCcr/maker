package com.crsm.maker.rabbitmq;

import org.springframework.stereotype.Component;

/**
 * Created  by Ccr on 2019/1/20
 **/
@Component
public class HelloReceive {

    /**
     * 接收消息
     * @param str
     */
   /* @RabbitListener(queues="hello")
    public void processC(String str) {
        System.out.println("content:"+str);
    }*/
}
