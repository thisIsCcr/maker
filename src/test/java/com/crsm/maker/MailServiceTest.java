package com.crsm.maker;

import com.crsm.maker.mail.OrderManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private OrderManager orderManager;

    @Autowired
    TemplateEngine templateEngine;

    @Test
    public void testSimpleMail() throws Exception {
        orderManager.sendSimpleMail("577290947@qq.com","test simple mail"," hello this is simple mail");
    }

    @Test
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
    }

}
