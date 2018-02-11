package com.efrobot.weixin.collect.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.efrobot.weixin.baseapi.pojo.User;
import com.efrobot.weixin.collect.service.OrderService;
import com.efrobot.weixin.collect.service.UserService;
import com.efrobot.weixin.util.CommonUtil;
import com.efrobot.weixin.util.WeixinUtil;

@RequestMapping("/v1/page")
@Controller
public class PageForController {
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/orderStepOne")
	public String orderStepOne(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
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
		User record = new User();
		record.setOpenid(openid);
		List<User> list = userService.selectByUser(record);
		if (list.size() != 0) {
			request.setAttribute("user", list.get(0));
		}
		// 微信分享 modify by king ---start
		WeixinUtil.wxConfig(request, response);
		return "orderStepOne";
	}
	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertUser(User record, HttpSession session) throws Exception {
		int result = -1;
		String openid = (String) session.getAttribute("openid");
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
	@RequestMapping(value = "/orderDeal")
	public String orderDeal(HttpServletRequest request) {
		request.setAttribute("navName", "下单");
		return "orderDeal";
	}

	@RequestMapping(value = "/userInfo")
	public String userInfo(HttpServletRequest request) {
		request.setAttribute("navName", "个人信息");
		return "userInfo";
	}

	@RequestMapping(value = "/orderList")
	public String orderList(HttpServletRequest request) {
		request.setAttribute("navName", "订单列表");
		return "orderList";
	}

	@RequestMapping(value = "/orderDetail")
	public String orderDetail(HttpServletRequest request) {
		request.setAttribute("navName", "订单详情");
		return "orderDetail";
	}
	@RequestMapping(value = "/orderPay")
	public String orderPay(HttpServletRequest request) {
		request.setAttribute("navName", "订单支付");
		return "orderPay";
	}
	@RequestMapping(value = "/orderCancel")
	public String orderCancel(HttpServletRequest request) {
		request.setAttribute("navName", "取消订单");
		return "orderCancel";
	}
	@RequestMapping(value = "/orderAddressList")
	public String orderAddressList(HttpServletRequest request) {
		request.setAttribute("navName", "收货地址");
		return "orderAddressList";
	}
	@RequestMapping(value = "/orderAddressNew")
	public String orderAddressNew(HttpServletRequest request) {
		request.setAttribute("navName", "新增地址");
		return "orderAddressNew";
	}
}
