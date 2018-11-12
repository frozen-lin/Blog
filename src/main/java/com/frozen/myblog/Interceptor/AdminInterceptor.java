package com.frozen.myblog.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.frozen.myblog.pojo.User;

public class AdminInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}
	//进入controller调用,拦截权限不足的用户进入后台
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object o) throws Exception {
		User user=(User)req.getSession().getAttribute("user");
		//若已登录且用户权限=1放行,否则拦截
		if(user!=null&&user.getPermission().equals(1)) {
			return true;
		}
		req.setAttribute("msg", "您无权进入后台!!");
		req.getRequestDispatcher("/login").forward(req, resp);
		return false;
	}

}
