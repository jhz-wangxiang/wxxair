package com.efrobot.weixin.util;

public class WXKeys {
	/**
	 * 微信专有标识
	 */
	public static final String WX_APPID = WeixinConfig.getProperty("appid");

	public static final String WX_SECRET = WeixinConfig.getProperty("appsecret");

	public static String WX_PARTNER = WeixinConfig.getProperty("partner");
	// 这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
	public static String WX_PARTNERKEY = WeixinConfig.getProperty("partnerkey");

	public static String WX_TOKEN = WeixinConfig.getProperty("token");// "dream2014";

	// 微信接口支付
	public static String ROBOT_OPENID = WeixinConfig.getProperty("openId");

	public static String WX_CERTPATH = WeixinConfig.getProperty("certPath");
	
	public static String WX_PYA_URL = WeixinConfig.getProperty("pay_url");

	public static final String SUCCESS = "SUCCESS";

	public static final String FAIL = "FAIL";

	
	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	/**
	 * 请求消息类型：地理位置 0（推送）
	 */
	public static final String EVENT_TYPE_LOCATION = "LOCALHOST";
	/**
	 * 事件类型：SCAN(扫描二维码事件)
	 */
	public static final String EVENT_TYPE_SCAN = "SCAN";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String REQ_MESSAGE_TYPE_MENTION = "mention";
	/**
	 * 获取用户基本信息的接口地址
	 */
	public static final String PERSON_BASE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	/**
	 * ACCESS_TOKEN_JOBURL
	 */
	public static final String ACCESS_TOKEN_JOBURL = "http://wxaccesstoken.aliapp.com/openApi/token";
	/**
	 * JSTICKETJOBURL
	 */
	public static final String JSTICKETJOBURL = "http://wxaccesstoken.aliapp.com/openApi/getticket";

}
