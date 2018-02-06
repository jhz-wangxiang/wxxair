package com.efrobot.weixin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Description
 *
 * @author wurui. Email:wurui@ren001.com
 * @date 2015年12月25日 下午3:28:07
 */
public class WeixinConfig {
	private static final Logger log = LoggerFactory.getLogger(WeixinConfig.class);

	private static String configFile = "/weixin_cfg.properties";

	private static Properties prop = new Properties();

	static {
		InputStream is = WeixinConfig.class.getResourceAsStream(configFile);
		if (is == null) {
			log.error(configFile + "文件不存在,请检查");
		} else {
			try {
				BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				prop.load(bf);
			} catch (Exception e) {
				log.error("读取" + configFile + "出错:" + e.getMessage());
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						log.error("关闭IO流出错:" + e.getMessage());
					}
				}
			}
		}
	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

	public static void main(String[] args) {
		String athena_config_test = WeixinConfig.getProperty("id");
		System.out.print(athena_config_test);
	}
}
