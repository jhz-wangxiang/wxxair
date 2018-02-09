package com.efrobot.weixin.collect.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.efrobot.weixin.baseapi.pojo.Address;
import com.efrobot.weixin.collect.service.AddressService;
import com.efrobot.weixin.util.CommonUtil;

@RequestMapping("/v1/address")
@RestController
public class AddressController {
	@Autowired
	private AddressService addressService;
	
	
	@RequestMapping(value = "/insertAddress", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAddress(Address record) throws Exception {
		int result = -1;
		if(null==record.getUserid()){
			return CommonUtil.resultMsg("FAIL", "用户信息不存在!");
		}
		List<Address> list=addressService.getAddress(record);
		record.setStatus(1);
		if(list.size()==0){
			record.setStatus(1);
		}else{
			for(Address ad:list){
				if(null!=record.getStatus()&&"1".equals(record.getStatus())){
					record.setStatus(0);
				}
			}
		}
		result = addressService.insertSelective(record);
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1)
			return CommonUtil.resultMsg("SUCCESS", "信息插入功");
		else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}
	
	@RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAddress(Address record ) throws Exception {
		int result = -1;
		result = addressService.updateByPrimaryKeySelective(record);
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1)
			return CommonUtil.resultMsg("SUCCESS", "编辑信息成功");
		else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}
	@RequestMapping(value = "/updateAddressStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAddressStatus(Address record) throws Exception {
		int result = -1;
		record.setStatus(0);
		result = addressService.updateAddressStatus(record);
		record.setStatus(1);
		result = addressService.updateByPrimaryKeySelective(record);
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1)
			return CommonUtil.resultMsg("SUCCESS", "编辑信息成功");
		else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}
	@RequestMapping(value = "/deletAddress")
	@ResponseBody
	public Map<String, Object> deletAddress(Address record) throws Exception {
		int result = -1;
		Address record2=addressService.selectByPrimaryKey(record.getId());
		if(record2.getStatus()==0){
			result = addressService.deleteByPrimaryKey(record.getId());
		}else{
			result = addressService.deleteByPrimaryKey(record.getId());
			List<Address> list=addressService.getAddress(record);
			if(list.size()!=0){
				Address a=list.get(0);
				a.setStatus(1);
				result = addressService.updateByPrimaryKeySelective(a);
			}
		}
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的生产信息");
		} else if (result == 1)
			return CommonUtil.resultMsg("SUCCESS", "编辑生产信息成功");
		else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}
	
	@RequestMapping(value = "/getAddress", method = RequestMethod.POST)
	@ResponseBody
	public List<Address> getAddress(Address record) throws Exception {
		List<Address> list=addressService.getAddress(record);
		return list;
	}
	
	@RequestMapping(value = "/getAddressDetail", method = RequestMethod.POST)
	@ResponseBody
	public Address getAddressDetail(Integer id) throws Exception {
		Address adderss=addressService.selectByPrimaryKey(id);
		return adderss;
	}
}
