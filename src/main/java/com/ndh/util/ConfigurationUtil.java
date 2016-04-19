package com.ndh.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class ConfigurationUtil {
	
	/**
	 * 获取令牌过期时间
	 * @return
	 */
	public static String getTokenTimeOut() {
		String timeOut = "0";
		try {
			XMLConfiguration config = new XMLConfiguration("ehcache.xml");
			timeOut = config.getString("cache[@timeToLiveSeconds]");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		return timeOut;
	}
}
