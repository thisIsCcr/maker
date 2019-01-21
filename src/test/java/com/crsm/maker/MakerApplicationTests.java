package com.crsm.maker;

import com.alibaba.fastjson.JSONObject;
import com.crsm.maker.rabbitmq.HelloSender;
import com.crsm.maker.user.entity.SysRms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MakerApplicationTests {

    @Autowired
    AmqpTemplate template;

    @Test
    public void contextLoads() {
        Map map=new HashMap<String,Object>();
        map.put("order",0); //1：启动 0：关闭
        map.put("content","TestMessage");
        map.put("status",200);
        String jsonObject=JSONObject.toJSONString(map);
        System.out.println(jsonObject);
        template.convertAndSend("pi",jsonObject);
    }

}
