package com.efrobot.weixin.collect.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.efrobot.toolkit.util.http.RestClient;
import com.efrobot.toolkit.util.xml.XMLParser;
import com.efrobot.weixin.collect.bean.TextMessage;
import com.efrobot.weixin.collect.service.CoreService;
import com.efrobot.weixin.collect.service.UserService;
import com.efrobot.weixin.util.WXKeys;
import com.efrobot.weixin.util.WeixinUtil;

@Service("coreService")
public class CoreServiceImpl implements CoreService {
	private static final Logger log = Logger.getLogger(CoreServiceImpl.class);
	@Resource
	private UserService userService;

	public static RestTemplate restTemplate = RestClient.getInstance();// 请求类
	public static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 6);

	// @Transactional(rollbackFor = { Exception.class })
	public String processRequest(HttpServletRequest request, HttpServletResponse response, String xmlReceivedMessage)
			throws Exception {
		// 解析返回的xml数据
		Map requestMap = XMLParser.getMapFromXML(xmlReceivedMessage);
		// 接收方帐号（收到的OpenID）
		String fromUserName = (String) requestMap.get("FromUserName");
		// 事件场景值ID
		String scene_id = (String) requestMap.get("EventKey");
		// 公众帐号
		String toUserName = (String) requestMap.get("ToUserName");
		// 消息类型
		String msgType = (String) requestMap.get("MsgType");
		// 设置默认返回的文本消息内容
		String respContent = "请求处理异常，请稍候尝试！";
		// 创建文本消息
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
//		textMessage.setMsgType("text");
		textMessage.setMsgType("transfer_customer_service");

		if (msgType.equals(WXKeys.REQ_MESSAGE_TYPE_TEXT)) {
			log.info("设置回复用户文本消息");
			 respContent = "请先输入您订单遇到的情况概述和您的联系方式，稍后客服会联系您请先输入您订单遇到的情况概述和您的联系方式，稍后客服会联系您";
		} else if (msgType.equals(WXKeys.REQ_MESSAGE_TYPE_IMAGE)) {
			log.info("设置回复用图片消息");
			 respContent = "您发送的是图片消息！";
		} else if (msgType.equals(WXKeys.REQ_MESSAGE_TYPE_LOCATION)) {
			log.info("您发送的是地理位置消息！");
			respContent = "您发送的是地理位置消息！";
		} else if (msgType.equals(WXKeys.REQ_MESSAGE_TYPE_LINK)) {
			log.info("您发送的是链接消息！");
			respContent = "您发送的是链接消息！";
		} else if (msgType.equals(WXKeys.REQ_MESSAGE_TYPE_VOICE)) {
			log.info("您发送的是音频消息！");
			// respContent = "您发送的是音频消息！";
		} else if (msgType.equals(WXKeys.REQ_MESSAGE_TYPE_VIDEO)) {
			log.info("您发送的是音频消息！");
		} else if (msgType.equals("event")) {
			// 事件类型
			String eventType = (String) requestMap.get("Event");
			// 订阅
			if (eventType.equals(WXKeys.EVENT_TYPE_SUBSCRIBE)) {
				log.info("===================关注>>>>>>>>>>>>>>>" + scene_id);
				respContent = "欢迎关注行李到家！不方便携带大包小包的、着急有事儿等不了行李提取的旅客，您可以轻松离开，我们为您服务到家！同时，我们也提供您出行中有用的资讯和其它便捷服务。希望您的出行更舒适和便捷。";
			} else if (eventType.equals(WXKeys.EVENT_TYPE_UNSUBSCRIBE)) {
				log.info("===================取消关注");
			} else if (eventType.equals(WXKeys.EVENT_TYPE_SCAN)) {
				log.info("===================扫描二维码事件");
				log.info("===================扫描二维码事件>>>>>>>>>>>>>>>" + scene_id);
				// 保存用户信息
				respContent = "SUCCESS";
			} else if (eventType.equals(WXKeys.EVENT_TYPE_LOCATION)) {
				log.info("===================请求消息类型：地理位置 0（推送）");
			} else if (eventType.equals(WXKeys.EVENT_TYPE_CLICK)) {
				String EventKey = (String) requestMap.get("EventKey");
				textMessage.setMsgType("text");
				System.out.println("您发送的是音频消息！"+EventKey);
				if("V1001_GOOD".equals(EventKey)){
					respContent = "嗨，很高兴为您服务！民航出行相关的，请您随便出手，姆们以15年行业专业功力为您提供满意的答案~民航出行不相关的，请您适度开撩，对待非专业应撩党手下留情~~~客服值班时间每日8点—20点；请直接在下方对话框中输入问题!";
				}
				log.info("===================事件类型：CLICK自定义菜单点击事件");
//				respContent = "菜单点击事件";
			}
		}
		textMessage.setContent(respContent);
		String respMessage = WeixinUtil.getObjectMessageToXml(textMessage);
		return respMessage;
	}
}