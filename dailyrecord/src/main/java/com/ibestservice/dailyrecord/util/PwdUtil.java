package com.ibestservice.dailyrecord.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PwdUtil {
	
	public static final String KEY_SHA = "SHA";
	
	public static final String KEY_MD5 = "MD5";

	/**
	 * 16进制字符
	 */
	static String[] chars = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	/**
	 * SHA加密
	 * 
	 * @param pwd
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static BigInteger ShaPwd(String pwd) throws NoSuchAlgorithmException {
		byte[] result = pwd.getBytes();
		MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
		messageDigest.update(result);
		BigInteger sha = new BigInteger(messageDigest.digest());
		return sha;
	}
	
	/**
	 * MD5加密
	 * @param pwd
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String Md5Pwd(String pwd) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		byte[] result = md5.digest(pwd.getBytes());

		StringBuilder sb = new StringBuilder(32);
		// 将结果转为16进制字符 0~9 A~F
		for (int i = 0; i < result.length; i++) {
			// 一个字节对应两个字符
			byte x = result[i];
			// 取得高位
			int h = 0x0f & (x >>> 4);
			// 取得低位
			int l = 0x0f & x;
			sb.append(chars[h]).append(chars[l]);
		}
		return sb.toString();
	}
}
