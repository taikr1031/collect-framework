package com.xxx.collect.core.tool;

public class UrlTool {

  private static int URL_MAX_LENGTH = 1500;

  public static String URL_REPLACE_CHAR_SPRIT = "|:|";
  public static String URL_REPLACE_CHAR_SPRIT_REG = "(\\|:\\|)+";
  public static String URL_REPLACE_CHAR_DOT = "|*|";

  /**
   * 保证url的长度不超过数据库url的最大长度限制
   * 
   * @param url
   * @return
   */
  public static String getSafeUrl(String url) {
    if (url == null)
      return null;
    if (url.length() >= URL_MAX_LENGTH)
      url = url.substring(0, URL_MAX_LENGTH - 1);
    return url;
  }

  /**
   * 这里把href部分关键字符替换，防止被爬虫获取
   * 
   * @param url
   * @return
   */
  public static String genJsHrefUrl(String url) {
    return url.replace("/", URL_REPLACE_CHAR_SPRIT).replace(".", URL_REPLACE_CHAR_DOT);
  }

}
