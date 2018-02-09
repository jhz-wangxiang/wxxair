package com.efrobot.weixin.collect.service;

import java.util.List;

import com.efrobot.weixin.baseapi.pojo.User;


public interface UserService {
	public  List<User> selectByUser(User record);	
	
	public int insertSelective(User record);

	public int updateByPrimaryKeySelective(User record);
	
}