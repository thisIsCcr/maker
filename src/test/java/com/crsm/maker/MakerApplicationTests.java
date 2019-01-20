package com.crsm.maker;

import com.alibaba.fastjson.JSONObject;
import com.crsm.maker.rabbitmq.HelloSender;
import com.crsm.maker.user.entity.SysRms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MakerApplicationTests {

    @Autowired
    private HelloSender helloSender;

    @Test
    public void contextLoads() {
        SysRms sysRms=new SysRms();
        sysRms.setId(1);
        sysRms.setRmsIocn("testIocn");
        sysRms.setRmsName("testRname");
        sysRms.setRmsUrl("testUrl");
        String jsonObject=JSONObject.toJSONString(sysRms);
        System.out.println(jsonObject);
        helloSender.send(jsonObject);
    }

}
