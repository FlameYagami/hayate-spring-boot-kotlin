package com.hayate.mail;

import com.hayate.mail.service.intf.IMailService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 邮件发送功能测试类
 * Created by Flame on 2020/03/19.
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HayateMailApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MailTest {

    @Autowired
    private IMailService iMailService;

    @Test
    public void mailTest(){
        boolean result = iMailService.sendFeedbackMail("394198188@qq.com", "feedbackTitle", "feedbackMessage");
        System.out.println("test result => " + result);
    }
}
