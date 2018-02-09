package com.efrobot.weixin.collect.service;

import java.util.List;

import com.efrobot.weixin.baseapi.pojo.Area;


public interface AreaService {
	
//
	public int insertSelective(Area record);
//	
	public int updateByPrimaryKeySelective(Area record);
//	
	public int deleteByPrimaryKey(Integer id);
	
	public List<Area> selectByParms(Area record);
}