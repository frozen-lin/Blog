package com.frozen.myblog.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frozen.myblog.util.VerifyCode;

@Controller
public class VerifyCodeController {
	@RequestMapping("/getVerifyCode")
	public void getVerifyCode(HttpSession session, HttpServletResponse resp) {
		// 生成验证码
		VerifyCode vc = new VerifyCode();
		String code = vc.getCode();
		// 将验证码保存至session中
		session.setAttribute("code", code);
		try {
			vc.write(resp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ajax校验验证码
	@RequestMapping("/confirmVerify")
	@ResponseBody()
	public Map<String, String> confirmVerify(String verify, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		// 验证码是否输入正确
		if (VerifyCode.confirmVerify(verify, session)) {
			map.put("valid", "true");
		} else {
			map.put("valid", "false");
		}
		return map;
	}
}
