package com.efrobot.weixin.collect.controller;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import com.efrobot.toolkit.util.xml.XMLParser;
import com.efrobot.weixin.baseapi.pojo.Address;
import com.efrobot.weixin.baseapi.pojo.Area;
import com.efrobot.weixin.baseapi.pojo.Channel;
import com.efrobot.weixin.baseapi.pojo.FlightNum;
import com.efrobot.weixin.baseapi.pojo.Order;
import com.efrobot.weixin.baseapi.pojo.OrderStatusRecord;
import com.efrobot.weixin.baseapi.pojo.User;
import com.efrobot.weixin.collect.bean.WxPay;
import com.efrobot.weixin.collect.service.AddressService;
import com.efrobot.weixin.collect.service.AreaService;
import com.efrobot.weixin.collect.service.FlightNumService;
import com.efrobot.weixin.collect.service.OrderService;
import com.efrobot.weixin.collect.service.UserService;
import com.efrobot.weixin.util.CommonUtil;
import com.efrobot.weixin.util.SerialNum;
import com.efrobot.weixin.util.WXKeys;
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
	private AddressService addressService;

	@Autowired
	private FlightNumService flightNumService;

	@RequestMapping(value = "/orderStepOne")
	public String orderStepOne(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		request.setAttribute("navName", "下单第一步");
		String openid = (String) session.getAttribute("openid");
//		openid = "oEzzKvw3ZWWJwu0YNFQZP0Hi_dCo";
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
			// request.setAttribute("user", list.get(0));
			request.setAttribute("name", list.get(0).getName());
			request.setAttribute("phone", list.get(0).getPhone());
		} else {
			request.setAttribute("name", "");
			request.setAttribute("phone", "");
		}
		// 微信分享 modify by king ---start
		WeixinUtil.wxConfig(request, response);
		return "orderStepOne";
	}

	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertUser(User record, HttpSession session) throws Exception {
		String openid = (String) session.getAttribute("openid");
		User user2 = new User();
		user2.setOpenid(openid);
		List<User> list = userService.selectByUser(user2);
		record.setOpenid(openid);
		if (list.size() == 0) {
			if (record.getName() != null && !"".equals(record.getName())) {
				record.setExp1("是");
			} else {
				record.setExp1("未");
			}
			userService.insertSelective(record);
		}
			return CommonUtil.resultMsg("SUCCESS", "信息插入功");

	}

	@RequestMapping(value = "/checkflightNum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkflightNum(Order record) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
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
			return CommonUtil.resultMsg("FAIL", "对不起，你航班还没有开通。 ");
		}
		// 校验航班时间
		String endHour="";
		if (flightNum.getEndHour().length() == 2) {
			endHour= flightNum.getEndHour() + ":00:00";
		} else {
			endHour= flightNum.getEndHour() + ":00";
		}
		String time = sdf.format(record.getNowTime()) + " " + endHour;

		Date t = sdf2.parse(time);
		Date n = new Date();
