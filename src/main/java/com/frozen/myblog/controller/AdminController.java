package com.frozen.myblog.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.frozen.myblog.pojo.Article;
import com.frozen.myblog.pojo.Carousel;
import com.frozen.myblog.pojo.Category;
import com.frozen.myblog.service.ArticleService;
import com.frozen.myblog.service.CarouselService;
import com.frozen.myblog.service.CategoryService;
import com.frozen.myblog.service.CommentService;
import com.frozen.myblog.service.LinkService;
import com.frozen.myblog.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private CategoryService cs;
	@Autowired
	private ArticleService as;
	@Autowired
	private UserService us;
	@Autowired
	private LinkService ls;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CarouselService carouselService;
	
	//转发后台首页
	@RequestMapping(value= {"","/index"}, method = RequestMethod.GET)
	public String toAdminIndex(Model model) {
		Integer articleCount = as.queryArticleCount();
		Integer userCount = us.queryUserCount();
		Integer linkCount = ls.queryLinkCount();
		Integer commentCount = commentService.queryCommentCount();
		model.addAttribute("articleCount", articleCount);
		model.addAttribute("userCount", userCount);
		model.addAttribute("linkCount", linkCount);
		model.addAttribute("commentCount", commentCount);
		return "admin/index.jsp";
	}
	
	//转发后台写文章页面
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String toWrite() {
		return "admin/write.jsp";
	}
	
	//转发后台成功页面
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String toSuccess(Model model) {
		return "admin/success.jsp";
	}
	
	//转发后台错误页面
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String toError(Model model) {
		return "admin/error.jsp";
	}

	//转发分类管理页面
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String toCategory(Model model) {
		List<Category> categorylist = cs.queryCategoryAndArticleCount();
		Collections.sort(categorylist);
		model.addAttribute("list", categorylist);
		return "admin/category.jsp";
	}
	
	//转发修改文章页面
	@RequestMapping(value = "/edit/{articleId}", method = RequestMethod.GET)
	public String toEditArticle(@PathVariable Integer articleId, Model model) {
		Article article = as.queryArticleById(articleId);
		if(article==null) {return "forward:/admin/error";}
		model.addAttribute("article", article);
		return "admin/write.jsp";
	}

	//转发友链管理页面
	@RequestMapping(value = "/link", method = RequestMethod.GET)
	public String toLink() {
		return "admin/link.jsp";
	}

	//转发轮播图管理页面
	@RequestMapping(value = "/carousel", method = RequestMethod.GET)
	public String toCarousel(Model model) {
		List<Carousel> list = carouselService.queryCarousel();
		Collections.sort(list);
		model.addAttribute("list", list);
		return "admin/carousel.jsp";
	}
	
	
}
