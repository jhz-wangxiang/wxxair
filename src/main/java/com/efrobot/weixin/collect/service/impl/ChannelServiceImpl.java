package com.efrobot.weixin.collect.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.efrobot.weixin.baseapi.ChannelMapper;
import com.efrobot.weixin.baseapi.pojo.Channel;
import com.efrobot.weixin.collect.service.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Resource
	private ChannelMapper channelMapper;

	
	@Override
	public int insertSelective(Channel record){
		return channelMapper.insertSelective(record);
	}
	@Override
	public int updateByPrimaryKeySelective(Channel record){
		return channelMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int deleteByPrimaryKey(Integer id){
		return channelMapper.deleteByPrimaryKey(id);
	}
//	
//	@Override
//	public List<SysRole> getRoleAll(SysRole record){
//		List<SysRole> list= sysRoleMapper.selectByParms(record);
//		return list;
//	}
	
}
