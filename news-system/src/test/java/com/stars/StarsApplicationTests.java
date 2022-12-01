package com.stars;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;

@SpringBootTest
class StarsApplicationTests {

	@Autowired
	JavaMailSenderImpl mailSender;


	@Test
	void contextLoads() {


//      study: mail
//		 简单邮件  自动配置类：MailSenderAutoConfiguration
//		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//		simpleMailMessage.setSubject("夕阳小月，你好呀！");
//		simpleMailMessage.setText("远方有你真好");
//		simpleMailMessage.setTo("1456300078@qq.com");
//		simpleMailMessage.setFrom("1456300078@qq.com");
//		mailSender.send(simpleMailMessage);
//		System.out.println("---邮箱发送成功---");

	}

	@Test
	void contextLoads2() throws MessagingException {
		// 一个复杂的邮件
//		MimeMessage mimeMessage = mailSender.createMimeMessage();
//		// 组装 true支持多邮件发送
//		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//		// 正文 true支持标签
//		helper.setSubject("夕阳彤彤，你好呀！");
//		helper.setText("<p style='color:red'>今晚有红烧排骨！</p>", true);
//		// 附件
//		helper.addAttachment("bill@2x.png", new File("C:\\Users\\USER\\Desktop\\sunsetFog\\66666\\stars\\src\\main\\resources\\static\\img\\bill@2x.png"));
//		// 发件人和收件人
//		helper.setTo("1456300078@qq.com");
//		helper.setFrom("1456300078@qq.com");
//		// 发送
//		mailSender.send(mimeMessage);
//		System.out.println("---邮箱发送成功---");


	}



}
