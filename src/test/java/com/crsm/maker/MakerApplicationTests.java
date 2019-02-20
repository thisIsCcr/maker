package com.crsm.maker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class MakerApplicationTests {

    /*@Autowired
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
    }*/

    public static void main(String[] args){
           while (true){
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm:ss")));
           }
    }

}
