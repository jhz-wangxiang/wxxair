package com.efrobot.weixin.baseapi;

import java.util.List;

import com.efrobot.weixin.baseapi.pojo.Area;
import com.efrobot.weixin.baseapi.pojo.FlightNum;

public interface AreaMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);
    
    List<Area> getArea(Area record);
    
    List<Area> selectByParms(Area record);
}