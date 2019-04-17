package com.crsm.maker;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public  class MakerApplicationTests implements Runnable{

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





    public static void main(String[] args) throws InterruptedException {
        final String upuserName = "/home/resource/systemResource/123456/img/73718346_p0.png";
        for (int i = 0;i < upuserName.length();i++){
            //charAt是获取字符串第i个字符
            System.out.println(i+","+upuserName.charAt(i));
        }
        System.out.println(upuserName.substring(14));
       /* MakerApplicationTests syncThread = new MakerApplicationTests();
        Thread thread1 = new Thread(syncThread, "SyncThread1");
        Thread thread2 = new Thread(syncThread, "SyncThread2");
        thread1.start();
        thread2.start();*/
         /* int i=0;
          while (true){
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm:ss")));
           }*/
    }
    static int count=0;

    @Override
    public void run() {
        synchronized(this) {
            for (int i = 0; i < 5; i++) {
                try {
                    if(Thread.currentThread().getName()=="SyncThread1"){
                        wait(200);
                    }
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    /*if(count==3){
                        Thread.currentThread().notifyAll();
                    }*/
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
