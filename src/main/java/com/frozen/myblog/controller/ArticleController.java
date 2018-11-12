package com.frozen.myblog.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frozen.myblog.pojo.Article;
import com.frozen.myblog.service.ArticleService;
import com.frozen.myblog.util.BlogException;
import com.frozen.myblog.util.MyUtils;
import com.frozen.myblog.util.Uploader;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService as;

	// 提交文章表单
	@RequestMapping(value = "/admin/article", method = RequestMethod.POST)
	public String publish(Article article, Model model,RedirectAttributes attr ,HttpSession session) {
		// 校验表单
		if (MyUtils.checkempty(article.getArticleContent())) {
			model.addAttribute("article", article);
			model.addAttribute("msg", "内容不能为空！");
			return "admin/write.jsp";
		}
		if (MyUtils.checkempty(article.getArticleTitle())) {
			model.addAttribute("article", article);
			model.addAttribute("msg", "文章标题不能为空！");
			return "admin/write.jsp";
		}
		if (article.getCategory() == null || article.getCategory().getCategoryId() == null) {
			model.addAttribute("article", article);
			model.addAttribute("msg", "请选择文章分类！");
			return "admin/write.jsp";
		}
		// 设置文章发表时间
		article.setArticleTime(new Date());
		as.saveArticle(article);
		// 重定向到成功页面
		attr.addAttribute("msg","文章提交成功!!!");
		return "redirect:success";
	}

	// 富文本编辑器图片上传支持
	@RequestMapping(value = "/admin/imageUp", method = RequestMethod.POST)
	@ResponseBody()
	public Map<String, Object> imageUp(@RequestParam("file") MultipartFile file, HttpServletRequest req) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 文件上传
		Uploader uploader = new Uploader(req);
		uploader.setSavePath("static/upload/image");
		uploader.setFile(file);
		uploader.upload();
		if (uploader.getState() == "success") {
			String[] str = { uploader.getUrl() };
			// errno:0 代表无错误
			map.put("errno", 0);
			map.put("data", str);
		} else {
			map.put("errno", 1);
		}
		return map;
	}
	
	//ajax请求删除文章
	@RequestMapping(value = "/admin/article/{articleId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deleteArticle(@PathVariable(required = true) Integer articleId) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			as.deleteArticle(articleId);
			// errno:0 代表无错误
			map.put("errno", "0");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errno", "1");
			map.put("msg", e.getMessage());
		}
		return map;
	}
	
	
	//上传修改文章表单
	@RequestMapping(value = "/admin/article", method = RequestMethod.PUT)
	public String editArticle(Article article, Model model,RedirectAttributes attr) {
		// 校验表单
		if (article.getArticleId() == null) {
			model.addAttribute("article", article);
			model.addAttribute("msg", "没有文章Id！");
			return "admin/write.jsp";
		}
		if (MyUtils.checkempty(article.getArticleContent())) {
			model.addAttribute("article", article);
			model.addAttribute("msg", "内容不能为空！");
			return "admin/write.jsp";
		}
		if (MyUtils.checkempty(article.getArticleTitle())) {
			model.addAttribute("article", article);
			model.addAttribute("msg", "文章标题不能为空！");
			return "admin/write.jsp";
		}
		if (article.getCategory() == null || article.getCategory().getCategoryId() == null) {
			model.addAttribute("article", article);
			model.addAttribute("msg", "请选择文章分类！");
			return "admin/write.jsp";
		}
		article.setArticleTime(new Date());
		as.editArticle(article);
		attr.addAttribute("msg","文章修改成功!!!");
		return "redirect:success";
	}
	
	@RequestMapping(value="/article/{articleId}",method=RequestMethod.GET)
	public String detailView(@PathVariable(required=true) Integer articleId,Model model) {
		try {
			Article article=as.queryArticleDetail(articleId);
			model.addAttribute("article", article);
			return "article.jsp";
		} catch (BlogException e) {
			//转发至错误页面
			return "forward:/error";
			
		}
	}
}
