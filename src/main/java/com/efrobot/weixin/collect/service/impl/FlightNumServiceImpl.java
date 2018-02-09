package com.efrobot.weixin.collect.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.efrobot.weixin.baseapi.FlightNumMapper;
import com.efrobot.weixin.baseapi.pojo.FlightNum;
import com.efrobot.weixin.collect.service.FlightNumService;


@Service
public class FlightNumServiceImpl implements FlightNumService {

	@Resource
	private FlightNumMapper flightNumMapper;
	
	@Override
	public int insertSelective(FlightNum record){
		return flightNumMapper.insertSelective(record);
	}
	
	@Override
	public List<FlightNum> selectByParms(FlightNum record){
		return flightNumMapper.selectByParms(record);
	}
	@Override
	public int updateByPrimaryKeySelective(FlightNum record){
		return flightNumMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int deleteByPrimaryKey(Integer id){
		return flightNumMapper.deleteByPrimaryKey(id);
	}
	@Override
	public Map<String, Object> importFlightNum(List<String[]> list) {
		Map<String, Object> result = new HashMap<>();
		for (String[] arrs : list) {
			FlightNum manager = new FlightNum();
			try {
				manager.setFlightNum(arrs[0]);//arrs[64] == null ? null : new SimpleDateFormat("yyyy/MM/dd").parse(arrs[64])
				manager.setCompay(arrs[1]);;
				manager.setStartPlace(arrs[2]);
				manager.setEndPlace(arrs[3]);
				manager.setStartTime(arrs[4] == null ? null : new SimpleDateFormat("yyyy/MM/dd").parse(arrs[4]));
				manager.setEndTime(arrs[5] == null ? null : new SimpleDateFormat("yyyy/MM/dd").parse(arrs[5]));
				manager.setStartHour(arrs[6]);
				manager.setEndHour(arrs[7]);
				manager.setExp1(arrs[8]);
				
				FlightNum flightNum = new FlightNum();
				flightNum.setFlightNum(arrs[0]);
				List<FlightNum> list2=flightNumMapper.selectByParms(flightNum);
				if(list2.size()==0){
					flightNumMapper.insertSelective(manager);
				}else
				{
					manager.setId(list2.get(0).getId());
					flightNumMapper.updateByPrimaryKeySelective(manager);
				}
				result.put("resultCode", "SUCCESS");// 状态码
				result.put("msg", "导入成功");
			} catch (Exception e) {
				result.put("resultCode", "FAIL");// 状态码
				result.put("msg", "失败,是否有重复的航班号,是否格式不正确");
			}
		}
		return result;
	}
}
