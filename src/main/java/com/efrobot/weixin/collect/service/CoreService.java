package com.efrobot.weixin.collect.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface CoreService {

	String processRequest(HttpServletRequest request,
			HttpServletResponse response, String xmlReceivedMessage)  throws Exception;

}
