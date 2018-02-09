package com.efrobot.weixin.collect.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.efrobot.weixin.baseapi.AreaMapper;
import com.efrobot.weixin.baseapi.ChannelMapper;
import com.efrobot.weixin.baseapi.FlightNumMapper;
import com.efrobot.weixin.baseapi.OrderMapper;
import com.efrobot.weixin.baseapi.OrderStatusMapper;
import com.efrobot.weixin.baseapi.OrderStatusRecordMapper;
import com.efrobot.weixin.baseapi.pojo.Area;
import com.efrobot.weixin.baseapi.pojo.Channel;
import com.efrobot.weixin.baseapi.pojo.FlightNum;
import com.efrobot.weixin.baseapi.pojo.Order;
import com.efrobot.weixin.baseapi.pojo.OrderStatus;
import com.efrobot.weixin.baseapi.pojo.OrderStatusRecord;
import com.efrobot.weixin.collect.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service
public class OrderServiceImpl implements OrderService {

	@Resource
	private OrderMapper orderMapper;
	@Resource
	private OrderStatusMapper orderStatusMapper;
	@Resource
	private ChannelMapper channelMapper;
	@Resource
	private AreaMapper areaMapper;
	@Resource
	private FlightNumMapper flightNumMapper;
	@Resource
	private OrderStatusRecordMapper orderStatusRecordMapper;

	
	@Override
	public List<Order> selectByParms(Order record) {
		return orderMapper.selectByParms(record);
	}
	
	@Override
	public int insertSelective(Order record) {
		return orderMapper.insertSelective(record);
	}
	
	@Override
	public int updateByPrimaryKey(Order record) {
		return orderMapper.updateByPrimaryKey(record);
	}
	@Override
	public int updateByPrimaryKeySelective(Order record) {
		return orderMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public List<OrderStatus> selectSelectList(String selected) {
		if(selected==null){
			return orderStatusMapper.selectAll();
		}else{
			return orderStatusMapper.selectSelectList(Arrays.asList(selected.split(",")));
		}
	}
	@Override
	public  List<Channel> getChannel(Channel record) {
		return channelMapper.getChannel(record);
	}
	@Override
	public  List<Area> getArea(Area record) {
		return areaMapper.getArea(record);
	}
	@Override
	public  List<FlightNum> getFlightNum(FlightNum record) {
		return flightNumMapper.getFlightNum(record);
	}
	@Override
	public  Order selectByPrimaryKey(Integer id) {
		return orderMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public  int updateByPrimaryKeySelective(OrderStatusRecord record) {
		return orderStatusRecordMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public  int insertSelective(OrderStatusRecord record) {
		return orderStatusRecordMapper.insertSelective(record);
	}
	public List<OrderStatusRecord> selectByparms(String exp1){
		return orderStatusRecordMapper.selectByparms(exp1);
	}
	
}
