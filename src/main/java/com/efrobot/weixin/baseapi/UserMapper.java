package com.efrobot.weixin.baseapi;

import java.util.List;

import com.efrobot.weixin.baseapi.pojo.User;

public interface UserMapper {

	List<User> queryUser(User user);

	int saveUser(User user);
	
	int updateUserByOpenid(User user);
	
	int updateUser(User user);
}
