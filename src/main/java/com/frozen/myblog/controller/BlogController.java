package com.frozen.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.frozen.myblog.pojo.Article;
import com.frozen.myblog.service.ArticleService;
import com.frozen.myblog.util.MyUtils;
import com.frozen.myblog.util.Page;

@Controller
public class BlogController {
	@Autowired
	ArticleService as;

	// 前台的成功页面
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String toSuccess(Model model) {
		return "success.jsp";
	}

	// 前台的错误页面
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String toError(Model model) {
		model.addAttribute("msg", "error");
		return "error.jsp";
	}

	// 前台首页 ,跳转分类为全部的第一页
	@RequestMapping(value= {"","/index"},method = RequestMethod.GET)
	public String toIndex() {
		return "forward:/category/0";
	}

	// 前台搜
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search() {
		return "forward:/search/p/1";
	}

	// 前台搜索
	@RequestMapping(value = "/search/p/{curr}", method = RequestMethod.GET)
	public String search(@PathVariable(required=true)Integer curr,String q,Model model) {
		if(curr<1) return "forward:/error";
		//ruo查询条件为空,相当于查全部,转发至首页
		if(MyUtils.checkempty(q)) {
			return "forward:/index";
		}
		Page<Article> articlePage = as.queryArticlePageByParameter(q,curr);
		model.addAttribute("articlePage", articlePage);
		return "index.jsp";
	}
}
