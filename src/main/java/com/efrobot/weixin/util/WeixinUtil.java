package com.efrobot.weixin.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSONObject;
import com.efrobot.toolkit.util.aliyun.oss.EndPointEnum;
import com.efrobot.toolkit.util.aliyun.oss.OssClient;
import com.efrobot.toolkit.util.http.HttpUtils;
import com.efrobot.toolkit.util.http.HttpsRequest4WX;
import com.efrobot.toolkit.util.http.RestClient;
import com.efrobot.toolkit.util.string.StringUtils;
import com.efrobot.toolkit.util.weixin.GenerateRequestXml;
import com.efrobot.toolkit.util.weixin.Signature;
import com.efrobot.toolkit.util.weixin.bean.CashRedPacketRequestXmlBean;
import com.efrobot.toolkit.util.weixin.bean.PayRequestXmlBean;
import com.efrobot.toolkit.util.xml.XMLParser;
import com.efrobot.weixin.collect.bean.WxPay;
import com.efrobot.weixin.job.GetWXAccessJob;
import com.efrobot.weixin.util.http.HttpClientConnectionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

public class WeixinUtil {

	public static RestTemplate restTemplate = RestClient.getInstance();// 请求类


	public static DefaultHttpClient initHttpclient() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient);
		httpclient.getParams().setParameter("http.protocol.allow-circular-redirects", Boolean.valueOf(true));
		httpclient.getParams().setParameter("http.socket.timeout", Integer.valueOf(60000));
		httpclient.getParams().setParameter("http.connection.timeout", Integer.valueOf(60000));
		HttpProtocolParams.setUseExpectContinue(httpclient.getParams(), false);

		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= 5) {
					return false;
				}
				if ((exception instanceof NoHttpResponseException)) {
					return true;
				}
				return (exception instanceof ClientProtocolException);
			}
		};
		httpclient.setHttpRequestRetryHandler(retryHandler);
		return httpclient;
	}

	/**
	 * 通过图片url返回图片Bitmap
	 * 
	 * @param url
	 * @return
	 */
	public static InputStream returnBitMap(String path) {
		URL url = null;
		InputStream is = null;
		try {
			url = new URL(path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 利用HttpURLConnection对象,我们可以从网络中获取网页数据.
			conn.setDoInput(true);
			conn.connect();
			is = conn.getInputStream(); // 得到网络返回的输入流

		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/**
	 * Object转化长xml
	 * 
	 * @date 2013-05-19
	 */
	public static String getObjectMessageToXml(Object object) {
		xstream.alias("xml", object.getClass());
		return xstream.toXML(object);
	}

	/**
	 * 获取用户基本信息
	 * 
	 * @param openid
	 *            用户表示
	 * @return JSONObject 用户信息
	 */
	public static JSONObject getWXUserInfo(String openid) {
		String url = WXKeys.PERSON_BASE_MESSAGE.replace("ACCESS_TOKEN", GetWXAccessJob.getAccessToken())
				.replace("OPENID", openid);// GetWXAccessJob.getAccessToken()
		JSONObject jsonObject = null;
		try {
			String jsonStr = HttpUtils.get(url);
			jsonObject = JSONObject.parseObject(jsonStr.toString());
			if (null != jsonObject) {
				if (jsonObject.containsKey("errcode")) {
					return null;
				} else {
					return jsonObject;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 获取用户授权登陆后的access_token
	 * 
	 * @param request
	 * 
	 * @return
	 */
	public static JSONObject getGrantAccessToken(HttpServletRequest request) {
		JSONObject url04_json = null;
		try {
			String code = request.getParameter("code");
			System.out.println("1.code===" + code);
			String url02 = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WXKeys.WX_APPID + "&secret="
					+ WXKeys.WX_SECRET + "&code=" + code + "&grant_type=authorization_code";
			String url03_json = HttpUtils.get(url02);
			JSONObject reqjson = JSONObject.parseObject(url03_json);
			String refresh_token = reqjson.getString("refresh_token");

			String url03 = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + WXKeys.WX_APPID
					+ "&grant_type=refresh_token&refresh_token=" + refresh_token;
			String url04_str = HttpUtils.get(url03);
			url04_json = JSONObject.parseObject(url04_str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url04_json;
	}

	/**
	 * 获取用户授权登陆后的基本信息
	 * 
	 * @param openid
	 *            用户表示
	 * @return JSONObject 用户信息
	 */
	public static JSONObject getGrantUserInfo(String openid, String accessToken) {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		url = url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);// GetWXAccessJob.getAccessToken()
		JSONObject jsonObject = null;
		try {
			String jsonStr = HttpUtils.get(url);
			jsonObject = JSONObject.parseObject(jsonStr.toString());
			if (null != jsonObject) {
				if (jsonObject.containsKey("errcode")) {
					return null;
				} else {
					return jsonObject;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 元转换成分
	 * 
	 * @param money
	 * 
	 * @return
	 */
	public static String getMoney(String amount) {
		if (amount == null) {
			return "";
		}
		// 金额转化为分为单位
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
																// 或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
		}
		return amLong.toString();
	}

	/**
	 * 返回Ajax响应字符串，将处理结果返回
	 * 
	 * @return
	 */
	public static void sendAjaxResponse(HttpServletResponse res, String message) {
		PrintWriter out = null;
		try {
			if (res != null) {
				res.setCharacterEncoding("UTF-8");
				res.setContentType("text/json");
				out = res.getWriter();
				out.print(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 获得用户openid
	 * 
	 * @param request
	 * 
	 * @return
	 */
	public static String getopenidAction(HttpServletRequest request) {
		String openid = "";
		try {
			String code = request.getParameter("code");
			System.out.println("1.code===" + code);
			String url02 = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WXKeys.WX_APPID + "&secret="
					+ WXKeys.WX_SECRET + "&code=" + code + "&grant_type=authorization_code";
			String url03_json = HttpUtils.get(url02);
			JSONObject reqjson = JSONObject.parseObject(url03_json);
			String refresh_token = reqjson.getString("refresh_token");

			String url03 = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + WXKeys.WX_APPID
					+ "&grant_type=refresh_token&refresh_token=" + refresh_token;
			String url04_str = HttpUtils.get(url03);
			JSONObject url04_json = JSONObject.parseObject(url04_str);
			openid = url04_json.getString("openid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return openid;
	}

	// 获取微信jsAPi配置信息
		public static void wxConfig(HttpServletRequest request, HttpServletResponse response) {

			StringBuffer homeUrl = request.getRequestURL();
			String queryString = request.getQueryString();
			if (!StringUtil.isNullOrEmpty(queryString)) {
				homeUrl.append("?").append(queryString);
			}
			long timestamp = System.currentTimeMillis() / 1000;

			String nonceStr = UUID.randomUUID().toString();

			String signature = null;
			try {

				signature = getSignature(GetWXAccessJob.getJsTicket(), nonceStr, timestamp, homeUrl.toString());
				request.setAttribute("appid", WXKeys.WX_APPID);
				request.setAttribute("timestamp", timestamp);
				request.setAttribute("nonceStr", nonceStr);
				request.setAttribute("signature", signature);
//				session.setAttribute("appid", WXKeys.WX_APPID);
//				session.setAttribute("timestamp", timestamp);
//				session.setAttribute("nonceStr", nonceStr);
//				session.setAttribute("signature", signature);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	/**
	 * 获取签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static String getSignature(String ticket, String nonceStr, long timeStamp, String url) throws Exception {

		/**** Java写的参数拼接算法 ***/
		String[] paramArr = new String[] { "jsapi_ticket=" + ticket, "timestamp=" + timeStamp, "noncestr=" + nonceStr,
				"url=" + url };
		Arrays.sort(paramArr);
		// 将排序后的结果拼接成一个字符串
		String content = paramArr[0].concat("&" + paramArr[1]).concat("&" + paramArr[2]).concat("&" + paramArr[3]);
		System.out.println("拼接之后的content为:" + content);
		return getSignature(content);
	}

	/**
	 * 生成签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static String getSignature(String sKey) throws Exception {
		String ciphertext = null;
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] digest = md.digest(sKey.toString().getBytes());
		ciphertext = StringUtil.byteToStr(digest);
		return ciphertext.toLowerCase();
	}

	/**
	 * 获取请求预支付id报文
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getPackage(WxPay tpWxPay, String notifyUrl) {
		String finaPackage = "";
		try {
			String openId = tpWxPay.getOpenId();
			// 订单号
			String orderId = tpWxPay.getOrderId();
			// 附加数据 原样返回
			String attach = "";
			// 总金额以分为单位，不带小数点
			String totalFee = getMoney(tpWxPay.getTotalFee());
			// 订单生成的机器 IP
			String spbill_create_ip = tpWxPay.getSpbillCreateIp();
			// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
			String notify_url = notifyUrl;

			String trade_type = "JSAPI";
			// ---必须参数
			// 商户号
			String mch_id = WXKeys.WX_PARTNER;
			// 随机字符串
			String nonce_str = StringUtil.getNonceStr();

			// 商品描述根据情况修改
			String body = tpWxPay.getBody();

			// 商户订单号
			String out_trade_no = orderId;

			PayRequestXmlBean payBean = GenerateRequestXml.generatePay(WXKeys.WX_APPID, mch_id, out_trade_no, attach,
					totalFee, spbill_create_ip, trade_type, nonce_str, body, notify_url, openId, WXKeys.WX_PARTNERKEY);

			String prepay_id = "";

			String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			String xmlString = restTemplate.postForObject(createOrderURL, payBean, String.class);
			Map<String, String> mapString = XMLParser.getMapFromXML(xmlString);
			System.out.println("获取到的预支付ID：" + mapString);

			// 获取prepay_id后，拼接最后请求支付所需要的package

			SortedMap<String, String> finalpackage = new TreeMap<String, String>();
			String timestamp = StringUtil.getTimeStamp();
			String packages = "prepay_id=" + mapString.get("prepay_id");

			finalpackage.put("appId", WXKeys.WX_APPID);
			finalpackage.put("timeStamp", timestamp);
			finalpackage.put("nonceStr", nonce_str);
			finalpackage.put("package", packages);
			finalpackage.put("signType", "MD5");
			// 要签名
			Signature.initKey(WXKeys.WX_PARTNERKEY);
			String finalsign = Signature.getSign(finalpackage);

			finaPackage = "appId:" + WXKeys.WX_APPID + ",timeStamp:" + timestamp + ",nonceStr:" + nonce_str
					+ ",package:" + packages + ",signType:MD5" + ",paySign:" + finalsign + ",orderNum:" + orderId;

			System.out.println("V3 jsApi package:" + finaPackage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finaPackage;
	}

	
	/**
	 * 查询订单
	 * 
	 * @param appid
	 * @param mch_id
	 * @param transaction_id
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	// public static Map<String, String> queryOrder(String transaction_id)
	// throws ParserConfigurationException, IOException, SAXException {
	// String mch_id = WXKeys.WX_PARTNER;
	// String nonce_str = System.currentTimeMillis() + "";
	// PayOrderInfoRequestXmlBean2
	// order=GenerateRequestXml2.generatePayOrderInfo(WXKeys.WX_APPID, mch_id,
	// transaction_id, nonce_str, WXKeys.WX_PARTNERKEY);
	// String createOrderURL = "https://api.mch.weixin.qq.com/pay/orderquery";
	// String xmlString = restTemplate.postForObject(createOrderURL, order,
	// String.class);
	// Map<String, String> mapString = XMLParser.getMapFromXML(xmlString);
	// return mapString;
	// }
	/**
	 * 支付成功后签名获得
	 * 
	 * @return
	 */
	public static String getPaySign(Map<String, String> m) {

		SortedMap<String, String> signMap = new TreeMap<String, String>();
		signMap.put("appid", WXKeys.WX_APPID);
		signMap.put("bank_type", m.get("bank_type").toString());
		signMap.put("cash_fee", m.get("cash_fee").toString());
		signMap.put("fee_type", m.get("fee_type").toString());
		signMap.put("is_subscribe", m.get("is_subscribe").toString()); // 是否关注
		signMap.put("mch_id", WeixinConfig.getProperty("partner"));
		signMap.put("nonce_str", m.get("nonce_str").toString());
		signMap.put("openid", m.get("openid").toString());
		signMap.put("out_trade_no", m.get("out_trade_no").toString());
		signMap.put("result_code", m.get("result_code").toString());
		signMap.put("return_code", m.get("return_code").toString());
		signMap.put("time_end", m.get("time_end").toString());
		signMap.put("total_fee", m.get("total_fee").toString());
		signMap.put("trade_type", m.get("trade_type").toString());
		signMap.put("transaction_id", m.get("transaction_id").toString());
		// 生成签名
		Signature.initKey(WXKeys.WX_PARTNERKEY);
		String sign = Signature.getSign(signMap);
		return sign;
	}

	/**
	 * 客服消息
	 * 
	 * @param accessToken
	 * @return
	 * 
	 * 		.
	 */
	public static String customerService(String postParam, String accessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken;
		String retr = "";
		try {
			System.out.println("发送给客户postParam>>>>>>>>>>>>>>>" + postParam);
			DefaultHttpClient httpclient = initHttpclient();
			HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
			JSONObject object = null;
			try {
				httpost.setEntity(new StringEntity(postParam, "UTF-8"));
				HttpResponse res = httpclient.execute(httpost);
				String jsonStr = EntityUtils.toString(res.getEntity(), "UTF-8");
				object = JSONObject.parseObject(jsonStr);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				httpost.abort();
				httpclient.getConnectionManager().shutdown();
			}
			// retr=restTemplate.postForObject(url,postParam,String.class);
			System.out.println("发送给客户?>>>>>>>>>>>>>>>" + object.toString());
			retr = object.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retr;
	}
	/**
	 * 客服消息
	 * 
	 * @param accessToken
	 * @return
	 * 
	 * 		.
	 */
	public static String sendTemplate(String openid, String orderNo, String price, String time,String accessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;
		String retr = "";
		String postParam = "{\"touser\":\""+openid+"\",\"template_id\":\"Emw1YIcD0FICcYnuCqUDc7olFz9c7kQfouaMi0YFxd0\","
		 		+ "\"topcolor\":\"#FF0000\",\"data\":{"
		 		+ "\"first\": {\"value\":\"您好，您的微信支付已成功\",\"color\":\"#173177\"},"
		 		+ "\"keyword1\":{\"value\":\""+orderNo+"\",\"color\":\"#173177\"},"
		 		+ "\"keyword2\":{\"value\":\""+price+"\",\"color\":\"#173177\"},"
		 		+ "\"keyword3\":{\"value\":\"行李到家服务号\",\"color\":\"#173177\"},"
		 		+ "\"keyword4\":{\"value\":\""+time+"\",\"color\":\"#173177\"},"
		 		+ "\"remark\":{\"value\":\"请您在航班落地后前往”行李送到家“柜台,递交行李小票（必须）\",\"color\":\"#173177\"}}}";
		try {
			System.out.println("发送给客户postParam>>>>>>>>>>>>>>>" + postParam);
			DefaultHttpClient httpclient = initHttpclient();
			HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
			JSONObject object = null;
			try {
				httpost.setEntity(new StringEntity(postParam, "UTF-8"));
				HttpResponse res = httpclient.execute(httpost);
				String jsonStr = EntityUtils.toString(res.getEntity(), "UTF-8");
				object = JSONObject.parseObject(jsonStr);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				httpost.abort();
				httpclient.getConnectionManager().shutdown();
			}
			// retr=restTemplate.postForObject(url,postParam,String.class);
			System.out.println("发送给客户?>>>>>>>>>>>>>>>" + object.toString());
			retr = object.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retr;
	}

	/**
	 * 获得素材
	 * 
	 * @param accessToken
	 * @return
	 * 
	 * 		.
	 */
	public static String getMedia(String postParam, String accessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=" + accessToken;
		String retr = "";
		try {
			System.out.println("发送给客户postParam>>>>>>>>>>>>>>>" + postParam);
			DefaultHttpClient httpclient = initHttpclient();
			HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
			JSONObject object = null;
			try {
				httpost.setEntity(new StringEntity(postParam, "UTF-8"));
				HttpResponse res = httpclient.execute(httpost);
				String jsonStr = EntityUtils.toString(res.getEntity(), "UTF-8");
				object = JSONObject.parseObject(jsonStr);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				httpost.abort();
				httpclient.getConnectionManager().shutdown();
			}
			// retr=restTemplate.postForObject(url,postParam,String.class);
			System.out.println("发送给客户?>>>>>>>>>>>>>>>" + object.toString());
			retr = object.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retr;
	}

	/**
	 * 获取关注着列表
	 * 
	 * @param appId
	 *            开发者凭据
	 * @param appSecret
	 * @param next_openid
	 *            拉取列表的后一个用户的OPENID
	 */
	public static JSONObject getAttation(String next_openid, String accessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
		try {
			// 拼装获取关注者列表的URL
			url = url.replace("ACCESS_TOKEN", GetWXAccessJob.getAccessToken()).replace("NEXT_OPENID", next_openid);
			// 调用接口获取关注者列表
			JSONObject jsonObject = null;
			String jsonStr = HttpUtils.get(url);
			jsonObject = JSONObject.parseObject(jsonStr.toString());
			if (null != jsonObject) {
				if (jsonObject.containsKey("errcode")) {
					return null;
				} else {
					return jsonObject;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	/**
	 * 生成临时参数二维码图片
	 * 
	 * @param accessToken
	 * @param sceneStr
	 *            参数值
	 * @return
	 */
	public static String TicketImgUrl(int sceneid) {
		JSONObject jsonObject = new JSONObject();
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + GetWXAccessJob.getAccessToken();
		String ticket = null;
		String downloadTicketImg = "";
		try {
			String postParam = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": \""
					+ sceneid + "\"}}}";
			// ticket = HttpUtils.post(postParam, url, null);
			ticket = restTemplate.postForObject(url, postParam, String.class);
			jsonObject = JSONObject.parseObject(ticket);
			downloadTicketImg = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + jsonObject.getString("ticket");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return downloadTicketImg;
	}

	/**
	 * 生成临时参数二维码图片
	 * 
	 * @param sceneid
	 * @param expireSeconds//有效时间，单位是秒
	 *            参数值
	 * @return
	 */
	public static String TicketImgUrlByTime(int sceneid, String expireSeconds) {
		JSONObject jsonObject = new JSONObject();
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + GetWXAccessJob.getAccessToken();
		String ticket = null;
		String downloadTicketImg = "";
		try {
			String postParam = "{\"expire_seconds\": " + expireSeconds
					+ ", \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": \"" + sceneid
					+ "\"}}}";
			// ticket = HttpUtils.post(postParam, url, null);
			ticket = restTemplate.postForObject(url, postParam, String.class);
			jsonObject = JSONObject.parseObject(ticket);
			downloadTicketImg = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + jsonObject.getString("ticket");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return downloadTicketImg;
	}

	/**
	 * 上传多媒体文件()
	 * 
	 * @param appId
	 *            开发者凭据
	 * @param appSecret
	 * @param media
	 *            媒体文件
	 * @param type
	 *            文件类型
	 */
	public static JSONObject uploadMedia(String type, String filename, File media, String content_type,
			String filePath) {
		// JSONObject
		// jsonObject123=uploadMedia("image","123.png",file,"png",file.getPath());
		String end = "\r\n";
		String twoHyphens = "--"; // 用于拼接
		String boundary = "*****"; // 用于拼接 可自定义
		String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		StringBuffer bf = new StringBuffer();
		JSONObject jsonObject = new JSONObject();
		try {
			// 调用接口获取access_token
			// 拼装移动用户分组的URL
			url = url.replace("ACCESS_TOKEN", GetWXAccessJob.getAccessToken()).replace("TYPE", type);
			URL submit = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) submit.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			// 获取输出流对象，准备上传文件
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			dos.writeBytes("Content-Disposition: form-data; name=\"" + media + "\";filename=\"" + filename
					+ ";filelength=\"" + filePath + ";Content-Type=\"" + content_type + end);
			dos.writeBytes(end);
			// 对文件进行传输
			FileInputStream fis = new FileInputStream(media);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
			}
			fis.close(); // 关闭文件流

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			// 将返回的输入流转换成字符串
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				bf.append(str);
				System.out.println(str + "");
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.parseObject(bf.toString());

			if (null != jsonObject) {
				if (jsonObject.containsKey("errcode")) {
					return null;
				} else {
					return jsonObject;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("与服务器连接发生异常错误:" + e.toString());
			System.out.println("连接地址是:" + url);
			return null;
		}
		return null;
	}

	/**
	 * 获得http请求的文件
	 * 
	 * @param url
	 *            http路径
	 */
	public static File getFileByHttp(String urlString, String path) {
		File file = null;
		try {
			String str[] = urlString.split("/");
			String imageName = str[str.length - 1];
			URL url = new URL(urlString);
			DataInputStream dataInputStream;
			dataInputStream = new DataInputStream(url.openStream());
			File file1 = new File(path);
			file = new File(file1, imageName);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = dataInputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, length);
			}
			System.out.println("发送给客户msg>>>>>>>>>>>>>>>" + imageName + "======" + urlString);
			dataInputStream.close();
			fileOutputStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 获得http请求的文件
	 * 
	 * @param url
	 *            http路径
	 */
	public static File getFileByHttpVoice(String fileFileName, String urlString, String path) {
		InputStream in;
		File file = null;
		// path = path+fileFileName+".amr";
		try {
			// String str[] = urlString.split("/");
			String imageName = fileFileName + ".mp3";
			URL url = new URL(urlString);
			DataInputStream dataInputStream;
			dataInputStream = new DataInputStream(url.openStream());
			File file1 = new File(path);
			file = new File(file1, imageName);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = dataInputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, length);
			}
			System.out.println("发送给客户msg>>>>>>>>>>>>>>>" + imageName + "======" + urlString);
			dataInputStream.close();
			fileOutputStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 从网络Url中下载文件
	 * 
	 * @param urlStr
	 * @param fileName
	 * @param savePath
	 * @throws IOException
	 */
	public static void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
		InputStream in = new URL(urlStr).openConnection().getInputStream(); // 创建连接、输入流
		FileOutputStream f = new FileOutputStream("savePath");// 创建文件输出流
		byte[] bb = new byte[1024]; // 接收缓存
		int len;
		while ((len = in.read(bb)) > 0) { // 接收
			f.write(bb, 0, len); // 写入文件
		}
		f.close();
		in.close();

	}

	/**
	 * 从输入流中获取字节数组
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

	public static void change(String sourceUrl, String targetUrl) {
		try {
//			 File source = new File("D:\\123.amr");
//			 File target = new File("D:\\123.mp3");
			File source = new File(sourceUrl);
			File target = new File(targetUrl);

			AudioAttributes audio = new AudioAttributes();
			audio.setCodec("libmp3lame");
			audio.setBitRate(new Integer(128000));
			audio.setChannels(new Integer(2));
			audio.setSamplingRate(new Integer(44100));
			EncodingAttributes attrs = new EncodingAttributes();
			attrs.setFormat("mp3");
			attrs.setAudioAttributes(audio);
			Encoder encoder = new Encoder();
			encoder.encode(source, target, attrs);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InputFormatException e) {
			e.printStackTrace();
		} catch (EncoderException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @desc ：微信上传素材的请求方法
	 * 
	 * @param requestUrl
	 *            微信上传临时素材的接口url
	 * @param file
	 *            要上传的文件
	 * @return String 上传成功后，微信服务器返回的消息
	 */
	public static String httpRequest(String type, File file) {
		StringBuffer buffer = new StringBuffer();
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", GetWXAccessJob.getAccessToken()).replace("TYPE", type);
		try {
			// 1.建立连接
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection(); // 打开链接

			// 1.1输入输出设置
			httpUrlConn.setDoInput(true);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setUseCaches(false); // post方式不能使用缓存
			// 1.2设置请求头信息
			httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
			httpUrlConn.setRequestProperty("Charset", "UTF-8");
			// 1.3设置边界
			String BOUNDARY = "----------" + System.currentTimeMillis();
			httpUrlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			// 请求正文信息
			// 第一部分：
			// 2.将文件头输出到微信服务器
			StringBuilder sb = new StringBuilder();
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length() + "\";filename=\""
					+ file.getName() + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			byte[] head = sb.toString().getBytes("utf-8");
			// 获得输出流
			OutputStream outputStream = new DataOutputStream(httpUrlConn.getOutputStream());
			// 将表头写入输出流中：输出表头
			outputStream.write(head);

			// 3.将文件正文部分输出到微信服务器
			// 把文件以流文件的方式 写入到微信服务器中
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				outputStream.write(bufferOut, 0, bytes);
			}
			in.close();
			// 4.将结尾部分输出到微信服务器
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			outputStream.write(foot);
			outputStream.flush();
			outputStream.close();

			// 5.将微信服务器返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * 现金红包
	 * 
	 * @param openid
	 * @param amount
	 * @param nickName
	 *            提供方名称-进化者机器人小胖
	 * @param sendName
	 *            商户名称-进化者机器人小胖
	 * @param wishing
	 *            红包祝福语-小胖恭祝您新年发大财！！
	 * @param actName
	 *            活动名称-新年红包
	 * @param remark
	 *            备注-持续关注，更多惊喜等着你哦！
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> sendRedPack(String openid, int amount, String nickName, String sendName,
			String wishing, String actName, String remark) {
		Map<String, String> XmlMap = new HashMap<String, String>();
		String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
		try {
			CashRedPacketRequestXmlBean enterprisePayRequestXmlBean = GenerateRequestXml.generate4CashRedPacket(
					WXKeys.WX_PARTNERKEY, WXKeys.WX_PARTNER, WXKeys.WX_APPID, openid, amount, nickName, sendName,
					wishing, actName, remark);
			HttpsRequest4WX htttpcliect = new HttpsRequest4WX(
					WeixinUtil.class.getResource(WXKeys.WX_CERTPATH).getPath(), WXKeys.WX_PARTNER);
			String xmlstr = htttpcliect.sendPost(url, enterprisePayRequestXmlBean);
			XmlMap = XMLParser.getMapFromXML(xmlstr);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e1) {
			e1.printStackTrace();
		} catch (KeyManagementException e1) {
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return XmlMap;
	}
	/**
	 * 退款
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> Refund(String out_trade_no,Integer total_fee, Integer refund_fee) {
		Map<String, String> XmlMap = new HashMap<String, String>();
		// 随机字符串
		String nonce_str = StringUtil.getNonceStr();
		String out_refund_no = StringUtil.getNonceStr();
		String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		try {
			RefundRequestXmlBean enterprisePayRequestXmlBean = generateRefundInfo(WXKeys.WX_APPID,WXKeys.WX_PARTNERKEY, WXKeys.WX_PARTNER,  nonce_str, out_trade_no,
					 out_refund_no,  total_fee,  refund_fee);
			HttpsRequest4WX htttpcliect = new HttpsRequest4WX(
					WeixinUtil.class.getResource(WXKeys.WX_CERTPATH).getPath(), WXKeys.WX_PARTNER);
			String xmlstr = htttpcliect.sendPost(url, enterprisePayRequestXmlBean);
			XmlMap = XMLParser.getMapFromXML(xmlstr);
			System.out.print(XmlMap);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e1) {
			e1.printStackTrace();
		} catch (KeyManagementException e1) {
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return XmlMap;
	}
	//退款
	public static RefundRequestXmlBean generateRefundInfo(String appid, String partnerKey,String mch_id, String nonce_str,String out_trade_no,
			String out_refund_no, Integer total_fee, Integer refund_fee) {
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appid", appid);// 公众账号appid
		packageParams.put("mch_id", mch_id);// 商户号
		packageParams.put("nonce_str", nonce_str);// 随机字符串
		packageParams.put("out_trade_no", out_trade_no);// 商户订单号
		packageParams.put("out_refund_no", out_refund_no);// 用户openid
		packageParams.put("total_fee", total_fee.toString());// 校验用户姓名选项 NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名
		packageParams.put("refund_fee", refund_fee.toString());// 企业付款金额，单位为分
		Signature.initKey(partnerKey);
		String sign = Signature.getSign(packageParams);
		RefundRequestXmlBean xmlBean = new RefundRequestXmlBean( appid,  mch_id,  nonce_str,  sign,  out_trade_no,
				 out_refund_no,  total_fee,  refund_fee);
		return xmlBean;
	}

	/**
	 * md5相关
	 * 
	 * @return
	 */
	public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams,
			String API_KEY) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}

		sb.append("key=" + API_KEY);

		// 算出摘要
		String mysign = MD5Encode(sb.toString(), characterEncoding).toLowerCase();
		String tenpaySign = ((String) packageParams.get("sign")).toLowerCase();

		// System.out.println(tenpaySign + " " + mysign);
		return tenpaySign.equals(mysign);
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	public static JSONObject createMenu(String accessToken) {
		System.out.println("开始调用创建菜单方法======createMenu");
		JSONObject jsonObject = new JSONObject();

		DefaultHttpClient httpclient = initHttpclient();

		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter("http.protocol.allow-circular-redirects", Boolean.valueOf(true));
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		String menu = null;
		try {
			//正式
//			 String postParam = "{\"button\":[{\"name\":\"个人信息\",\"sub_button\":[{\"type\":\"view\",\"name\":\"我的订单\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + 
//	    			 WXKeys.WX_APPID + "&redirect_uri=http://ajtservice.com/v1/page/orderList.htmls&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\"" + 
//	  		           "}," + 
//  		              "{" + 
//	    		        "\"type\":\"view\"," + 
//	    		        "\"name\":\"帐号信息\"," + 
//	    		        "\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +  WXKeys.WX_APPID + "&redirect_uri=http://ajtservice.com/v1/page/userInfo.htmls&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\"" + 
//	    		        "}" + 
//	    		        "]" + 
//	    		        "}," +  
//	    		        "{\"name\":\"业务说明\",\"sub_button\":[" +
//						"{" + 
//						"\"type\":\"view\"," + 
//						"\"name\":\"服务介绍\"," + 
//						"\"url\":\"http://ajtservice.com/v1/page/serviceInstroduction.htmls\"" + 
//						"}," +
//						 "{" + 
//						 "\"type\":\"view\"," + 
//						 "\"name\":\"使用说明\"," + 
//						 "\"url\":\"http://ajtservice.com/v1/page/useInstroduction.htmls\"" + 
//						 "}" + 				
//						"]" + 
//						"}," + 
//	    		        "{" + 
//	    		        "\"type\":\"view\"," + 
//	    		        "\"name\":\"立刻下单\"," + 
//	    		        "\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +  WXKeys.WX_APPID + "&redirect_uri=http://ajtservice.com/v1/page/orderStepOne.htmls&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\"" + 
//	    		        "}" + 
//	    		        "]" + 
//	    		        "}";
			 String postParam = "{\"button\":[{\"name\":\"行李到家\",\"sub_button\":[{\"type\":\"view\",\"name\":\"首都机场T2航站楼\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + 
					 WXKeys.WX_APPID + "&redirect_uri=http://ajtservice.com/v1/page/orderStepOne.htmls&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\"" + 
					 "}" +  
					 "]" + 
					 "}," +  
					 "{\"name\":\"出行资讯\",\"sub_button\":[" +
					 "{" + 
					 "\"type\":\"view\"," + 
					 "\"name\":\"服务介绍\"," + 
					 "\"url\":\"http://ajtservice.com/v1/page/serviceInstroduction.htmls\"" + 
					 "}," +
					 "{" + 
					 "\"type\":\"view\"," + 
					 "\"name\":\"航业知识\"," + 
					 "\"url\":\"http://mp.weixin.qq.com/mp/homepage?__biz=MzIxNzcwMTkwNw==&hid=2&sn=6ea6ac87303cad76511a0df28db59c51#wechat_redirect\"" + 
					 "}" + 				
					 "]" + 
					 "}," + 
					 "{\"name\":\"我的\",\"sub_button\":[" +
					 "{" + 
					 "\"type\":\"view\"," + 
					 "\"name\":\"我的订单\"," + 
					 "\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +  WXKeys.WX_APPID + "&redirect_uri=http://ajtservice.com/v1/page/orderList.htmls&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\"" + 
					 "}," +
					 "{" + 
					 "\"type\":\"view\"," + 
					 "\"name\":\"个人信息\"," + 
					 "\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +  WXKeys.WX_APPID + "&redirect_uri=http://ajtservice.com/v1/page/userInfo.htmls&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\"" + 
					 "}" + 				
					 "]" + 
					 "}" + 
					 "]" + 
					 "}";

			System.out.println("菜单：" + postParam);
			httpost.setEntity(new StringEntity(postParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			menu = EntityUtils.toString(response.getEntity(), "UTF-8");
			jsonObject = JSONObject.parseObject(menu);
			System.out.println("menu=====>" + menu);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpost.abort();
			httpclient.getConnectionManager().shutdown();
		}
		return jsonObject;
	}

	public static void main(String[] args) {
//		Refund("B201802250225cz3107001","wx123654987456", 1, 1);
		 createMenu("7_5-hDpCA9mQh2aLhOeEtUP4GzwKimKgrTo8Q86k9wpZCi5YdJWuJTyFj_DIpZsQ_weo7LFVdz6qVj65AKV8zB4sURdQl5Ed2l4M9CYttyMzEaK86_Js7ytY7pJHTeMziO0opucIheKAAYka4WHNQhABAOUX");

//			String msg = WeixinUtil.sendTemplate("", "7_wdQ2gG51hs7uWgHUX8OEIhAT7S-QcdQwDHr2QBeiRVvlJRWJFdtXkdMFrEpbCAXaWtfXGz7uMNYIvF3R8ztyCjenk0GdcB0Fq_8RO9WAknCuaVxxl_--Kc7OX8R-4i7OGKocdY_10m7O2q8RCVJcAJAWPD");
//			System.out.println(msg);
	}
}
