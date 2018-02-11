package com.efrobot.weixin.collect.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.efrobot.weixin.baseapi.pojo.User;
import com.efrobot.weixin.collect.service.OrderService;
import com.efrobot.weixin.collect.service.UserService;
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
//		 openid = "ofWtHvxtcgT1InB4sE0AvE6eMt4c";
		session.setAttribute("openid", openid);
		if (null == openid || "".equals(openid)) {
			openid = WeixinUtil.getopenidAction(request);// 获得openid
			if (null == openid || "".equals(openid)) {
				throw new RuntimeException("数据异常");
			}
			session.setAttribute("openid", openid);
		}
		User record=new User();
		record.setOpenid(openid);
		List<User> list=userService.selectByUser(record);	
		if(list.size()!=0){
			request.setAttribute("user",list.get(0));
		}
		return "orderStepOne";
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
}
