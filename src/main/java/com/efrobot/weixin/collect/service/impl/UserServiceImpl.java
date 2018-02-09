package com.efrobot.weixin.collect.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.efrobot.weixin.baseapi.OrderMapper;
import com.efrobot.weixin.baseapi.UserMapper;
import com.efrobot.weixin.baseapi.pojo.User;
import com.efrobot.weixin.collect.service.UserService;


@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper userMapper;
	@Resource
	private OrderMapper orderMapper;

	@Override
	public List<User> selectByUser(User record) {
		return userMapper.selectByUser(record);
	}
	
	@Override
	public int insertSelective(User record) {
		return userMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}
	

}
