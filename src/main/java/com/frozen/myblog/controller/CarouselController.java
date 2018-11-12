package com.frozen.myblog.controller;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.frozen.myblog.pojo.Carousel;
import com.frozen.myblog.service.CarouselService;
import com.frozen.myblog.util.BlogException;
import com.frozen.myblog.util.Uploader;

@Controller
public class CarouselController {
	@Autowired
	CarouselService cs;

	// 上传轮播图图片
	@RequestMapping(value = "/admin/carousel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> CarouselUp(MultipartFile file, HttpServletRequest req) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Uploader up = new Uploader(req);
			// 设置上传参数
			up.setFile(file);
			up.setSavePath("static/upload/carousel");
			// 上传
			up.upload();
			if (up.getState() != "success") {
				throw new BlogException("上传失败");
			}
			Carousel carousel = new Carousel();
			carousel.setCarouselUrl(up.getUrl());
			cs.saveCarousel(carousel);
			map.put("errno", "0");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errno", "1");
		}
		return map;
	}

	// ajax提交修改 轮播图信息表单
	@RequestMapping(value = "/admin/carousel", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, String> editCarousel(Carousel carousel) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (carousel.getCarouselId() == null) {
				throw new BlogException("没有轮播图Id");
			}
			cs.editCarousel(carousel);
			map.put("errno", "0");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errno", "1");
			map.put("msg", e.getMessage());
		}
		return map;
	}

	// ajax请求 删除轮播图
	@RequestMapping(value = "/admin/carousel/{carouselId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deleteCarousel(@PathVariable(required = true) Integer carouselId,
			HttpServletRequest req) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Carousel carousel = cs.deleteCarousel(carouselId);
			map.put("errno", "0");
			try {
				if (carousel != null) {
					// 获取图片真实路径
					String path = carousel.getCarouselUrl().substring(carousel.getCarouselUrl().indexOf("/static"));
					String realpath = req.getSession().getServletContext().getRealPath(path);
					// commons-io删除图片
					FileUtils.forceDelete(new File(realpath));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errno", "1");
			map.put("msg", e.getMessage());
		}
		return map;
	}
	//ajax请求order排前三的轮播图
	@RequestMapping(value = "/carousel", method = RequestMethod.GET)
	@ResponseBody
	public List<Carousel> getCarousel() {
		List<Carousel> list = cs.queryCarousel();
		Collections.sort(list);
		//取前三条
		return list.subList(0, 3);
	}
}
