package com.efrobot.weixin.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SerialNum {
	private static String SXX = "000";

	private static SerialNum primaryGenerater = null;

	private SerialNum() {

	}

	/**
	 * 取得PrimaryGenerater的单例实现
	 *
	 * @return
	 */
	public static SerialNum getInstance() {
		if (primaryGenerater == null) {
			synchronized (SerialNum.class) {
				if (primaryGenerater == null) {
					primaryGenerater = new SerialNum();
				}
			}
		}
		return primaryGenerater;
	}

	/**
	 * 系统订单号 每月从0001开始计数
	 */
	public static synchronized String getSystemManageOrder() {
			DecimalFormat df = new DecimalFormat("000");
		String id =	df.format(1 + Integer.parseInt(SXX));
		if(id.length()>3){
			id="001";
		}
		SXX=id;
		return id;
	}

	

	public static void main(String[] args) {
		/**
		 * HHTG170980001 HH公司简称 + TG业务类型 + 年月 + 8部门 + 0001
		 * 
		 */
			System.out.println(getSystemManageOrder());
	}
}