package com.efrobot.weixin.collect.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.efrobot.weixin.baseapi.pojo.Channel;
import com.efrobot.weixin.collect.service.ChannelService;
import com.efrobot.weixin.util.CommonUtil;

@RequestMapping("/v1/channel")
@Controller
public class ChannelController {

	@Resource
	private ChannelService channelService;
	
	
	@RequestMapping(value = "/insertChannel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertChannel(Channel record) throws Exception {
		int result = -1;
		record.setExp1("1");
		result = channelService.insertSelective(record);
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1){
			return CommonUtil.resultMsg("SUCCESS", "信息插入成功");
		}else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}
	
	@RequestMapping(value = "/updateChannel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateRole(Channel record) throws Exception {
		int result = -1;
		result = channelService.updateByPrimaryKeySelective(record);
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1){
			return CommonUtil.resultMsg("SUCCESS", "修改成功");
		}else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
		
	}
	
	@RequestMapping(value = "/deleteChannel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteChannel(Channel record) throws Exception {
		int result = -1;
		record.setExp1("0");
		result = channelService.updateByPrimaryKeySelective(record);
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1){
			return CommonUtil.resultMsg("SUCCESS", "修改成功");
		}else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}
//	
//	@RequestMapping(value = "/getRoleAll", method = RequestMethod.POST)
//	@ResponseBody
//	public List<SysRole> getRoleAll(SysRole record) throws Exception {
//		List<SysRole> list=sysRoleService.getRoleAll(record);
//		return list;
//	}
}
