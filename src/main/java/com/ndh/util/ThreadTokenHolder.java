package com.ndh.util;

/**
 * 令牌线程 : 保存当前令牌的线程
 * @author Administrator
 */
public class ThreadTokenHolder {

	public static final ThreadLocal<String> tokens = new ThreadLocal<String>();

	public static void setToken(String token) {
		tokens.set(token);
	}

	public static String getToken() {
		return tokens.get();
	}

	public static void delToken() {
		tokens.remove();
	}
}
