package com.frozen.myblog.service;

import java.util.List;

import com.frozen.myblog.pojo.Link;

public interface LinkService {

	List<Link> queryLink();

	void editLink(Link link);

	void deleteLink(Integer linkId);

	void addLink(Link link);

	Integer queryLinkCount();
}
