package com.efrobot.weixin.collect.service;

import com.efrobot.weixin.baseapi.pojo.Channel;

public interface ChannelService {
	

	public int insertSelective(Channel record);
//	
	public int updateByPrimaryKeySelective(Channel record);
	
	public int deleteByPrimaryKey(Integer id);
//	
}