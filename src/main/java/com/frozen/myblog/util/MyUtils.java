package com.frozen.myblog.util;

public class MyUtils {
	// 校验用户名
	public static void ckeckUsername(String username) throws BlogException {
		if (checkempty(username)) {
			throw new BlogException("用户名不能为空");
		}
		if (username.length() < 3 || username.length() > 8) {
			throw new BlogException("用户名长度在3-8位之间");
		}
	}

	// 校验密码
	public static void ckeckpassword(String password) throws BlogException {
		if (checkempty(password)) {
			throw new BlogException("密码不能为空");
		}
		if (password.length() < 6 || password.length() > 12) {
			throw new BlogException("密码长度在6-12位之间");
		}
	}

	// 校验字符串是否为空
	public static boolean checkempty(String str) {
		// 字符串为null或去前后空格为空 返回true
		if (str == null || str.trim().equals("")) {
			return true;
		}
		return false;
	}

}
