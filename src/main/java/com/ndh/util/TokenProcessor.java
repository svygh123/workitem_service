package com.ndh.util;

import java.util.Random;

/**
 * 令牌处理器
 * @author Administrator
 */
public enum TokenProcessor {
	INSTANCE;

	public String generateToken(String seed, boolean isRadom) {
		Random random = new Random(System.currentTimeMillis());
		return random.nextInt() + seed;
	}

}
