package com.efrobot.weixin.collect.service;

import java.util.List;
import java.util.Map;

import com.efrobot.weixin.baseapi.pojo.FlightNum;


public interface FlightNumService {

	public int insertSelective(FlightNum record);
	
	public int updateByPrimaryKeySelective(FlightNum record);
	
	public int deleteByPrimaryKey(Integer id);
	
	public List<FlightNum> selectByParms(FlightNum record);
	
	public Map<String, Object> importFlightNum(List<String[]> list);
}