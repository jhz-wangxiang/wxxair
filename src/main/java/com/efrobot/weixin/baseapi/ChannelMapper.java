package com.efrobot.weixin.baseapi;
import java.util.List;

import com.efrobot.weixin.baseapi.pojo.Channel;

public interface ChannelMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Channel record);

    int insertSelective(Channel record);

    Channel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Channel record);

    int updateByPrimaryKey(Channel record);
    
    List<Channel> getChannel(Channel channel);
}