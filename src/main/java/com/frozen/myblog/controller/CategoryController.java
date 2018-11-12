package com.frozen.myblog.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frozen.myblog.pojo.Article;
import com.frozen.myblog.pojo.Category;
import com.frozen.myblog.service.ArticleService;
import com.frozen.myblog.service.CategoryService;
import com.frozen.myblog.util.BlogException;
import com.frozen.myblog.util.MyUtils;
import com.frozen.myblog.util.Page;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService cs;
	@Autowired
	private ArticleService as;

	// produces解决返回前台字符串中文乱码问题 ,ajax提交新 分类表单
	@RequestMapping(value = "/admin/category", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	@ResponseBody()
	public String addCategory(Category category) {
		if (MyUtils.checkempty(category.getCategoryName())) {
			return "文章分类不能为空。";
		}
		try {
			cs.addCategory(category);
		} catch (BlogException e) {
			return e.getMessage();
		}
		return "success";
	}

	// ajax请求删除分类
	@RequestMapping(value = "/admin/category/{categoryId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deleteCategory(@PathVariable(required = true) Integer categoryId) {
		Map<String, String> map = new HashMap<String, String>();
		if (categoryId == null) {
			map.put("msg", "没有文章分类ID？？");
		} else {
			try {
				cs.deleteCategory(categoryId);
				map.put("msg", "success");
			} catch (BlogException e) {
				map.put("msg", e.getMessage());
			}
		}
		return map;
	}

	// ajax提交修改分类信息
	@RequestMapping(value = "/admin/category", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, String> editCategory(Category category) {
		Map<String, String> map = new HashMap<String, String>();
		if (category.getCategoryId() == null) {
			map.put("msg", "没有文章分类ID？？");
		} else if (MyUtils.checkempty(category.getCategoryName())) {
			map.put("msg", "分类名字不能为空。");
		} else {
			cs.editCategory(category);
			map.put("msg", "修改成功！");
		}
		return map;
	}

	// ajax请求文章分类
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	@ResponseBody()
	public List<Category> getCategory() {
		List<Category> list = cs.queryCategoryList();
		// 进行排序
		Collections.sort(list);
		return list;
	}

	// 后台 按文章分类展示分页
	@RequestMapping(value = "/admin/category/{categoryId}/p/{curr}", method = RequestMethod.GET)
	public String toarticleList(@PathVariable(required = true) Integer categoryId,
			@PathVariable(required = true) Integer curr, Model model) {
		if (curr < 1)
			return "forward:/admin/error";
		Page<Article> articlePage = as.queryArticlePageByCategory(categoryId, curr);
		model.addAttribute("articlePage", articlePage);
		model.addAttribute("categoryId", categoryId);
		return "admin/articlelist.jsp";
	}

	// 后台 没页数转发到第一页
	@RequestMapping(value = "/admin/category/{categoryId}", method = RequestMethod.GET)
	public String toarticleList(@PathVariable(required = true) Integer categoryId) {
		return "forward:/admin/category/" + categoryId + "/p/1";
	}

	// 前台 按文章分类展示分页
	@RequestMapping(value = "/category/{categoryId}/p/{curr}", method = RequestMethod.GET)
	public String toBlog(@PathVariable(required = true) Integer categoryId, @PathVariable(required = true) Integer curr,
			Model model) {
		if (curr < 1)
			return "forward:/error";
		Page<Article> articlePage = as.queryArticlePageByCategory(categoryId, curr);
		String categoryName;
		if (categoryId == 0) {
			categoryName = "All";
		} else {
			categoryName = cs.queryCategoryNameById(categoryId);
		}
		model.addAttribute("articlePage", articlePage);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("categoryName", categoryName);
		return "index.jsp";
	}

	// 前台 没页数转发到第一页
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
	public String toBlog(@PathVariable(required = true) Integer categoryId) {
		return "forward:/category/" + categoryId + "/p/1";
	}

}
