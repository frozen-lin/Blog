package com.frozen.myblog.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frozen.myblog.pojo.MailPojo;
import com.frozen.myblog.service.MailService;
import com.frozen.myblog.util.BlogException;
import com.frozen.myblog.util.MyUtils;

@Controller
public class MailController {
	@Autowired
	private MailService ms;
	//ajax的mail提交
	@RequestMapping(value = "/mail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> sendMail(MailPojo mail) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (MyUtils.checkempty(mail.getMailContent())) {
				throw new BlogException("内容不能为空");
			}
			if (MyUtils.checkempty(mail.getMailTitle())) {
				throw new BlogException("标题不能为空");
			}
			ms.sendMail(mail);
			map.put("errno", "0");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errno", "1");
			map.put("msg", e.getMessage());
		}
		return map;
	}
	//查找收到多少邮件
	@RequestMapping(value = "/admin/mail", method = RequestMethod.GET)
	@ResponseBody
	public Integer getMailNum() {
		return ms.getMailNum();
	}
	//清除mailNum
	@RequestMapping(value = "/admin/clearMailNum", method = RequestMethod.GET)
	public String clearMailNum() {
		ms.setMailNumZero();
		//重定向至126邮箱
		return "redirect:https://mail.126.com";
	}
}
