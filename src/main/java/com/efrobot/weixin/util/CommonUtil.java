package com.efrobot.weixin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 公共方法类
 * @author WangBin Email:wangbin@ren001.com
 *
 * @date 2016年4月26日 上午9:42:12
 */
public class CommonUtil {
	
	private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);
	
	/**
	 * 设置返回消息
	 */
	public static Map<String, Object> resultMsg(String resultCode, Object msg){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("resultCode", resultCode);
		map.put("msg", msg);
		
		return map;
	}
	
	/**
	 * 传参空值校验
	 * @param strArr
	 * @return
	 */
	public static Map<String, Object> verifyNull(String... strArr) {
		for (String str : strArr) {
			if (str == null || str.length() <= 0) {
				log.debug("参数不能为空， str:" + str);
				
				return CommonUtil.resultMsg("PARAM_FORMAT_ERROR", "参数不能为空");
			}
		}
		
		return null;
	}
	
	/**
	 * 限定一定长度获取验证码
	 * @param len
	 * @return
	 */
	public static StringBuffer buildRandomCode(Integer strLen) {
		char [] randomArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
				'0', '2', '3', '4', '5', '6', '7', '8', '9'};
		
		StringBuffer strList = new StringBuffer();
		Random random = new Random();
		
		int charLen = randomArray.length;
		for (int i = 0; i < strLen; i++) {
			strList.append(randomArray[random.nextInt(charLen)]);
		}
		
		return strList;
	}
	/**
	 * 限定一定长度获取验证码
	 * @param len
	 * @return
	 */
	public static StringBuffer buildRandomCode2(Integer strLen) {
//		char [] randomArray = {'A', 'B', 'L', 'D', 'E', 'F', 'C', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'Z', 'T', 'U', 'V', 'W', 'I', 'Y','O',
//				'0','1', '2', '3', '4', '5', '6', '7', '8', '9'};
		char [] randomArray = {'0','1', '2', '3', '4', '5', '6', '7', '8', '9'};
		
		StringBuffer strList = new StringBuffer();
		Random random = new Random();
		
		int charLen = randomArray.length;
		for (int i = 0; i < strLen; i++) {
			strList.append(randomArray[random.nextInt(charLen)]);
		}
		
		return strList;
	}
	
	/**
	 * 生成订单号
	 * @return
	 */
	public static String bulidOrderNum(String str) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String millisString = String.valueOf(System.currentTimeMillis());
		int millisStringLen = millisString.length();
		String orderNum = str + sdf.format(new Date()) + millisString.substring(millisStringLen - 10, millisStringLen)+buildRandomCode2(6);
		
		return orderNum;
	}
	public static boolean jisuan(Date start, Date end) throws Exception {
		long cha = end.getTime() - start.getTime();
		double result = cha * 1.0 / (1000 * 60 * 60);
		if (result <= 24) {
			if (result > 0) {
				return false;
			}
			// System.out.println("可用");
			return true;
		} else {
			// System.out.println("已过期");
			return false;
		}
	}
}
