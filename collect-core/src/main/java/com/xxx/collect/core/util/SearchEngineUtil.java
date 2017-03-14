package com.xxx.collect.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 搜索引擎识别的工具类
 */
public class SearchEngineUtil {
  public static void main(String[] args) {
    String url = "http://www.google.com.hk/#newwindow=1&safe=strict&q=%E6%88%91%E6%88%91%E6%88%91123";
    System.out.println(getDomain(url) + " : " + SearchEngineUtil.getKeyword(url));
    System.out.println("");
    url = "http://www.haosou.com/s?ie=utf-8&shb=1&src=360sou_newhome&q=%E6%88%91%E6%88%91";
    System.out.println(getDomain(url) + " : " + SearchEngineUtil.getKeyword(url));
    System.out.println("");
    url = "http://www.sogou.com/web?sss=111&query=java%20%E8%A7%A3%E6%9E%90%E6%90%9C%E7%B4%A2%E5%BC%95%E6%93%8E%20%E5%85%B3%E9%94%AE%E8%AF%8D&_ast=&ie=utf8&pid=s.idx&cid=s.idx.se&rfrom=so1so&unc=&w=01019900&sut=1582&sst0=1428067285490&lkt=0%2C0%2C0";
    System.out.println(getDomain(url) + " : " + SearchEngineUtil.getKeyword(url));
    System.out.println("");
    url = "http://www.baidu.com/s?ie=UTF-8&wd=java%20%E8%A7%A3%E6%9E%90%E6%90%9C%E7%B4%A2%E5%BC%95%E6%93%8E%20%E5%85%B3%E9%94%AE%E8%AF%8D&rt=123";
    System.out.println(getDomain(url) + " : " + SearchEngineUtil.getKeyword(url));
    System.out.println("");
    url = "http://cn.bing.com/search?q=%E7%88%B1%E7%BB%99%E7%BD%91+music&go=%E6%8F%90%E4%BA%A4&qs=n&form=QBLH&pq=%E7%88%B1%E7%BB%99%E7%BD%91+music&sc=0-4&sp=-1&sk=&cvid=ecaa51bb03274b509ff215638712d98f";
    System.out.println(getDomain(url) + " : " + SearchEngineUtil.getKeyword(url));
    System.out.println("");
  }

  private static List<String> listSpiderKey = Arrays.asList(new String[] { "Googlebot", "Sogou web spider", "bingbot",
      "Baiduspider", "YoudaoBot", "YisouSpider", "BLEXBot", "360Spider" });

  /**
   * 判断是否是搜索引擎的爬虫
   * 
   * @param userAgent
   * @return
   */
  public static boolean isSpider(String userAgent) {
    if (userAgent == null)
      return false;
    for (String spiderKey : listSpiderKey) {
      if (userAgent.contains(spiderKey))
        return true;
    }
    return false;
  }

  public static String getDomain(String url) {
    if (url.contains("//www.google."))
      return "google";
    if (url.contains("//www.haosou."))
      return "haosou";
    if (url.contains("//www.baidu."))
      return "baidu";
    if (url.contains("bing.com/"))
      return "bing";
    if (url.contains("//www.sogou."))
      return "sogou";
    return null;
  }

  public static String getKeyword(String url) {
    String keywordReg = "(?:haosou.+?q=|bing.+?q=|sogou.+?query=|google.+?q=|baidu.+?wd=)([^&]*)";
    String encodeReg = "^(?:[\\x00-\\x7f]|[\\xfc-\\xff][\\x80-\\xbf]{5}|[\\xf8-\\xfb][\\x80-\\xbf]{4}|[\\xf0-\\xf7][\\x80-\\xbf]{3}|[\\xe0-\\xef][\\x80-\\xbf]{2}|[\\xc0-\\xdf][\\x80-\\xbf])+$";
    Pattern keywordPatt = Pattern.compile(keywordReg);
    StringBuffer keyword = new StringBuffer(20);
    Matcher keywordMat = keywordPatt.matcher(url);
    while (keywordMat.find()) {
      keywordMat.appendReplacement(keyword, "$1");
    }
    if (!keyword.toString().equals("")) {
      String keywordsTmp = keyword.toString().replace("http://www.", "");
      keywordsTmp = keywordsTmp.toString().replace("https://www.", "");
      keywordsTmp = keywordsTmp.toString().replace("http://cn.", "");
      keywordsTmp = keywordsTmp.toString().replace("https://cn.", "");
      Pattern encodePatt = Pattern.compile(encodeReg);
      String unescapeString = SearchEngineUtil.unescape(keywordsTmp);
      Matcher encodeMat = encodePatt.matcher(unescapeString);
      String encodeString = "gbk";
      if (encodeMat.matches())
        encodeString = "utf-8";
      try {
        return URLDecoder.decode(keywordsTmp, encodeString);
      } catch (UnsupportedEncodingException e) {
        return null;
      }
    }
    return null;
  }

  public static String unescape(String src) {
    StringBuffer tmp = new StringBuffer();
    tmp.ensureCapacity(src.length());
    int lastPos = 0, pos = 0;
    char ch;
    while (lastPos < src.length()) {
      pos = src.indexOf("%", lastPos);
      if (pos == lastPos) {
        if (src.charAt(pos + 1) == 'u') {
          ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
          tmp.append(ch);
          lastPos = pos + 6;
        } else {
          ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
          tmp.append(ch);
          lastPos = pos + 3;
        }
      } else {
        if (pos == -1) {
          tmp.append(src.substring(lastPos));
          lastPos = src.length();
        } else {
          tmp.append(src.substring(lastPos, pos));
          lastPos = pos;
        }
      }
    }
    return tmp.toString();
  }
}
