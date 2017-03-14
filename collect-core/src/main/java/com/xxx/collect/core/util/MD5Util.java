package com.xxx.collect.core.util;

import com.xxx.collect.core.util.io.IOUtil;
import org.apache.commons.codec.binary.Hex;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

  public static String md5(byte[] btInput) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
        'F'};
    try {
      // 使用指定的字节更新摘要
      md.update(btInput);
      // 获得密文
      byte[] mdBytes = md.digest();
      // 把密文转换成十六进制的字符串形式
      int j = mdBytes.length;
      char str[] = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = mdBytes[i];
        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        str[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(str).toLowerCase();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String md5(File file) {
    FileInputStream fileInputStream = null;
    try {
      fileInputStream = new FileInputStream(file);
      return md5(fileInputStream);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } finally {
      if (fileInputStream != null)
        try {
          fileInputStream.close();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
    }
  }

  /**
   * 对一个文件求他的md5值
   * <p>
   * 要求md5值的文件
   *
   * @return md5串
   */
  public static String md5(InputStream fis) {
    byte[] buffer = new byte[8192];
    int length;
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    try {
      while ((length = fis.read(buffer)) != -1) {
        md.update(buffer, 0, length);
      }
      fis.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return new String(Hex.encodeHex(md.digest()));
  }

  public static void main(String[] args) throws FileNotFoundException {
    String str1 = "201212212012122120121221201";
    String str2 = "中文";
    String md51 = MD5Util.md5(str1.getBytes());
    String md52 = MD5Util.md5(str2.getBytes());
    String md53 = md5(IOUtil.bytes2InputStream(str1.getBytes()));
    String md54 = md5(IOUtil.bytes2InputStream(str2.getBytes()));
    System.out.println(md51.equals(md53));
    System.out.println(md52.equals(md54));
    File bigFile = new File("F:\\我的常用软件\\开发工具\\rose.rar");
    FileInputStream bigIS = new FileInputStream(bigFile);
    String md5 = md5(bigIS);
    System.out.println("大文件md5：" + md5);

  }
}
