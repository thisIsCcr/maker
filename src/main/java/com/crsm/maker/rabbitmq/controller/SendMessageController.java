package com.crsm.maker.rabbitmq.controller;

import com.alibaba.fastjson.JSONObject;
import com.crsm.maker.base.BaseController;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created  by Ccr on 2019/2/12
 **/
@RestController
@RequestMapping("piControl")
public class SendMessageController extends BaseController {

    @Autowired
    AmqpTemplate amqpTemplate;


    @RequestMapping(value = "piCameraSwitch/{switchs}",method = RequestMethod.GET)
    public String piCameraSwitch(@PathVariable("switchs")Integer switchs){
       Map map=new HashMap<String,Object>();
        map.put("order",switchs); //1：启动 0：关闭
        map.put("content","TestMessage");
        map.put("status",200);
        String jsonObject=JSONObject.toJSONString(map);
        System.out.println(jsonObject);
        amqpTemplate.convertAndSend("pi",jsonObject);
        return success();
    }

}
