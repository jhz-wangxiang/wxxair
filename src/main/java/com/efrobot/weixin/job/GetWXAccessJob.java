package com.efrobot.weixin.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.efrobot.toolkit.util.http.HttpUtils;
import com.efrobot.toolkit.util.http.RestClient;
import com.efrobot.weixin.util.WeixinConfig;


/**
 * 
 * 类的描述:获取微信token,js ticket
 * Copyright 2015 JIQIREN Inc. All Rights Reserved.
 * @author wurui. Email:wurui@ren001.com
 * @date 2015年12月4日 下午2:40:35
 */

public class GetWXAccessJob {
//	private static final Logger log = Logger.getLogger(GetWXAccessJob.class);
	// wx token
	private static String accessToken = "";
	private static Date accessTokenExpireDate = null;
	// wx js ticket
	private static String jsTicket = "";
	private static Date jsTicketExpireDate = null;
	public static RestTemplate restTemplate = RestClient.getInstance();//请求类
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String getAccessToken() {
		return accessToken;
	}
	public static Date getAccessTokenExpireDate() {
		return accessTokenExpireDate;
	}
	public static String getJsTicket() {
		return jsTicket;
	}
	public static Date getJsTicketExpireDate() {
		return jsTicketExpireDate;
	}
	
	protected void execute(){
		try {
//			recharge();//轮询没有成功的充值
			// wx token
			if(!tokenValid()){
				//log.info("token需要重新获取!");
				// 重新获取
				getToken();
			}else{
				//log.debug("token有效无需重新获取!需要更新时间:"+format.format(accessTokenExpireDate));
			}
			
			// js ticket
			if(!ticketValid()){
				//log.info("ticket需要重新获取!");
				getTicket();
			}else{
				//log.debug("ticket无需重新获取,需要更新时间:"+format.format(jsTicketExpireDate));
			}
			
		} catch (Exception e) {
			//log.error("获取access出错了!");
			e.printStackTrace();
		}
	}
	
	/**
	 * true 表示无需检查
	 * @return
	 * @throws Exception
	 */
	private boolean tokenValid()throws Exception{
		boolean result = true;
		if(accessTokenExpireDate==null){
			result = false;
		}else{
			if(accessTokenExpireDate.before(new Date())){
				result = false;
			}
		}
		return result;
	}
	
	private void getToken()throws Exception{
		String url = WeixinConfig.getProperty("accessTokenJobUrl");
		JSONObject obj = getRequest(url);
		if(obj != null && obj.containsKey("access_token")){
			accessToken = obj.getString("access_token");
			accessTokenExpireDate = format.parse(obj.getString("expires_date"));
		}else{
			//log.warn("token同步失败!");
		}
	}
	
	private boolean ticketValid()throws Exception{
		boolean result = true;
		if(jsTicketExpireDate==null){
			result = false;
		}else{
			if(jsTicketExpireDate.before(new Date())){
				result = false;
			}
		}
		return result;		
	}
	
	private void getTicket()throws Exception{
		String url = WeixinConfig.getProperty("jsTicketJobUrl");
		JSONObject obj = getRequest(url);
		if(obj!=null && obj.containsKey("ticket")){
			jsTicket = obj.getString("ticket");
			jsTicketExpireDate = format.parse(obj.getString("expires_date"));
		}else{
			//log.warn("ticket同步失败!");
		}
	}	
	
	private JSONObject getRequest(String url)throws Exception{
		JSONObject obj = new JSONObject();
		String html = HttpUtils.get(url);
		//log.info("返回结果为:"+html);
		return obj.parseObject(html);
	}
}
