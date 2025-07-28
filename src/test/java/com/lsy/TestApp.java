package com.lsy;


import com.lsy.domain.Admin;
import com.lsy.domain.Consumer;
import com.lsy.mapper.AdminMapper;
import com.lsy.mapper.ConsumerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class TestApp {

    //Spring ioc 容器中 找  AdminMapper 对象
    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void test1() {
        for (Admin admin : adminMapper.selectList(null)) {
            System.out.println(admin);
        }
    }
    @Autowired
    private ConsumerMapper consumerMapper;

    @Test
    public void test2() {
        for (Consumer consumer : consumerMapper.selectList(null)) {
            System.out.println(consumer);
        }
    }
    @Autowired
    private JavaMailSender sender;

    @Test
    public void test3() {
        // 邮件对象
        SimpleMailMessage mimeMessage = new SimpleMailMessage();

        // 发件人（必须和邮箱验证账号一致）
        mimeMessage.setFrom("1643489909@qq.com");

        // 收件人
        mimeMessage.setTo("1643489909@qq.com");

        // 主题
        mimeMessage.setSubject("测试...");

        // 内容
        mimeMessage.setText("内容...");

        // 发送
        sender.send(mimeMessage);
    }

}