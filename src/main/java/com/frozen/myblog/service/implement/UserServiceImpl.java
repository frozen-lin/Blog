package com.frozen.myblog.service.implement;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.frozen.myblog.mapper.UserMapper;
import com.frozen.myblog.pojo.User;
import com.frozen.myblog.service.UserService;
import com.frozen.myblog.util.BlogException;
import com.frozen.myblog.util.MyUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper um;
	@Resource(name="redisTemplate")
	private RedisTemplate<String, Object> rt;

	@Override
	public User login(User user) throws BlogException {
		// 先从redis中查看该用户是否存在
		ValueOperations<String, Object> ops = rt.opsForValue();
		User t_user = (User) ops.get("username:" + user.getUsername());
		// 不存在,从数据库中查找,再存入redis
		if (t_user == null) {
			t_user = um.queryUserByUsername(user.getUsername());
		}
		// 找不到该用户
		if (t_user == null) {
			throw new BlogException("用户名不存在");
		} else {
			// 无论t_user是在redis中还是数据库中查到,覆盖过期时间,10天过期。
			ops.set("username:" + t_user.getUsername(), t_user, 10, TimeUnit.DAYS);
			// 校验密码
			if (t_user.getPassword().equals(user.getPassword())) {
				return t_user;
			} else {
				throw new BlogException("用户名或密码错误");
			}
		}
	}

	@Override
	public void register(User user) throws BlogException {
		boolean isExit = existUsername(user.getUsername());
		if (isExit) {
			// 若数据库中已存在该用户名,抛出异常
			throw new BlogException("用户名已存在");
		}
		// 无该用户,正常注册
		try {
			um.saveUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BlogException("注册异常");
		}
	}

	// 校验用户名是否存在
	@Override
	public boolean existUsername(String username) throws BlogException {
		if (MyUtils.checkempty(username)) {
			throw new BlogException("用户名不能为空");
		} else {
			// 先从redis中查看用户名是否存在
			ValueOperations<String, Object> ops = rt.opsForValue();
			User user = (User) ops.get("username:" + username);
			if (user != null)
				return true;
			// 不存在,再去查找数据库
			Integer i = um.query1ByUsername(username);
			// 若ID不存在,用户不存在,返回false
			if (i == null)
				return false;
			// 否则 返回true
			return true;
		}
	}

	@Override
	public User addVisitor(String username) {
		User user = new User();
		user.setUsername(username);
		// 确认该游客名是否存在,存在就使用,不存在则创建
		Integer i=um.query1ByUsername(username);
		if (i == null)
			um.saveVisitor(username);
		return user;
	}
	//查找用户总数量
	@Override
	public Integer queryUserCount() {
		return um.queryCount();
	}

}
