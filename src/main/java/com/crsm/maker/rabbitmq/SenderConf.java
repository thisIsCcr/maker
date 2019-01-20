package com.crsm.maker.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created  by Ccr on 2019/1/20
 **/
@Configuration
public class SenderConf {
    @Bean
    public Queue Queue() {
        return new Queue("hello");
    }
}
