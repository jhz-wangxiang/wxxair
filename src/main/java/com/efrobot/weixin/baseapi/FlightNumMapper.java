package com.efrobot.weixin.baseapi;

import java.util.List;

import com.efrobot.weixin.baseapi.pojo.FlightNum;

public interface FlightNumMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(FlightNum record);

    int insertSelective(FlightNum record);

    FlightNum selectByPrimaryKey(Integer id);
    
    List<FlightNum> getFlightNum(FlightNum record);

    int updateByPrimaryKeySelective(FlightNum record);

    int updateByPrimaryKey(FlightNum record);
    
    List<FlightNum> selectByParms(FlightNum record);
}