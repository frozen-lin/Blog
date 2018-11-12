package com.frozen.myblog.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frozen.myblog.pojo.User;
import com.frozen.myblog.service.UserService;
import com.frozen.myblog.util.Base64Utils;
import com.frozen.myblog.util.BlogException;
import com.frozen.myblog.util.MyUtils;
import com.frozen.myblog.util.VerifyCode;

@Controller
public class UserController {
	@Autowired
	private UserService us;
	
	//转发登录页面
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String toLogin(String status,Model model) {
		if(status!=null&&status.equals("success")) {
		model.addAttribute("msg", "注册成功,快去登录吧!!");
		}
		return "login.jsp";
	}

	//提交登录表单
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(User user, String autologin, Model model,RedirectAttributes attr,HttpSession session, HttpServletResponse res) {
		try {
			//校验用户名,密码
			if (MyUtils.checkempty(user.getUsername())) {
				model.addAttribute("msg", "用户名不能为空");
				return "login.jsp";
			}
			if (MyUtils.checkempty(user.getPassword())) {
				model.addAttribute("msg", "密码不能为空");
				return "login.jsp";
			}
			// 将密码用Base64编码
			user.setPassword(Base64Utils.encode(user.getPassword()));
			//正常登录,返回数据库中的该用户的全部信息,若用户名密码错误抛出异常
			User t_user = us.login(user);
			session.setAttribute("user", t_user);
			// 如果自动登录不为空,则添加账号密码的Cookie
			if (autologin != null) {
				//用户名有可能是中文,所以cookie需要进行编码
				Cookie namecookie = new Cookie("username",URLEncoder.encode(t_user.getUsername(),"UTF-8") );
				// 存入经Base64编码的密码
				Cookie passcookie = new Cookie("password", t_user.getPassword());
				// 保存7天
				namecookie.setPath("/");
				namecookie.setMaxAge(60 * 60 * 24 * 7);
				passcookie.setPath("/");
				passcookie.setMaxAge(60 * 60 * 24 * 7);
				res.addCookie(namecookie);
				res.addCookie(passcookie);
			}
		} catch (Exception e) {
			// 出现异常打回
			model.addAttribute("msg", e.getMessage());
			return "login.jsp";
		}
		// 正常登录,重定向到成功页面
		attr.addAttribute("msg","登录成功");
		return "redirect:/success";
	}

	// ajax校验用户名
	@RequestMapping(value = "/confirmUsername")
	@ResponseBody()
	public Map<String, String> confirmUsername(String username) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 若username不存在,返回true
			if (!us.existUsername(username)) {
				map.put("valid", "true");
			} else {
				map.put("valid", "false");
			}
		} catch (BlogException e) {
			// 出现异常返回false
			map.put("valid", "false");
		}
		return map;
	}
	
	//转发 注册页面
	@RequestMapping(value="/register",method = RequestMethod.GET)
	public String toRegister() {
		return "register.jsp";
	}

	//提交 注册表单
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(User user, String verify, HttpSession session, Model model) {
		// 如果验证码不正确,打回
		if (!VerifyCode.confirmVerify(verify, session)) {
			model.addAttribute("msg", "验证码不正确");
			return "register.jsp";
		}
		try {
			// 校验用户名,密码,不符合要求抛出异常
			MyUtils.ckeckUsername(user.getUsername());
			MyUtils.ckeckpassword(user.getPassword());
			// 将密码用Base64编码
			user.setPassword(Base64Utils.encode(user.getPassword()));
			us.register(user);
			return "redirect:/login?status=success";
		} catch (Exception e) {
			// 出现异常,打回
			model.addAttribute("msg", e.getMessage());
			return "register.jsp";
		}
	}
	@RequestMapping(value="/loginout")
	public String loginOut(HttpServletRequest req,HttpServletResponse resp) {
		req.getSession().removeAttribute("user");
		//删除Cookie
		Cookie namecookie = new Cookie("username", null);
		namecookie.setPath("/");
		namecookie.setMaxAge(0);
		Cookie passcookie = new Cookie("password", null);
		passcookie.setPath("/");
		passcookie.setMaxAge(0);
		resp.addCookie(namecookie);
		resp.addCookie(passcookie);
		return "redirect:/index";	
	}
}
