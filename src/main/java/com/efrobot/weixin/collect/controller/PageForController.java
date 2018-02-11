package com.efrobot.weixin.collect.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.efrobot.weixin.baseapi.pojo.FlightNum;
import com.efrobot.weixin.baseapi.pojo.Order;
import com.efrobot.weixin.baseapi.pojo.User;
import com.efrobot.weixin.collect.service.AreaService;
import com.efrobot.weixin.collect.service.FlightNumService;
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
	@Autowired
	private AreaService areaService;

	@Autowired
	private FlightNumService flightNumService;
	@RequestMapping(value = "/orderStepOne")
	public String orderStepOne(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		request.setAttribute("navName", "下单第一步");
		String openid = (String) session.getAttribute("openid");
		// openid = "ofWtHvxtcgT1InB4sE0AvE6eMt4c";
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
	@RequestMapping(value = "/checkflightNum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkflightNum(Order record) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if ("".equals(record.getNowTimeStr()) || null == record.getNowTimeStr()) {
			return CommonUtil.resultMsg("FAIL", "航班日期不能为空 ");
		}
		if ("".equals(record.getFlightNum()) || null == record.getFlightNum()) {
			return CommonUtil.resultMsg("FAIL", "航班号不能为空 ");
		}
		FlightNum f = new FlightNum();
		f.setFlightNum(record.getFlightNum());
		List<FlightNum> list = flightNumService.selectByParms(f);
		if (list.size() == 0) {
			return CommonUtil.resultMsg("FAIL", "对不起，你航班的目的地还未开通此项服务。 ");
		}

		FlightNum flightNum = list.get(0);
		if (record.getNowTime().getTime() < flightNum.getEndTime().getTime()
				&& record.getNowTime().getTime() >= flightNum.getStartTime().getTime()) {
		} else {
			return CommonUtil.resultMsg("FAIL", "对不起，你航班的目的地还未开通此项服务。 ");
		}
		// 校验航班时间
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

		String time = sdf.format(record.getNowTime()) + " " + flightNum.getEndHour() + ":00:00";

		Date t = sdf2.parse(time);
		Date n = new Date();
		if (!CommonUtil.jisuan(n, t)) {
			return CommonUtil.resultMsg("FAIL", "航班未开放，请于落地前 24 小时内下单");//
		}
		return CommonUtil.resultMsg("SUCCESS", "校验成功");
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
