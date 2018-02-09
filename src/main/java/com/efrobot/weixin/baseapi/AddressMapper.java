package com.efrobot.weixin.baseapi;

import java.util.List;

import com.efrobot.weixin.baseapi.pojo.Address;


public interface AddressMapper {
 
    int deleteByPrimaryKey(Integer id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Integer id);
    
    List<Address> getAddress(Address record);

    int updateByPrimaryKeySelective(Address record);
    
    int updateAddressStatus(Address record);

    int updateByPrimaryKey(Address record);
}