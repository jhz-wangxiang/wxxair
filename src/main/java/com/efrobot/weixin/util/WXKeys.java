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

	public static String WX_TENVOTE = WeixinConfig.getProperty("tenVote");

	public static String WX_NOTIFYURL = WeixinConfig.getProperty("vote_notifyurl");

	public static String WX_ROBOT_NOTIFYURL = WeixinConfig.getProperty("robotNotifyUrl");
	
	public static String WX_ROBOT_RECHARGEURL = WeixinConfig.getProperty("rechargeNotify");
	
	public static String WX_ROBOT_RECHARGE = WeixinConfig.getProperty("recharge");

	public static String WX_STORE_NOTIFYURL = WeixinConfig.getProperty("storeNotifyUrl");

	public static String WX_HEARTBEATURL = WeixinConfig.getProperty("heartBeatUrl");

	public static String WX_HERTCODE = WeixinConfig.getProperty("heartCode");

	public static String WX_TOKEN = WeixinConfig.getProperty("token");// "dream2014";

	public static String MOOR7_TOKEN = WeixinConfig.getProperty("7moortoken");// 七陌token;
	// memcache缓存服务器配置
	public static String WX_MEMCACHE_HOST = WeixinConfig.getProperty("memcache_host");
	public static String WX_MEMCACHE_PORT = WeixinConfig.getProperty("memcache_port");
	public static String WX_MEMCACHE_SID = WeixinConfig.getProperty("memcache_sid");
	public static String WX_MEMCACHE_PASSWORD = WeixinConfig.getProperty("memcache_password");
	// 短信发送需要的AAPPKEY
	public static String ALIDAYU_APPKEY = WeixinConfig.getProperty("alidayu_appkey");
	// 短信发送的密钥
	public static String ALIDAYU_APPSECRET = WeixinConfig.getProperty("alidayu_appsecret");
	// 微信接口支付
	public static String ROBOT_OPENID = WeixinConfig.getProperty("openId");

	public static String WX_CERTPATH = WeixinConfig.getProperty("certPath");
	/* 阿里云上传图片key和密钥 */
	public static String ALI_ACCESSKEYID = WeixinConfig.getProperty("accessKeyId");
	public static String ALI_ACCESSKEYSECRET = WeixinConfig.getProperty("accessKeySecret");
	/*病毒一病毒二扫码活动接口url*/
	public static String SCAN_CODE_VIRUS1 = WeixinConfig.getProperty("virus1");
	public static String SCAN_CODE_WITHDRAW = WeixinConfig.getProperty("withdraw");
	public static String SCAN_CODE_WDFULLDATA = WeixinConfig.getProperty("wdfulldata");
	/*菜单跳转路径url*/
	public static String SEND_URL_GAME = WeixinConfig.getProperty("sendurl_game");
	public static String SEND_URL_INDEX = WeixinConfig.getProperty("sendurl_index");
	public static String SEND_URL_USERINFO = WeixinConfig.getProperty("sendurl_userinfo");
//	MQTT路径
	public static String ROBOT_SERVICE_MQTT = WeixinConfig.getProperty("robot_service_mqtt");
	
	/**
	 * 微信创建菜单路径
	 */
	public static final String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	public static final String CHK_USE_ORDER_NOTIFY = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	public static final String WEIXIN_CUSTOMER_SERVER_URL = "http://wechat.7moor.com/Icallback";

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

	public static String msgCommon(String content) {
		if ("1".equals(content)) {
			return "小胖，英文名Fabo是由北京进化者机器人科技有限公司研发的第一代家庭服务型机器人。集视觉建模、自建地图、自主导航避障、自我意识、情感表达等多项高科技功能于一体。目前已取得多项国家专利。";
		} else if ("2".equals(content)) {
			return "小胖家用机功能定位娱乐、教育、服务三大领域。基础功能：小学生全科教育，名校名师辅导，英语跟读教学，童话寓言，百科全书，单词翻译，移动投影，体感游戏，语音互动，视频聊天，唱歌跳舞，评书相声，移动空气净化，智控家电，运送物品，紧急报警，定时找你，远程遥控，应用商城。了解详情观看<a href='http://v.qq.com/x/page/w0178tjca3j.html'>视频</a>。";
		} else if ("3".equals(content)) {
			return "除家用机基础功能外，可按照您自身拥有的渠道资源，应用于不同商务环境，嵌入专门的软件模块，打造全新商业模式。了解详情观看<a href='http://v.qq.com/x/page/g0176pz8nec.html'>视频</a>。";
		} else if ("4".equals(content)) {/*<a hrel=\"http://wechat-server.efrobot.com/clickMe/zsVideo.htmls\">视频</a> */
			return "官方购买渠道：官方微信“进化者机器人小胖”商城、官网www.efrobot.com 、京东：小胖机器人官方旗舰店、天猫：小胖机器人旗舰店。小胖家用机价格12988元起。";
		} else if ("5".equals(content)) {
			return "在微信商城购买成功后，可以进入微信公众号左下方的菜单“胖粉中心”中的“我的订单”进行查询订单详情和物流状态。";
		}else if ("6".equals(content)) {
			return "凡在购买时在微信或官网订单页面“推荐机器人ID”一栏填写正确的机器人ID码，价格可以立减200元现金哦！";
		}else if ("7".equals(content)) {
			return "请点击以下链接，在手机浏览器中打开即可下载小胖手机端APP。http://efrobot.com/apk.html";
//		}else if ("8".equals(content)) {
//			return "已经购买小胖的胖粉只要让你的朋友们扫机器人端屏幕的微信二维码，并且他们成功关注小胖的公众号后，你的机器人就会赚金币哦。每一个成功关注后你的机器人就能赚2金币，会存在机器人的金币账户里，有了金币你可以随便购买机器人应用商城的产品了哦，赶快分享二维码吧！";
//		}else if ("9".equals(content)) {
//			return "回复“会说话的小胖水壶京东众筹”系统将自动弹出问题，答对2题则收到随机红包1个。先抢先得，抢完为止！（注：问题答案在众筹页面寻找：<a href='https://z.jd.com/project/details/68513.html'>京东众筹</a>）。凡参与众筹成功购买的粉丝点此链接填写众筹订单号即可领取礼物：<a href='http://u.eqxiu.com/s/1qP4sU9M?eqrcode=1&from=groupmessage&isappinstalled=0'>活动链接</a>";
//		}else if ("10".equals(content)) {
//			return "了解会说话的小胖热水壶请前往<a href='https://z.jd.com/project/details/68513.html'>京东众筹</a>。凡参与众筹成功购买的粉丝点此链接填写众筹订单号即可额外获得礼物：<a href='http://u.eqxiu.com/s/1qP4sU9M?eqrcode=1&from=groupmessage&isappinstalled=0'>活动链接</a>";
		} else {
			return null;
		}
	}
}
