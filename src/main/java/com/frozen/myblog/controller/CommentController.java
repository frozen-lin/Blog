package com.frozen.myblog.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frozen.myblog.pojo.Comment;
import com.frozen.myblog.pojo.User;
import com.frozen.myblog.service.CommentService;
import com.frozen.myblog.service.UserService;
import com.frozen.myblog.util.BlogException;
import com.frozen.myblog.util.MyUtils;
import com.frozen.myblog.util.Page;
import com.frozen.myblog.util.VerifyCode;

@Controller
public class CommentController {
	@Autowired
	private CommentService cs;
	@Autowired
	private UserService us;
	//提交评论
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> submitComment(Comment comment, String verify, HttpSession session,
			HttpServletRequest req) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 校验表单
			if (MyUtils.checkempty(comment.getCommentContent()))
				throw new BlogException("评论内容不能为空");
			if (comment.getArticleId() == null)
				throw new BlogException("文章Id不能为空");
			if (!VerifyCode.confirmVerify(verify, session))
				throw new BlogException("验证码错误");
			// 检查是否登录
			User user = (User) session.getAttribute("user");
			if (user == null) {
				// 确认游客用户名,注册为3-8字符,必定不重复
				String username = "游客" + req.getRemoteAddr().substring(0, 7);
				user = us.addVisitor(username);
			}
			comment.setCommentTime(new Date());
			comment.setUsername(user.getUsername());
			cs.addComment(comment);
			map.put("errno", "0");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errno", "1");
			map.put("msg", e.getMessage());
		}
		return map;
	}

	// 查看个人评论
	@RequestMapping(value = "/comment", method = RequestMethod.GET)
	public String personalComment(HttpSession session, Model model, RedirectAttributes attr) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			List<Comment> commentlist = cs.queryCommentListByUsername(user.getUsername());
			model.addAttribute("list", commentlist);
			return "personalcomment.jsp";
		}
		attr.addAttribute("msg", "你还未登录,没有权限!");
		return "redirect:/error";
	}

	// ajax请求最新评论
	@RequestMapping(value = "/newcomment", method = RequestMethod.GET)
	@ResponseBody
	public List<Comment> getNewComment() {
		List<Comment> list = cs.queryNewCommentList();
		return list;
	}

	// 用户删除comment
	@RequestMapping(value = "/comment/{commentId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deleteComment(@PathVariable Integer commentId, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 取出session中的用户,确认是该用户删除的是自己的评论
			User user = (User) session.getAttribute("user");
			cs.deleteCommentByIdByUser(commentId, user.getUsername());
			map.put("errno", "0");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errno", "1");
			map.put("msg", e.getMessage());
		}
		return map;
	}
	
	// 管理员删除comment
	@RequestMapping(value = "/admin/comment/{commentId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deleteComment(@PathVariable Integer commentId) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			cs.deleteCommentByIdByAdmin(commentId);
			map.put("errno", "0");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errno", "1");
			map.put("msg", e.getMessage());
		}
		return map;
	}
	
	//后台分页查看评论入口
	@RequestMapping(value = "/admin/comment",method=RequestMethod.GET)
	public String toAdminComment() {
		return "forward:/admin/comment/p/1";
	}
	
	//分页查看评论
	@RequestMapping(value="/admin/comment/p/{curr}",method=RequestMethod.GET)
	public String adminComment(@PathVariable Integer curr,Model model) {
		Page<Comment> commentPage = cs.queryCommentPage(curr);
		model.addAttribute("commentPage", commentPage);
		return "admin/comment.jsp";
	}
}
