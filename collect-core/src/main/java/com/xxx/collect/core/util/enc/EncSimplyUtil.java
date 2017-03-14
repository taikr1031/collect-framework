package com.xxx.collect.core.util.enc;

import java.math.BigInteger;

public class EncSimplyUtil {

	public static void main(String args[]) {
		for (int i = 0; i < 1000; i++) {
			String str = "msrck00d4bf27275b6c4976811e89ce952d075f.png";
			String encryptPassword = encrypt(str);
			decrypt(encryptPassword);
			System.out.println(" encrypt password is " + encryptPassword);
			System.out.println(i+" encrypt password lenght " + encryptPassword.length());
			System.out.println(" decrypt password is " + decrypt("5400b860fa6cc0c03946780f00ac5db08596c0c2961f35a607b28e0f3c28a123009b365d07291838"));
		}
	}

	private static int RADIX = 32;
	private static final String SEED = "19850709";

	public static final String encrypt(String password) {
		if (password == null)
			return "";
		if (password.length() == 0)
			return "";

		BigInteger bi_passwd = new BigInteger(password.getBytes());

		BigInteger bi_r0 = new BigInteger(SEED);
		BigInteger bi_r1 = bi_r0.xor(bi_passwd);

		return bi_r1.toString(RADIX);
	}

	public static final String decrypt(String encrypted) {
		if (encrypted == null)
			return "";
		if (encrypted.length() == 0)
			return "";

		BigInteger bi_confuse = new BigInteger(SEED);

		try {
			BigInteger bi_r1 = new BigInteger(encrypted, RADIX);
			BigInteger bi_r0 = bi_r1.xor(bi_confuse);

			return new String(bi_r0.toByteArray());
		} catch (Exception e) {
			return "";
		}
	}

}
