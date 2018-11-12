package com.frozen.myblog.service;

import com.frozen.myblog.pojo.User;
import com.frozen.myblog.util.BlogException;

public interface UserService {
	User login(User user) throws BlogException;

	void register(User user) throws BlogException;

	boolean existUsername(String username) throws BlogException;

	User addVisitor(String username);

	Integer queryUserCount();
}
