package com.efrobot.weixin.collect.service;

import java.util.List;

import com.efrobot.weixin.baseapi.pojo.Area;
import com.efrobot.weixin.baseapi.pojo.Channel;
import com.efrobot.weixin.baseapi.pojo.FlightNum;
import com.efrobot.weixin.baseapi.pojo.Order;
import com.efrobot.weixin.baseapi.pojo.OrderStatus;
import com.efrobot.weixin.baseapi.pojo.OrderStatusRecord;


public interface OrderService {
	
	
	public List<Order> selectByParms(Order record) ;
	
	public int insertSelective(Order record);
	
	public int updateByPrimaryKey(Order record);
	
	public int updateByPrimaryKeySelective(Order record);
	
	public List<OrderStatus> selectSelectList(String selected);
	
	public List<Channel> getChannel(Channel record);
	
	public List<OrderStatusRecord> selectByparms(String exp1);
	
	public List<Area> getArea(Area record);
	
	public List<FlightNum> getFlightNum(FlightNum record);
	
	public  Order selectByPrimaryKey(Integer id);
	
	public  int updateByPrimaryKeySelective(OrderStatusRecord record);
	
	public  int insertSelective(OrderStatusRecord record);

}