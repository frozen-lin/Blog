package com.frozen.myblog.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frozen.myblog.pojo.Link;
import com.frozen.myblog.service.LinkService;
import com.frozen.myblog.util.BlogException;
import com.frozen.myblog.util.MyUtils;

@Controller
public class LinkController {
	@Autowired
	private LinkService ls;

	// ajax查友链
	@RequestMapping(value = "/link", method = RequestMethod.GET)
	@ResponseBody
	public List<Link> getLink() throws InterruptedException {
		List<Link> list = ls.queryLink();
		Collections.sort(list);
		return list;
	}

	// ajax提交友链表单
	@RequestMapping(value = "/admin/link", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addLink(Link link) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 校验表单
			if (MyUtils.checkempty(link.getLink())) {
				throw new BlogException("链接不能为空！");
			}
			if (MyUtils.checkempty(link.getLinkName())) {
				throw new BlogException("友链名称不能为空！");
			}
			ls.addLink(link);
			map.put("errno", "0");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errno", "1");
			map.put("msg", e.getMessage());
		}
		return map;
	}

	// ajax改友链信息
	@RequestMapping(value = "/admin/link", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, String> editLink(Link link) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 校验表单
			if (link.getLinkId()==null) {
				throw new BlogException("没有友链Id！");
			}
			if (MyUtils.checkempty(link.getLink())) {
				throw new BlogException("链接不能为空！");
			}
			if (MyUtils.checkempty(link.getLinkName())) {
				throw new BlogException("友链名称不能为空！");
			}
			ls.editLink(link);
			map.put("errno", "0");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errno", "1");
			map.put("msg", e.getMessage());
		}
		return map;
	}

	// ajax删友链
	@RequestMapping(value = "/admin/link/{linkId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deleteLink(@PathVariable(required = true) Integer linkId) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			// errno 为0,代表无错
			ls.deleteLink(linkId);
			map.put("errno", "0");
		} catch (Exception e) {
			// 出现异常
			e.printStackTrace();
			map.put("errno", "1");
			map.put("msg", e.getMessage());
		}
		return map;
	}
}
