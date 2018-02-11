package com.efrobot.weixin.collect.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.efrobot.weixin.baseapi.pojo.Address;
import com.efrobot.weixin.baseapi.pojo.Order;
import com.efrobot.weixin.baseapi.pojo.User;
import com.efrobot.weixin.collect.service.AddressService;
import com.efrobot.weixin.collect.service.OrderService;
import com.efrobot.weixin.collect.service.UserService;
import com.efrobot.weixin.util.CommonUtil;
import com.efrobot.weixin.util.WeixinUtil;

@RequestMapping("/v1/user")
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AddressService addressService;
	@RequestMapping(value = "/getAddressByUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAddressByUser(User user) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		Address record=new Address();
		record.setUserid(user.getId());
		List<Address> adressList=addressService.getAddress(record);
		for(Address record2:adressList){
			if(record2.getStatus()==1){
				map.put("address", record2);
			}
		}
		map.put("resultCode", "SUCCESS");
		map.put("addressList", adressList);
		return map;
	}
	
	@RequestMapping(value = "/getAddressAndUserByPhone", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAddressAndUserByPhone(User user) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		if(null==user.getPhone()||"".equals(user.getPhone())){
			map.put("resultCode", "FAIL");
			map.put("msg", "手机号不能为空");		
			return map;
		}
		Integer userid=0;
		List<Address> adressList=new ArrayList<>();
		User user2=new User();
		user2.setPhone(user.getPhone());
		List<User> list=userService.selectByUser(user2);
		if(list.size()==0){
			if(user.getName()!=null&&!"".equals(user.getName())){
				user.setExp1("是");
			}else{
				user.setExp1("未");
			}
			userService.insertSelective(user);
			userid=user.getId();
			map.put("user", user);	
		}else{
			userid=list.get(0).getId();
			Address record=new Address();
			record.setUserid(userid);
			adressList=addressService.getAddress(record);
			map.put("user", list.get(0));		
		}
		for(Address record2:adressList){
			if(record2.getStatus()==1){
				map.put("address", record2);
			}
		}
		map.put("resultCode", "SUCCESS");
		map.put("addressList", adressList);
		map.put("userid", userid);		
		return map;
	}
	
	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertUser(User record, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		int result = -1;
		request.setAttribute("navName", "下单第一步");
		String openid = (String) session.getAttribute("openid");
		openid = "ofWtHvxtcgT1InB4sE0AvE6eMt4c";
		session.setAttribute("openid", openid);
		if (null == openid || "".equals(openid)) {
			openid = WeixinUtil.getopenidAction(request);// 获得openid
			if (null == openid || "".equals(openid)) {
				throw new RuntimeException("数据异常");
			}
			session.setAttribute("openid", openid);
		}
		User user2=new User();
		user2.setPhone(record.getPhone());
		List<User> list=userService.selectByUser(user2);
		record.setOpenid(openid);
		if(list.size()!=0){
			result = userService.updateByPrimaryKeySelective(record);
		}else{
			if(record.getName()!=null&&!"".equals(record.getName())){
				record.setExp1("是");
			}else{
				record.setExp1("未");
			}
			result = userService.insertSelective(record);
		}
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1){
			return CommonUtil.resultMsg("SUCCESS", "信息插入功");
		}else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
		
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUser(User record ) throws Exception {
		int result = -1;
		if(record.getName()!=null&&!"".equals(record.getName())){
			record.setExp1("是");
		}else{
			record.setExp1("未");
		}
		User user2=new User();
		user2.setPhone(record.getPhone());
		List<User> list=userService.selectByUser(user2);
		if(list.size()>0){
			User us=list.get(0);
			if(us.getId()!=record.getId()){
				return CommonUtil.resultMsg("FAIL", "用户信息已经存在!!!");
			}
		}
		result = userService.updateByPrimaryKeySelective(record);
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1)
			return CommonUtil.resultMsg("SUCCESS", "编辑信息成功");
		else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}
	
	@RequestMapping(value = "/getUserDetail", method = RequestMethod.POST)
	@ResponseBody
	public User getUserDetail(User record) throws Exception {
		List<User> list = userService.selectByUser(record);
		User us=list.get(0);
		Order order=new Order();
		String ids="1,2,3,4,5";
		order.setListStatus(Arrays.asList(ids.split(",")));
		order.setUserId(us.getId());
		List<Order> orlist=orderService.selectByParms(order);
		if(orlist.size()!=0){
			us.setExp2("有");
		}else{
			us.setExp2("未");
		}
		if(us.getName()!=null&&!"".equals(us.getName())){
			us.setExp1("是");
		}else{
			us.setExp1("未");
		}
		return us;
	}
	
}
