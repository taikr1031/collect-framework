package com.xxx.collect.core.util.base64;

import java.io.UnsupportedEncodingException;

/**
 * @author Lenovo 安全的base64 将字符串中的加号+换成中划线-，并且将斜杠/换成下划线_，同时尾部保持填充等号=。
 */
public class Bese64Util {
  public static String encode(byte[] bytes) {
    return safeStr(Base64Encoder.encode(bytes));
  }

  public static byte[] decode(String str) {
    return Base64Decoder.decodeToBytes(safeStr(str));
  }

  public static String encodeUtf8(String str) {
    try {
      return encode(str.getBytes("utf-8"));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  public static String decodeUtf8(String str) {
    byte[] bytes = decode(str);
    try {
      return new String(bytes, "utf-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  private static String safeStr(String oldStr) {
    return oldStr.replace("+", "-").replace("/", "_");
  }
}
