package com.crsm.maker.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created  by Ccr on 2019/1/20
 **/
@Component
public class RaspberrypiMessageConfig {
    @Bean
    public Queue Queue() {
        return new Queue("pi");
    }
}
