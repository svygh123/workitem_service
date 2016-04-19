package com.ndh.rest;

import javax.annotation.PreDestroy;

import net.sf.ehcache.Element;
import net.sf.ehcache.CacheManager;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ndh.model.LoginUser;
import com.ndh.util.ConfigurationUtil;
import com.ndh.util.Constants;
import com.ndh.util.MD5Util;
import com.ndh.util.ThreadTokenHolder;
import com.ndh.util.TokenProcessor;

@Component
public class Memory {

	@Autowired
	private CacheManager cacheManager;

	/**
	 * 关闭缓存管理器
	 */
	@PreDestroy
	protected void shutdown() {
		if (cacheManager != null) {
			cacheManager.shutdown();
		}
	}

	/**
	 * 保存当前登录用户信息
	 * 
	 * @param loginUser
	 */
	public void saveLoginUser(LoginUser loginUser) {
		// 生成seed和token值
		String seed = MD5Util.getMD5Code(loginUser.getUsername());
		String token = TokenProcessor.INSTANCE.generateToken(seed, true);
		// 保存token到登录用户中
		loginUser.setToken(token);
		// 清空之前的登录信息
		clearLoginInfoBySeed(seed);
		// 保存新的token和登录信息
		String timeout = ConfigurationUtil.getTokenTimeOut();
		int ttiExpiry = NumberUtils.toInt(timeout);
		cacheManager.getCache(Constants.TOKEN_CACHE).put(new Element(seed, token, false, ttiExpiry, 0));
		cacheManager.getCache(Constants.TOKEN_CACHE).put(new Element(token, loginUser, false, ttiExpiry, 0));
	}

	/**
	 * 获取当前线程中的用户信息
	 * 
	 * @return
	 */
	public LoginUser currentLoginUser() {
		Element element = cacheManager.getCache(Constants.TOKEN_CACHE).get(ThreadTokenHolder.getToken());
		return element == null ? null : (LoginUser) element.getValue();
	}

	/**
	 * 根据token检查用户是否登录
	 * 
	 * @param token
	 * @return
	 */
	public boolean checkLoginInfo(String token) {
		Element element = cacheManager.getCache(Constants.TOKEN_CACHE).get(token);
		return element != null && (LoginUser) element.getValue() != null;
	}

	/**
	 * 清空登录信息
	 */
	public void clearLoginInfo() {
		LoginUser loginUser = currentLoginUser();
		if (loginUser != null) {
			// 根据登录的用户名生成seed，然后清除登录信息
			String seed = MD5Util.getMD5Code(loginUser.getUsername());
			clearLoginInfoBySeed(seed);
		}
	}

	/**
	 * 根据seed清空登录信息
	 * 
	 * @param seed
	 */
	public void clearLoginInfoBySeed(String seed) {
		// 根据seed找到对应的token
		Element element = cacheManager.getCache(Constants.TOKEN_CACHE).get(seed);
		if (element != null) {
			// 根据token清空之前的登录信息
			cacheManager.getCache(Constants.TOKEN_CACHE).remove(seed);
			cacheManager.getCache(Constants.TOKEN_CACHE).remove(element.getValue());
		}
	}
}
