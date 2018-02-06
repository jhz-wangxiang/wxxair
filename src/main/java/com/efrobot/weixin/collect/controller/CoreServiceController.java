package com.efrobot.weixin.collect.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efrobot.weixin.collect.service.CoreService;
import com.efrobot.weixin.util.StringUtil;
@Controller
@RequestMapping("/coreService")
public class CoreServiceController {
	
	@Resource
	private CoreService coreService;	
	
	private static  final Logger log=Logger.getLogger(CoreServiceController.class);
	
	
	private static List<String> RECEIVEDMESSAGE_LIST = new ArrayList<String>();
	
	
	@RequestMapping(value="/msgInfo" ,method=RequestMethod.GET)
	public String authorization(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8") ;
		PrintWriter out = response.getWriter() ;
		
		String signature = request.getParameter("signature") ;
		
		String echo = request.getParameter("echostr") ;
		String timestamp = request.getParameter("timestamp") ;
		String nonce = request.getParameter("nonce");
		
		if(echo==null){
			out.println("<html><body><h1>It works!</h1></body></html>");
			out.close();
			return null;
		}
		log.info("-----It works!-----code:"+echo);
		
		if(StringUtil.checkSignature(signature, timestamp, nonce)){
			echo = new String(echo.getBytes("ISO-8859-1"),"UTF-8");
	        out.println(echo);
	        log.info("验证成功！！");
		}else{
			echo = new String(echo.getBytes("ISO-8859-1"),"UTF-8");
			out.println(echo);
		}
		return null;
	}
	
	/**
	 * <xml><ToUserName><![CDATA[gh_88f4a49fcf2f]]></ToUserName>
	 *	<FromUserName><![CDATA[oEFIYv1ntXMQCHExqBjKglzBZ7pY]]></FromUserName>
	 *	<CreateTime>1447219937</CreateTime>
	 *	<MsgType><![CDATA[event]]></MsgType>
	 *	<Event><![CDATA[subscribe]]></Event>
	 *	<EventKey><![CDATA[]]></EventKey>
	 *	</xml>
	 * 接收微信post 数据
	 **/
	@RequestMapping(value="/msgInfo" ,method=RequestMethod.POST)
	public void getMsg(HttpServletRequest request,HttpServletResponse response){
		
		log.info("接收微信post 数据 === getMsg==== start");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		ServletInputStream in = null;
		String xmlReceivedMessage = null;
		try{
			int len = request.getContentLength();
			byte[] b = new byte[len];
			in = request.getInputStream();
			in.read(b);
			in.close();	
			xmlReceivedMessage = new String(b,"utf-8");
			log.info("请求xmlReceivedMessage======>"+xmlReceivedMessage);	
			if(RECEIVEDMESSAGE_LIST.contains(xmlReceivedMessage)){
				log.info("【getMsg】（没有相应，微信重复不断请求的情况）该请求消息为重复消息，将不做任何处理");
				response.getWriter().print("success");
			}else{
				RECEIVEDMESSAGE_LIST.add(xmlReceivedMessage);
				if(RECEIVEDMESSAGE_LIST.size() > 500000){
					RECEIVEDMESSAGE_LIST.clear();
				}
				String respMessageXml = coreService.processRequest(request, response,xmlReceivedMessage);
				log.info("回复respMessageXml====>"+respMessageXml);
				if(respMessageXml.contains("请求处理异常，请稍候尝试！") || respMessageXml.contains("SUCCESS")){
					response.getWriter().print("success");
					return;
				}
				response.getWriter().print(respMessageXml);
			}
		}catch (Exception e) {
			log.error("【msgInfo】：xmlReceivedMessage"+xmlReceivedMessage);
			log.error("【msgInfo】出错啦！！！！", e);
		}
	}


}
