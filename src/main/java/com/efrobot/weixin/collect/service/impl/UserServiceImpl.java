package com.efrobot.weixin.collect.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efrobot.weixin.baseapi.UserMapper;
import com.efrobot.weixin.baseapi.pojo.User;
import com.efrobot.weixin.collect.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
    private UserMapper userMapper;
	public int queryUserCount(User user) { 
		List<User> userlist=userMapper.queryUser(user);
		if(userlist.size()==0){
			return 0;
		}
		return 1;
	}
	
	public User findUserByParams(User user){ 
		List<User> userlist=userMapper.queryUser(user);
		if(userlist.size()!=0){
			return userlist.get(0);
		}
		return null;
	}
	
	public List<User> findUserByParamsList(User user){ 
		return userMapper.queryUser(user);
	}

	public int saveUser(User user) { 
		return userMapper.saveUser(user);
	}
	public int updateUser(User user) { 
		return userMapper.updateUser(user);
	}
	
	public int updateUserByOpenid(User user) { 
		return userMapper.updateUserByOpenid(user);
	}

}
