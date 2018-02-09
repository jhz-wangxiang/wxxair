package com.efrobot.weixin.collect.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.efrobot.weixin.baseapi.pojo.Area;
import com.efrobot.weixin.baseapi.pojo.Channel;
import com.efrobot.weixin.baseapi.pojo.FlightNum;
import com.efrobot.weixin.baseapi.pojo.Order;
import com.efrobot.weixin.baseapi.pojo.OrderStatus;
import com.efrobot.weixin.baseapi.pojo.OrderStatusRecord;
import com.efrobot.weixin.baseapi.pojo.User;
import com.efrobot.weixin.collect.service.AreaService;
import com.efrobot.weixin.collect.service.FlightNumService;
import com.efrobot.weixin.collect.service.OrderService;
import com.efrobot.weixin.collect.service.UserService;
import com.efrobot.weixin.util.CommonUtil;
import com.efrobot.weixin.util.SerialNum;

@RequestMapping("/v1/order")
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private AreaService areaService;

	@Autowired
	private FlightNumService flightNumService;
	@Autowired
	private UserService userService;
	public static Map<Integer, String> status_order = new ConcurrentHashMap<Integer, String>();

	@PostConstruct
	public void init() {
		List<OrderStatus> list = orderService.selectSelectList(null);
		for (OrderStatus s : list) {
			status_order.put(s.getStatus(), s.getDescribe());
		}
	}


	

	@RequestMapping(value = "/getOrderDetail", method = RequestMethod.POST)
	@ResponseBody
	public Order getOrderDetail(Order record) throws Exception {
		List<Order> orderList = orderService.selectByParms(record);
		return orderList.get(0);
	}

	@RequestMapping(value = "/insertOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertOrder(Order record) throws Exception {
		int result = -1;
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
		if (record.getNowTime().getTime() < flightNum.getEndTime().getTime()
				&& record.getNowTime().getTime() > flightNum.getStartTime().getTime()) {
		} else {
			return CommonUtil.resultMsg("FAIL", "对不起，你航班的目的地还未开通此项服务。 ");
		}
		// 校验航班时间
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

		String time = sdf.format(record.getNowTime()) + " " + flightNum.getEndHour() + ":00:00";

		Date t = sdf2.parse(time);
		Date n = new Date();
		if (!CommonUtil.jisuan(n, t)) {
			return CommonUtil.resultMsg("FAIL", "航班未开放，请于落地前 24 小时内下单");// 订单可在航班计划落地时间前
																		// 24
																		// 小时内下单，如早下单，则提示
		}
		String zm = flightNum.getExp1();
		String orderNo = zm + datestr + mmdd.format(record.getNowTime()) + record.getFlightNum()
				+ SerialNum.getSystemManageOrder();
//		Subject subject = SecurityUtils.getSubject();
//		Session session = subject.getSession();
//		SysUser sysUser = (SysUser) session.getAttribute(Const.SESSION_USER);
		// 异常处理
		record.setOrderStatus(1);// 支付变成2
		record.setPayStatus("未支付");
		record.setCreateDate(new Date());
		record.setOrderNo(orderNo);
		record.setSingleWay("柜台");
		record.setAbnormalStatus("否");
//		record.setOperator("柜台" + sysUser.getName());/
		// 价格计算
		float c = 1;
		float a = 1;
		int p = 1;
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
			User user = new User();
			user.setOrderDate(new Date());
			user.setExp2("有");
			user.setId(record.getUserId());
			userService.updateByPrimaryKeySelective(user);
			setHistory("提交订单", orderNo, "");
			return CommonUtil.resultMsg("SUCCESS", "订单创建成功!");
		} else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}

	@RequestMapping(value = "/insertOrderbyUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertOrderbyUser(Order record) throws Exception {
		int result = -1;
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
			return CommonUtil.resultMsg("FAIL", "对不起，你航班的目的地还未开通此项服务。");
		}
		FlightNum flightNum = list.get(0);
		if (record.getNowTime().getTime() < flightNum.getEndTime().getTime()
				&& record.getNowTime().getTime() > flightNum.getStartTime().getTime()) {
		} else {
			return CommonUtil.resultMsg("FAIL", "对不起，你航班的目的地还未开通此项服务。");
		}
		// 校验航班时间
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

		String time = sdf.format(record.getNowTime()) + " " + flightNum.getEndHour() + ":00:00";

		Date t = sdf2.parse(time);
		Date n = new Date();
		if (!CommonUtil.jisuan(n, t)) {
			return CommonUtil.resultMsg("FAIL", "航班未开放，请于落地前 24 小时内下单");// 订单可在航班计划落地时间前
																		// 24
																		// 小时内下单，如早下单，则提示
		}
		// 订单可在航班计划落地时间前 24 小时内下单，如早下单，则提示
		// //航班未开放，请于落地前 24 小时内下单。未支付订单保留至航班计划落地时间
		// //后 24 小时，将自动取消。未确认订单在航班计划落地时间后 24 小时，将自动
		// //退款，并标识订单取消。（
		String zm = flightNum.getExp1();
		String orderNo = zm + datestr + mmdd.format(record.getNowTime()) + record.getFlightNum()
				+ SerialNum.getSystemManageOrder();
