package com.efrobot.weixin.collect.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.efrobot.weixin.baseapi.AddressMapper;
import com.efrobot.weixin.baseapi.pojo.Address;
import com.efrobot.weixin.collect.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
	@Resource
	private AddressMapper addressMapper;
	@Override
	public int insertSelective(Address record) {
		return addressMapper.insertSelective(record);
	}
	@Override
	public  List<Address> getAddress(Address record){
		List<Address> list=addressMapper.getAddress(record);
		 return list;
	}
	@Override
	public int updateByPrimaryKey(Address record) {
		return addressMapper.updateByPrimaryKey(record);
	}
	@Override
	public int updateByPrimaryKeySelective(Address record) {
		return addressMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int updateAddressStatus(Address record) {
		return addressMapper.updateAddressStatus(record);
	}
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return addressMapper.deleteByPrimaryKey(id);
	}
	@Override
	public Address selectByPrimaryKey(Integer id){
		return addressMapper.selectByPrimaryKey(id);
	}
	
}
