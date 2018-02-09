package com.efrobot.weixin.collect.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.efrobot.weixin.baseapi.AreaMapper;
import com.efrobot.weixin.baseapi.pojo.Area;
import com.efrobot.weixin.collect.service.AreaService;


@Service
public class AreaServiceImpl implements AreaService {

	@Resource
	private AreaMapper areaMapper;

	
	@Override
	public int insertSelective(Area record){
		return areaMapper.insertSelective(record);
	}
	@Override
	public int updateByPrimaryKeySelective(Area record){
		return areaMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int deleteByPrimaryKey(Integer id){
		return areaMapper.deleteByPrimaryKey(id);
	}
	@Override
	public List<Area> selectByParms(Area record){
		return areaMapper.selectByParms(record);
	}
	
}
