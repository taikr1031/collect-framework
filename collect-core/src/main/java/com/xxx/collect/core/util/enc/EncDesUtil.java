package com.xxx.collect.core.util.enc;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES 可逆对称加密
 */
public class EncDesUtil {

	public static void main(String[] args) {
		String privateKey = "cookie_local_key";
		for (int i = 0; i < 123456; i++) {
			String encrypt = EncDesUtil.encrypt("visitorId:a12afcd9b6604bf6b4c465dc5261cf40",
					privateKey);
			String de = EncDesUtil.decrypt(encrypt, privateKey);
			//System.out.println(encrypt);
			//System.out.println(encrypt.length());
			//System.out.println(de);
		}
	}

	/**
	 * 密码解密,privateKey 为私钥，必须大于等于8位
	 */
	public final static String decrypt(String data, String privateKey) {

		try {
			return new String(decrypt(hex2byte(data.getBytes()), privateKey));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 密码加密,privateKey 为私钥，必须大于等于8位
	 */
	public final static String encrypt(String password, String privateKey) {

		try {

			return byte2hex(encrypt(password.getBytes(), privateKey));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// private static final String PASSWORD_CRYPT_KEY = "19830925";
	private final static String MODEL = "DES";
	private static Cipher cipherDe;
	private static Cipher cipherEn;

	private static Cipher getCipherDe(String privateKey) {
		if (cipherDe == null) {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			// 从原始密匙数据创建DESKeySpec对象
			DESKeySpec dks;
			try {
				dks = new DESKeySpec(privateKey.getBytes());
				// 创建一个密匙工厂，然后用它把DESKeySpec转换成
				// 一个SecretKey对象
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(MODEL);
				SecretKey securekey = keyFactory.generateSecret(dks);
				// Cipher对象实际完成加密操作
				Cipher cipher = Cipher.getInstance(MODEL);
				cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
				cipherDe = cipher;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return cipherDe;
	}

	private static Cipher getCipherEn(String privateKey) {
		if (cipherEn == null) {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks;
			try {
				dks = new DESKeySpec(privateKey.getBytes());
				// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
				// 一个SecretKey对象
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(MODEL);
				SecretKey securekey = keyFactory.generateSecret(dks);
				// Cipher对象实际完成解密操作
				Cipher cipher = Cipher.getInstance(MODEL);
				// 用密匙初始化Cipher对象
				cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
				cipherEn = cipher;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return cipherEn;
	}

	/**
	 * 
	 * 加密
	 * 
	 * @param src
	 *            数据源
	 * 
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * 
	 * @return 返回加密后的数据
	 * 
	 * @throws Exception
	 */

	private static byte[] encrypt(byte[] src, String privateKey) throws Exception {
		return getCipherEn(privateKey).doFinal(src);

	}

	/**
	 * 
	 * 解密
	 * 
	 * @param src
	 *            数据源
	 * 
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * 
	 * @return 返回解密后的原始数据
	 * 
	 * @throws Exception
	 */

	private static byte[] decrypt(byte[] src, String privateKey) throws Exception {
		return getCipherDe(privateKey).doFinal(src);

	}

	/**
	 * 
	 * 二行制转字符串
	 * 
	 * @param b
	 * 
	 * @return
	 */
	private static String byte2hex(byte[] b) {

		String hs = "";

		String stmp = "";

		for (int n = 0; n < b.length; n++) {

			stmp = (Integer.toHexString(b[n] & 0XFF));

			if (stmp.length() == 1)

				hs = hs + "0" + stmp;

			else

				hs = hs + stmp;

		}

		return hs.toLowerCase();

	}

	private static byte[] hex2byte(byte[] b) {

		if ((b.length % 2) != 0)

			throw new IllegalArgumentException("长度不是偶数");

		byte[] b2 = new byte[b.length / 2];

		for (int n = 0; n < b.length; n += 2) {

			String item = new String(b, n, 2);

			b2[n / 2] = (byte) Integer.parseInt(item, 16);

		}

		return b2;
	}
}
