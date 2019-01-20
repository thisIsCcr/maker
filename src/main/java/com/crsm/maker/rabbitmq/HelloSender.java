package com.crsm.maker.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created  by Ccr on 2019/1/20
 **/
@Component
public class HelloSender {
    @Autowired
    AmqpTemplate template;

    /**
     * 发送消息
     * @param content
     */
    public void send(String content){
        template.convertAndSend("hello",content);
    }
}
