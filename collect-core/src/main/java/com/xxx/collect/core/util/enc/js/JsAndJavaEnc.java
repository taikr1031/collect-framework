package com.xxx.collect.core.util.enc.js;

import com.xxx.collect.core.util.MD5Util;
import com.xxx.collect.core.util.RegexUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * java端和js端配套的des加密解密
 *
 * @author
 */
public class JsAndJavaEnc {

  private static SecretKeyFactory keyFactory;

  public static String jsKeyStr = "function";

  /**
   * 根据参数生成KEY
   */
  static {
    try {
      keyFactory = SecretKeyFactory.getInstance("DES");
    } catch (Exception e) {
      throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
    }
  }

  /**
   * 加密String明文输入,String密文输出
   */
  public static String encrypt(String strMing, String keyStr) {
    Key key = null;
    try {
      key = keyFactory.generateSecret(new DESKeySpec(keyStr.getBytes("UTF8")));
    } catch (InvalidKeyException | InvalidKeySpecException | UnsupportedEncodingException e1) {
      throw new RuntimeException(e1);
    }
    byte[] byteMi = null;
    byte[] byteMing = null;
    String strMi = "";
    BASE64Encoder base64en = new BASE64Encoder();
    try {
      byteMing = strMing.getBytes("UTF8");
      byteMi = getEncCode(byteMing, key);
      strMi = base64en.encode(byteMi);
    } catch (Exception e) {
      throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
    } finally {
      base64en = null;
      byteMing = null;
      byteMi = null;
    }
    //去除空行by luju
    strMi = RegexUtil.replace(strMi, "\\s+", "");
    return strMi;
  }

  /**
   * 解密 以String密文输入,String明文输出
   *
   * @param strMi
   * @return
   */
  public static String decrypt(String strMi, String keyStr) {
    Key key = null;
    try {
      key = keyFactory.generateSecret(new DESKeySpec(keyStr.getBytes("UTF8")));
    } catch (InvalidKeyException | InvalidKeySpecException | UnsupportedEncodingException e1) {
      throw new RuntimeException(e1);
    }
    BASE64Decoder base64De = new BASE64Decoder();
    byte[] byteMing = null;
    byte[] byteMi = null;
    String strMing = "";
    try {
      byteMi = base64De.decodeBuffer(strMi);
      byteMing = getDesCode(byteMi, key);
      strMing = new String(byteMing, "UTF8");
    } catch (Exception e) {
      throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
    } finally {
      base64De = null;
      byteMing = null;
      byteMi = null;
    }
    return strMing;
  }

  /**
   * 加密以byte[]明文输入,byte[]密文输出
   *
   * @param byteS
   * @return
   */
  private static byte[] getEncCode(byte[] byteS, Key key) {
    byte[] byteFina = null;
    Cipher cipher;
    try {
      cipher = Cipher.getInstance("DES");
      cipher.init(Cipher.ENCRYPT_MODE, key, SecureRandom.getInstance("SHA1PRNG"));
      byteFina = cipher.doFinal(byteS);
    } catch (Exception e) {
      throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
    } finally {
      cipher = null;
    }
    return byteFina;
  }

  /**
   * 解密以byte[]密文输入,以byte[]明文输出
   *
   * @param byteD
   * @return
   */
  private static byte[] getDesCode(byte[] byteD, Key key) {
    Cipher cipher;
    byte[] byteFina = null;
    try {
      cipher = Cipher.getInstance("DES");
      cipher.init(Cipher.DECRYPT_MODE, key, SecureRandom.getInstance("SHA1PRNG"));
      byteFina = cipher.doFinal(byteD);
    } catch (Exception e) {
      throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
    } finally {
      cipher = null;
    }
    return byteFina;
  }

  public static void main(String args[]) {
    String str1 = "pkg_items:2274163,2269176,2272785,2269475,2272872,2269334,2271260,2271254,2271251,2271243";
    // DES加密
    String enc = encrypt(str1, jsKeyStr);
//    for(int i=0;i<str2.length();i++) {
//      char c = str2.charAt(i);
//      System.out.println(i+":"+c);
//    }
    String deStr = decrypt(enc, jsKeyStr);
    System.out.println("密文:" + enc);
    // DES解密
    System.out.println("明文:" + deStr);
    System.out.println("md5:" + MD5Util.md5(str1.getBytes()));

  }

}
