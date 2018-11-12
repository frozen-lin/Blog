package com.frozen.myblog.service.implement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.frozen.myblog.pojo.MailPojo;
import com.frozen.myblog.service.MailService;

@Service
public class MailServiceImpl implements MailService {
	private Properties props;
	@Resource(name="incrmentRedisTemplate")
	private StringRedisTemplate rt;
	public MailServiceImpl() {
		// 初始化
		props = new Properties();
		InputStream inputStream = MailServiceImpl.class.getClassLoader().getResourceAsStream("mail.properties");
		try {
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMail(MailPojo mail) throws MessagingException {
		// 邮箱认证
		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(props.getProperty("mail.from"), props.getProperty("mail.password"));
			}
		};
		Session mailSession = Session.getInstance(props, auth);
		MimeMessage message = new MimeMessage(mailSession);
		// 设置邮件发信人
		message.setFrom(new InternetAddress(props.getProperty("mail.from")));
		// 设置邮件标题
		message.setSubject("Blog有人发消息了!!!");
		// 设置邮件内容
		message.setContent(mail.getMailTitle() + "<br />" + mail.getMailContent(), "text/html;charset=utf-8");
		// 设置邮件收信人,以及发送方式:正常发送
		message.addRecipient(RecipientType.TO, new InternetAddress(props.getProperty("mail.to")));
		// 发送
		Transport.send(message);
		// redis操作
		//如果 key不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作,所以直接自增操作即可
		rt.opsForValue().increment("mailNum", 1);
	}

	@Override
	public Integer getMailNum() {
		//将mailNum取出'
		String str = rt.opsForValue().get("mailNum");
		if(str==null) return 0;
		Integer i =Integer.parseInt(str);
		return i;
	}
	
	@Override
	public void setMailNumZero() {
		//删除mailNum
		rt.delete("mailNum");
	}
}