//		Subject subject = SecurityUtils.getSubject();
//		Session session = subject.getSession();
//		SysUser sysUser = (SysUser) session.getAttribute(Const.SESSION_USER);
		// 异常处理
		record.setOrderStatus(1);// 支付变成2
		record.setPayStatus("未支付");
		record.setCreateDate(new Date());
		record.setOrderNo(orderNo);
		record.setSingleWay("柜台");
		record.setAbnormalStatus("否");
//		record.setOperator("柜台" + sysUser.getName());
		// 价格计算
		float c = 1;
		float a = 1;
		int p = 1;
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
			User user = new User();
			user.setOrderDate(new Date());
			user.setExp2("有");
			user.setId(record.getUserId());
			user.setPhone(record.getPhone());
			user.setName(record.getName());
			user.setChannelId(record.getChannelId());
			userService.updateByPrimaryKeySelective(user);
			setHistory("提交订单", orderNo, "");
			return CommonUtil.resultMsg("SUCCESS", "订单创建成功!");
		} else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}

	@RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateOrder(Order record) throws Exception {
		int result = -1;
		result = orderService.updateByPrimaryKeySelective(record);
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1)
			return CommonUtil.resultMsg("SUCCESS", "编辑信息成功");
		else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}

	// 修改订单过程状态
	@RequestMapping(value = "/updateOrderStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateOrderStatus(Order record) throws Exception {
		int result = -1;
		Order order2 = orderService.selectByPrimaryKey(record.getId());
		if (order2.getOrderStatus() < 6) {
			if (order2.getOrderStatus() == 1) {
				record.setPayStatus("已支付");
			}
			record.setOrderStatus(order2.getOrderStatus() + 1);
			setHistory(status_order.get(order2.getOrderStatus() + 1), order2.getOrderNo(), record.getRemark());
		} else {
			return CommonUtil.resultMsg("FAIL", "已经签收完结");
		}
		result = orderService.updateByPrimaryKeySelective(record);
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1)
			return CommonUtil.resultMsg("SUCCESS", "编辑信息成功");
		else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}

	// 修改订单过程状态
	@RequestMapping(value = "/updateOrderStop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateOrderStop(Order record) throws Exception {
		int result = -1;
		Order order2 = orderService.selectByPrimaryKey(record.getId());
		record.setOrderStatus(11);
		result = orderService.updateByPrimaryKeySelective(record);
		setHistory(status_order.get(11), order2.getOrderNo(), record.getRemark());
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1)
			return CommonUtil.resultMsg("SUCCESS", "编辑信息成功");
		else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}

	// 修改取消过程状态
	@RequestMapping(value = "/updateOrderCancel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateOrderCancel(Order record) throws Exception {
		int result = -1;
		Order order2 = orderService.selectByPrimaryKey(record.getId());
		if (order2.getOrderStatus() < 2) {
			record.setOrderStatus(10);
			result = orderService.updateByPrimaryKeySelective(record);
			setHistory(status_order.get("订单取消"), order2.getOrderNo(), record.getCancelReason());
		} else {
			return CommonUtil.resultMsg("FAIL", "现在的状态不可以取消订单");
		}
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1)
			return CommonUtil.resultMsg("SUCCESS", "编辑信息成功");
		else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}

	// 修改取消过程状态
	@RequestMapping(value = "/updateAbnormalStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAbnormalStatus(Order record) throws Exception {
		int result = -1;
		Order order2 = orderService.selectByPrimaryKey(record.getId());
		if ("是".equals(order2.getAbnormalStatus())) {
			record.setAbnormalStatus("否");
			setHistory("订单异常", order2.getOrderNo(), record.getAbnormaReason());
		} else {
			record.setAbnormalStatus("是");
			setHistory("订单正常", order2.getOrderNo(), record.getAbnormaReason());
		}
		result = orderService.updateByPrimaryKeySelective(record);
		if (result == 0) {
			return CommonUtil.resultMsg("FAIL", "未找到可编辑的信息");
		} else if (result == 1)
			return CommonUtil.resultMsg("SUCCESS", "编辑信息成功");
		else {
			return CommonUtil.resultMsg("FAIL", "更新异常: 多条数据被更新 ");
		}
	}

	@RequestMapping(value = "/getOrderStatus", method = RequestMethod.POST)
	@ResponseBody
	public List<OrderStatus> getOrderStatus(String ids) throws Exception {
		List<OrderStatus> list = orderService.selectSelectList(ids);
		return list;
	}

	@RequestMapping(value = "/getChannel", method = RequestMethod.POST)
	@ResponseBody
	public List<Channel> getChannel(Channel record) throws Exception {
		record.setExp1("1");
		List<Channel> list = orderService.getChannel(record);
		return list;
	}

	@RequestMapping(value = "/getArea", method = RequestMethod.POST)
	@ResponseBody
	public List<Area> getArea(Area record) throws Exception {
		List<Area> list = orderService.getArea(record);
		return list;
	}

	@RequestMapping(value = "/getFlightNum", method = RequestMethod.POST)
	@ResponseBody
	public List<FlightNum> getFlightNum(FlightNum record) throws Exception {
		List<FlightNum> list = orderService.getFlightNum(record);
		return list;
	}


	public int setHistory(String remark, String orderNo, String reason) {
//		Subject subject = SecurityUtils.getSubject();
//		Session session = subject.getSession();
//		SysUser sysUser = (SysUser) session.getAttribute(Const.SESSION_USER);
		OrderStatusRecord orderStatusRecord = new OrderStatusRecord();
		orderStatusRecord.setRemark(remark);
		orderStatusRecord.setExp1(orderNo);
		orderStatusRecord.setExp2(reason);
//		orderStatusRecord.setRoleId(sysUser.getRoleId());
//		orderStatusRecord.setUserId(sysUser.getId());
		orderStatusRecord.setCreateDate(new Date());
		orderStatusRecord.setStatus(1);// 1-后台,2-客户
		int result = orderService.insertSelective(orderStatusRecord);
		return result;

	}
	@RequestMapping(value = "/getPrice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPrice(Order record) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		// 价格计算
		float c = 1;
		float a = 1;
		int p = 1;
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
		map.put("ChannelDiscount", c);//渠道折扣
		map.put("AreaDiscount", a);//区域折扣,第二件起的折扣
		map.put("num", num);//礼包数量
		map.put("price", p);//单间
		map.put("totalFee", num * p);//总额
		map.put("paid", paid);//实际支付价格
		return map;
	}
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HH");

		String time = sdf2.format(new Date()) + " " + "10";

		try {
			Date t = sdf2.parse(time);
			Date n = new Date();
			long between = t.getTime() - n.getTime();

			if (between < (24 * 3600000) || between > 0) {
				System.out.print(sdf2.format(new Date()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
