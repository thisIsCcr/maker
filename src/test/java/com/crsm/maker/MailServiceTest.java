package com.crsm.maker;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crsm.maker.mail.OrderManager;
import com.crsm.maker.quartz.BaseJob;
import com.crsm.maker.resourcesFile.entity.SysResource;
import com.crsm.maker.resourcesFile.mapper.FileAudioMapper;
import com.crsm.maker.resourcesFile.service.ISystemResourceService;
import com.crsm.maker.user.mapper.SysTreeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private OrderManager orderManager;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    SysTreeMapper sysTreeMapper;

    @Autowired
    ISystemResourceService iSystemResourceService;

    @Autowired
    private FileAudioMapper fileAudioMapper;


    @Test
    public void getAllJob() throws SchedulerException {
        //iSystemResourceService.getAllMusicInfo();
    }

    @Test
    public void mapper() throws Exception {


        /*List<SysTree> lis=sysTreeMapper.getPermission(new ArrayList<String>(){{add("1");add("2");}});
        for (SysTree item:lis){
            System.out.println(item.toString());
        }*/
    }

    /**
     * 必须是Job（BaseJob）的子类，
     *
     * @param classname
     * @return
     * @throws Exception
     */
    public static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob) class1.newInstance();
    }


    @Test
    public void testPage() {
        Page<SysResource> page = new Page<>(1, 10);
        SysResource sysResource = new SysResource();
        sysResource.setUserId(1);
        IPage<SysResource> iPage = iSystemResourceService.selectPageVo(page, sysResource);
    }


    @Test
    public void testSimpleMail() throws Exception {
        orderManager.sendSimpleMail("thisccr@gmail.com", "测试内容", " 这是测试内容");
    }

    /*@Test
    public void testHtmlMail() throws Exception {

        //创建邮件正文
        Context context = new Context();
        context.setVariable("id", "006");
        String emailContent = templateEngine.process("emailTemplate", context);

        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        orderManager.sendHtmlMail("1184421183@qq.com","这是模板测试邮件",emailContent);
    }

    @Test
    public void sendInlineResourceMail() {
        String rscId = "neo006";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "C:\\Users\\Administrator\\Desktop\\QQ图片20190104172613.jpg";

        orderManager.sendInlineResourceMail("577290947@qq.com", "主题：这是有图片的邮件", content, imgPath, rscId);
    }*/

}
