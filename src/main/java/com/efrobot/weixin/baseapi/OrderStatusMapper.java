package com.efrobot.weixin.baseapi;

import java.util.List;

import com.efrobot.weixin.baseapi.pojo.OrderStatus;

public interface OrderStatusMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrderStatus record);

    int insertSelective(OrderStatus record);

    OrderStatus selectByPrimaryKey(Integer id);
    
    List<OrderStatus> selectSelectList(List<String> list);
    
    List<OrderStatus> selectAll();

    int updateByPrimaryKeySelective(OrderStatus record);

    int updateByPrimaryKey(OrderStatus record);
}