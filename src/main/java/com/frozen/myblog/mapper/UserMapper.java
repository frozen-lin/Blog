package com.frozen.myblog.mapper;

import com.frozen.myblog.pojo.User;

public interface UserMapper {
	
	User queryUserByUsername(String username);
	//检查数据库是否存在该用户名,不存在返回null
	Integer query1ByUsername(String username);
	
	void saveUser(User user);

	void saveVisitor(String username);
	
	Integer queryCount();
}
