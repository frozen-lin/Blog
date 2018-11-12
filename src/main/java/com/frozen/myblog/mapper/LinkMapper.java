package com.frozen.myblog.mapper;

import java.util.List;

import com.frozen.myblog.pojo.Link;

public interface LinkMapper {
	 List<Link> queryLinkList();
	 
	void updateLink(Link link);

	void deleteLinkById(Integer linkId);

	void addLink(Link link);
}
