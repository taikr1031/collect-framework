package com.xxx.collect.core.util;

import com.xxx.collect.core.util.string.StringUtil;

public class UrlParser {

  /**
   * // 把绝对路径和相对路径都转化为绝对路径
   * 
   * @param parentUrl
   *          : (带http前缀和域名的绝对地址)
   * @param url
   * @return (返回带http前缀和域名的绝对地址)
   */
  public static String getAbsoluteUrl(String parentUrl, String url) {
    url = url.trim();
    String restUrl = url;
    if (StringUtil.isBlank(url))
      return parentUrl;
    if (!url.contains("http:")) {
      if ("/".equals(url.substring(0, 1))) {
        restUrl = getHostUrl(parentUrl) + url;
      } else if ("?".equals(url.substring(0, 1))) {
        restUrl = parentUrl + url;
      } else {
        restUrl = parentUrl + "/" + url;
      }
    }
    int indexOfJing = restUrl.indexOf("#");
    if (indexOfJing > 0)
      restUrl = restUrl.substring(0, indexOfJing);
    restUrl = StringUtil.deleteHead(restUrl, "http://");
    // 把两个以上的 / 去重
    restUrl = RegexUtil.replace(restUrl, "/+", "/");
    // 去除最后结尾的 /
    restUrl = StringUtil.deleteLast(restUrl, "/");
    return "http://" + restUrl;
  }

  /**
   * @param url
   *          : http://t.qq.com/people/
   * @return http://t.qq.com
   */
  public static String getHostUrl(String url) {
    String site = RegexUtil.searchBetween(url, "(http://[^/]*)");
    return site;
  }

  /**
   * @param url
   *          : http://t.qq.com/people/
   * @return t.qq.com
   */
  public static String getHostName(String url) {
    String site = RegexUtil.searchBetween(url, "http://([^/]+).*");
    return site;
  }

  public static void main(String[] args) {
    String url = "http://t.qq.com/sdfsdf?";
    System.out.println(getAbsoluteUrl(url, "123/#"));
  }

}
