package com.efrobot.weixin.collect.service;

import java.util.List;

import com.efrobot.weixin.baseapi.pojo.User;

public interface UserService {

	public int queryUserCount(User user);
	
	public User findUserByParams(User user);
	
	public List<User> findUserByParamsList(User user);
	
	public int saveUser(User user);
	
	public int updateUser(User user);
	
	public int updateUserByOpenid(User user);

}
