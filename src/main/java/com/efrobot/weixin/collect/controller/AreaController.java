package com.efrobot.weixin.collect.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.efrobot.weixin.baseapi.pojo.Area;
import com.efrobot.weixin.collect.service.AreaService;
import com.efrobot.weixin.util.CommonUtil;

@RequestMapping("/v1/area")
@Controller
public class AreaController {

	@Resource
	private AreaService areaService;
	
	
	@RequestMapping(value = "/insertArea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertArea(Area record) throws Exception {
		int result = -1;
		result = areaService.insertSelective(record);
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1){
			return CommonUtil.resultMsg("SUCCESS", "信息插入成功");
		}else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
		
	}
	
	@RequestMapping(value = "/updateArea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateArea(Area record) throws Exception {
		int result = -1;
		result = areaService.updateByPrimaryKeySelective(record);
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1){
			return CommonUtil.resultMsg("SUCCESS", "修改成功");
		}else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
		
	}
	
	@RequestMapping(value = "/deleteArea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteArea(Integer id) throws Exception {
		int result = -1;
		result = areaService.deleteByPrimaryKey(id);
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1){
			return CommonUtil.resultMsg("SUCCESS", "刪除成功");
		}else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}
	@RequestMapping(value = "/getArea", method = RequestMethod.POST)
	@ResponseBody
	public List<Area> getArea(Area record) throws Exception {
		List<Area> list = areaService.selectByParms(record);
		return list;
	}
}
