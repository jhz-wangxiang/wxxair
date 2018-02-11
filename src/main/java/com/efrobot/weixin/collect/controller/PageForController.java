package com.efrobot.weixin.collect.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/page")
@Controller
public class PageForController {

	@RequestMapping(value = "/orderStepOne")
	public String orderStepOne(HttpServletRequest request) {
		request.setAttribute("navName", "下单第一步");
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
