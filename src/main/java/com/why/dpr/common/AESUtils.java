package com.why.dpr.common;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import java.net.URLEncoder;
import java.net.URLDecoder;

/**
 * AES加密工具类,加密解密,urlEncode，urlDncode 兼容ios、android、java平台
 * 
 * @author Haiyang.Wu
 */
public class AESUtils {

	private static final byte[] ENCRYPT_IV = { 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
			0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0 };
	private static final String CHARSET = "UTF-8";

	/**
	 * 加密
	 * 
	 * @param dataPassword
	 *            密码
	 * @param cleartext
	 *            待加密字符串
	 * @return AES加密字符串Base64编码
	 * @throws Exception
	 */
	public static String encrypt(String dataPassword, String cleartext)
			throws Exception {
		IvParameterSpec zeroIv = new IvParameterSpec(ENCRYPT_IV);
		SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(cleartext.getBytes(CHARSET));

		return new String(Base64.encodeBase64(encryptedData));
	}

	/**
	 * @param dataPassword
	 *            密码
	 * @param cleartext
	 *            待加密字符串
	 * @return AES加密字符串Base64编码，urlEncode
	 * @throws Exception
	 */
	public static String encryptUrlEncode(String dataPassword, String cleartext)
			throws Exception {

		return URLEncoder.encode(encrypt(dataPassword, cleartext), CHARSET);
	}

	/**
	 * @param dataPassword
	 *            密码
	 * @param encrypted
	 *            AES加密后字符串
	 * @return Base64解码,AES解密
	 * @throws Exception
	 */
	public static String decrypt(String dataPassword, String encrypted)
			throws Exception {
		byte[] byteMi = Base64.decodeBase64(encrypted);
		IvParameterSpec zeroIv = new IvParameterSpec(ENCRYPT_IV);
		SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte[] decryptedData = cipher.doFinal(byteMi);

		return new String(decryptedData, CHARSET);
	}

	/**
	 * @param dataPassword
	 *            密码
	 * @param encrypted
	 *            AES加密后字符串
	 * @return urlDecode,Base64解码,AES解密
	 * @throws Exception
	 */
	public static String decryptUrlDecode(String dataPassword, String encrypted)
			throws Exception {
		return decrypt(dataPassword, URLDecoder.decode(encrypted, CHARSET));
	}
}
