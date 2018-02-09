package com.efrobot.weixin.collect.service;

import java.util.List;

import com.efrobot.weixin.baseapi.pojo.Address;


public interface AddressService {
	 public int insertSelective(Address record);
	 public  List<Address> getAddress(Address record);
	 
	 public int updateByPrimaryKey(Address record);
	 
	 public int updateByPrimaryKeySelective(Address record);
	 
	 public int updateAddressStatus(Address record);
	 
	 public int deleteByPrimaryKey(Integer id);
	 
	 public Address selectByPrimaryKey(Integer id);
	 
	
}