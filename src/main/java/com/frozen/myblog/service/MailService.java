package com.frozen.myblog.service;

import javax.mail.MessagingException;

import com.frozen.myblog.pojo.MailPojo;

public interface MailService {

	void sendMail(MailPojo mail) throws MessagingException;

	Integer getMailNum();

	void setMailNumZero();

}
