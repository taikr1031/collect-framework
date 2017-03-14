package com.xxx.collect.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by luju on 2015/12/21.
 */
public class URLCoderUtil {

  public static String encode(String str){
    try {
      return URLEncoder.encode(str,"utf-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 将空格普通的编码会把空格替换为+，这个是为七牛的编码，空格替换为%20
   * @param str
   * @return
   */
  public static String encodeForQiniu(String str){
    try {
      String encode = URLEncoder.encode(str, "utf-8");
      encode = encode.replace("+","%20");
      return encode;
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }


  public static String decode(String str){
    try {
      return URLDecoder.decode(str, "utf-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
