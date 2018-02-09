package com.efrobot.weixin.baseapi;

import java.util.List;

import com.efrobot.weixin.baseapi.pojo.OrderStatusRecord;

public interface OrderStatusRecordMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrderStatusRecord record);

    int insertSelective(OrderStatusRecord record);

    OrderStatusRecord selectByPrimaryKey(Integer id);
    
    List<OrderStatusRecord> selectByparms(String exp1);

    int updateByPrimaryKeySelective(OrderStatusRecord record);

    int updateByPrimaryKey(OrderStatusRecord record);
}