//		if (!CommonUtil.jisuan(n, t)) {
//			return CommonUtil.resultMsg("FAIL", "航班未开放，请于落地前 24 小时内下单");//
//		}
		return CommonUtil.resultMsg("SUCCESS", "校验成功");
	}

	@RequestMapping(value = "/getPrice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPrice(Order record) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 价格计算
		float c = 10;
		float a = 10;
		int p = 100;
		if (null != record.getChannelId()) {// 渠道
			Channel ch = new Channel();
			ch.setId(record.getChannelId());
			List<Channel> listch = orderService.getChannel(ch);
			c = listch.get(0).getDiscount().longValue();
		}
		if (null != record.getAreaId()) {// 區域
			Area ar = new Area();
			ar.setId(record.getAreaId());
			List<Area> listch = areaService.selectByParms(ar);
			a = listch.get(0).getDiscount().longValue();
			p = listch.get(0).getPrice();
		}

		int num = Integer.parseInt(record.getBaggageNum());
		float paid = (float) ((p + (num - 1) * p * 0.1 * a) * 0.1 * c);
		record.setTotalFee(new BigDecimal(num * p));
		record.setPaidFee(new BigDecimal(paid));

		map.put("resultCode", "SUCCESS");
		map.put("ChannelDiscount", c);// 渠道折扣
		map.put("AreaDiscount", a);// 区域折扣,第二件起的折扣
		map.put("num", num);// 礼包数量
		map.put("price", p);// 单间
		map.put("totalFee", num * p);// 总额
		map.put("paid", paid);// 实际支付价格
		return map;
	}

	@RequestMapping(value = "/insertOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertOrder(Order record,HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		int result = -1;
		String openid = (String) session.getAttribute("openid");
		User user = new User();
		user.setOpenid(openid);
		List<User> listuser = userService.selectByUser(user);
		record.setUserId(listuser.get(0).getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat mmdd = new SimpleDateFormat("MMdd");
		String datestr = sdf.format(new Date());
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
		if (record.getNowTime().getTime() <= flightNum.getEndTime().getTime()
				&& record.getNowTime().getTime() >= flightNum.getStartTime().getTime()) {
		} else {
			return CommonUtil.resultMsg("FAIL", "对不起，你航班还没有开通。 ");
		}
		// 校验航班时间
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

		String endHour="";
		if (flightNum.getEndHour().length() == 2) {
			endHour= flightNum.getEndHour() + ":00:00";
		} else {
			endHour= flightNum.getEndHour() + ":00";
		}
		String time = sdf.format(record.getNowTime()) + " " + endHour;

		Date t = sdf2.parse(time);
		Date n = new Date();
//		if (!CommonUtil.jisuan(n, t)) {
//			return CommonUtil.resultMsg("FAIL", "航班未开放，请于落地前 24 小时内下单");// 订单可在航班计划落地时间前
//																		// 24
//																		// 小时内下单，如早下单，则提示
//		}
		String zm = flightNum.getExp1();
		String orderNo = zm + datestr + mmdd.format(record.getNowTime()) + record.getFlightNum()
				+ SerialNum.getSystemManageOrder();
		// 异常处理
		record.setOrderStatus(1);// 支付变成2
		record.setPayStatus("未支付");
		record.setCreateDate(new Date());
		record.setOrderNo(orderNo);
		record.setSingleWay("微信下单");
		record.setAbnormalStatus("否");
		record.setOperator("微信下单");
		record.setRegisterName(record.getName());
		record.setRegisterPhone(record.getPhone());
		// 价格计算
		float c = 10;
		float a = 10;
		int p = 100;
		if (null != record.getChannelId()) {// 渠道
			Channel ch = new Channel();
			ch.setId(record.getChannelId());
			List<Channel> listch = orderService.getChannel(ch);
			c = listch.get(0).getDiscount().longValue();
		}
		if (null != record.getAreaId()) {// 區域
			Area ar = new Area();
			ar.setId(record.getAreaId());
			List<Area> listch = areaService.selectByParms(ar);
			a = listch.get(0).getDiscount().longValue();
			p = listch.get(0).getPrice();
		}

		int num = Integer.parseInt(record.getBaggageNum());
		float paid = (float) ((p + (num - 1) * p * 0.1 * a) * 0.1 * c);
		record.setTotalFee(new BigDecimal(num * p));
		record.setPaidFee(new BigDecimal(paid));
		result = orderService.insertSelective(record);
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1) {
			setHistory("提交订单", orderNo, "");
			Map<String,Object> map=CommonUtil.resultMsg("SUCCESS", "订单创建成功!");
			map.put("orderNo", orderNo);
			return map;
		} else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}

	public int setHistory(String remark, String orderNo, String reason) {
		OrderStatusRecord orderStatusRecord = new OrderStatusRecord();
		orderStatusRecord.setRemark(remark);
		orderStatusRecord.setExp1(orderNo);
		orderStatusRecord.setExp2(reason);
		orderStatusRecord.setCreateDate(new Date());
		orderStatusRecord.setStatus(2);// 1-后台,2-客户
		int result = orderService.insertSelective(orderStatusRecord);
		return result;

	}


	@RequestMapping(value = "/orderDeal")
	public String orderDeal(HttpServletRequest request) {
		request.setAttribute("navName", "下单");
		return "orderDeal";
	}

	@RequestMapping(value = "/userInfo")
	public String userInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		request.setAttribute("navName", "个人信息");
		String openid = (String) session.getAttribute("openid");
		openid = "oEzzKvw3ZWWJwu0YNFQZP0Hi_dCo";
		session.setAttribute("openid", openid);
		if (null == openid || "".equals(openid)) {
			openid = WeixinUtil.getopenidAction(request);// 获得openid
			if (null == openid || "".equals(openid)) {
				throw new RuntimeException("数据异常");
			}
			session.setAttribute("openid", openid);
		}
		User user = new User();
		user.setOpenid(openid);
		List<User> list = userService.selectByUser(user);
		if (list.size() != 0) {
			request.setAttribute("name", list.get(0).getName());
			request.setAttribute("phone", list.get(0).getPhone());
		} else {
			request.setAttribute("name", "");
			request.setAttribute("phone", "");
		}
		return "userInfo";
	}

	@RequestMapping(value = "/orderList")
	public String orderList(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		request.setAttribute("navName", "订单列表");
		String openid = (String) session.getAttribute("openid");
//		openid = "oEzzKvw3ZWWJwu0YNFQZP0Hi_dCo";
		session.setAttribute("openid", openid);
		if (null == openid || "".equals(openid)) {
			openid = WeixinUtil.getopenidAction(request);// 获得openid
			if (null == openid || "".equals(openid)) {
				throw new RuntimeException("数据异常");
			}
			session.setAttribute("openid", openid);
		}
		return "orderList";
	}
	/**
	 * 获取用户购买产品支付信息参数
	 * 
	 * @author wangxiangxiang
	 */
	@RequestMapping({ "/getPackage" })
	public void getPackage(String orderNo,HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {
		Order record=new Order();
		record.setOrderNo(orderNo);
		List<Order> orderList = orderService.selectByParms(record);
		String openid = (String) session.getAttribute("openid");
		WxPay tpWxPay = new WxPay();
		tpWxPay.setBody("行李到家");
		tpWxPay.setOrderId(orderNo);
		tpWxPay.setSpbillCreateIp("127.0.0.1");
		tpWxPay.setOpenId(openid);
		tpWxPay.setTotalFee("0.01");
//		tpWxPay.setTotalFee(orderList.get(0).getPaidFee().toString());
		String finaPackage = WeixinUtil.getPackage(tpWxPay, WXKeys.WX_PYA_URL);
		PrintWriter out;
		out = response.getWriter();
		out.print(finaPackage);
	}
	/**
	 * 用户购买产品支付成功后的回调
	 * 
	 * @author wangxiangxiang
	 */
	@RequestMapping({ "/storeNotify" })
	public void storeNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String notityXml = "";
			String resXml = "";
			String inputLine;
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml = notityXml + inputLine;
			}
			request.getReader().close();
			// log.info("接收到的报文：" + notityXml);
			if (notityXml == null || "".equals(notityXml.trim())) {
				resXml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
			} else {
				Map<String, String> m = XMLParser.getMapFromXML(notityXml);
				if (m != null && m.get("result_code") != null && "SUCCESS".equals(m.get("result_code").toString())) {

					String sign = WeixinUtil.getPaySign(m);// 获得支付成功后的签名
					// 验证签名
					// if (sign.equals(m.get("sign").toString())) {
					resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";
					String orderNum = m.get("out_trade_no").toString();
					String transaction_id = m.get("transaction_id").toString();
					Order record=new Order();
					record.setOrderNo(orderNum);
					List<Order> orderList = orderService.selectByParms(record);
					Order order=orderList.get(0);
					order.setOrderWxNo(transaction_id);
					order.setPayStatus("已支付");
					order.setPayType("微信支付");
					order.setOrderStatus(2);
					order.setUpdateDate(new Date());
					orderService.updateByPrimaryKeySelective(order);
				} else {
					resXml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
				}
			}
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
			out.close();
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
	@RequestMapping(value = "/serviceProtocol")
	public String serviceProtocol(HttpServletRequest request) {
		request.setAttribute("navName", "委托服务协议条款");
		return "serviceProtocol";
	}
	@RequestMapping(value = "/useInstroduction")
	public String useInstroduction(HttpServletRequest request) {
		request.setAttribute("navName", "使用说明");
		return "useInstroduction";
	}
	@RequestMapping(value = "/serviceInstroduction")
	public String serviceInstroduction(HttpServletRequest request) {
		request.setAttribute("navName", "服务介绍");
		return "serviceInstroduction";
	}
	@RequestMapping(value = "/orderAddressUpdate")
	public String orderAddressUpdate(HttpServletRequest request) {
		request.setAttribute("navName", "编辑地址");
		return "orderAddressUpdate";
	}
}
