package com.frozen.myblog.Interceptor;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.frozen.myblog.pojo.User;
import com.frozen.myblog.service.UserService;
import com.frozen.myblog.util.MyUtils;

public class AutoLoginInterceptor implements HandlerInterceptor {
	@Autowired
	private UserService us;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object o) throws Exception {
		// 检查是否已登录
		User user = (User) req.getSession().getAttribute("user");
		if (user == null) {
			Cookie[] cookies = req.getCookies();
			String username = null;
			String password = null;
			// 取出cookie中的用户名、密码
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if ("username".equals(cookie.getName())) {
						//因为存入时编码了,所以取出要进行解码
						username = URLDecoder.decode(cookie.getValue(),"UTF-8");
					}
					if ("password".equals(cookie.getName())) {
						password = cookie.getValue();
					}
				}
			}
			// 若取出的用户名密码都不为空
			if (!MyUtils.checkempty(username) && !MyUtils.checkempty(password)) {
				user = new User();
				user.setUsername(username);
				user.setPassword(password);
				//登录
				User t_user=us.login(user);
				req.getSession().setAttribute("user", t_user);
			}
		}
		return true;
	}

}
