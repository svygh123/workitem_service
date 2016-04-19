package com.ndh.util;

import java.security.MessageDigest;

public class MD5Util {
	public MD5Util() {
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));// 若使用本函数转换则可得到加密结果的16进制表示，即数字字母混合的形式
			// resultSb.append(byteToNumString(b[i]));//使用本函数则返回加密结果的10进制数字字串，即全数字形式
		}
		return resultSb.toString();
	}

	private static String byteToNumString(byte b) {

		int _b = b;
		if (_b < 0) {
			_b = 256 + _b;
		}
		return String.valueOf(_b);
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String getMD5Code(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	/**
	 * 进行md5字符中的比较
	 * 
	 * @param md5a
	 *            String
	 * @param md5b
	 *            String
	 * @return boolean
	 */
	public static boolean compareMD5(String md5a, String md5b) {
		boolean flag = false;
		if (md5a == md5b) {
			return true;
		}
		try {
			java.security.MessageDigest alga = java.security.MessageDigest
					.getInstance("MD5");
			flag = alga.isEqual(md5a.getBytes(), md5b.getBytes());
		} catch (Exception ex) {
		}
		return flag;
	}

	public static void main(String args[]) {
		String m = MD5Util.getMD5Code("aisa100");
		System.out.println(m);

		String m1 = MD5Util.getMD5Code("fb271113cf95966bfc917a735d065ffa");
		System.out.println(m1);
		System.out.println(MD5Util.compareMD5(m1, m));
	}
}
