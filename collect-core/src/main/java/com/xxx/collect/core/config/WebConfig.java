package com.xxx.collect.core.config;

import com.xxx.collect.core.util.HttpUtil;
import com.gei.framework.web.filter.VariableInitFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class WebConfig {


  public static String URL_PATH_FILE_DOWN = "/file/down/file/";
  public static String WebDomain = "2gei.com";
  public static String LocalDomain = "codefans.com";

  public static String getRootDomain() {
    // 开启本地调试模拟真实域名，主要用于真实的qq登录等，需要配合改hosts文件,发布版本时，这里要设置为false
    boolean openLocalMockRealDomain = false;
    return (!VariableInitFilter.LOCAL_DEBUG_MODEL || openLocalMockRealDomain) ? WebDomain : LocalDomain;
  }

  public static String getMainDomain() {
    return "www." + getRootDomain();
  }

  /**
   * cdn回源专用域名
   */
  public static String getForCdnSrcDomain() {
    return "cdn-only." + getRootDomain();
  }

  public static String getMobileDomain() {
    return "m." + getRootDomain();
  }

  /**
   * 是否是内部域名
   * 
   * @param domain
   * @return
   */
  public static boolean isInnerDomain(String domain) {
    if (domain == null)
      return true;
    for (String header : Arrays.asList(new String[] { "localhost", getForCdnSrcDomain(), getMobileDomain(),
        getRootDomain(), getMainDomain() })) {
      if (domain.startsWith(header))
        return true;
    }
    return false;
  }

  /**
   * 是否是内部url
   * 
   * @return
   */
  public static boolean isInnerUrl(String url) {
    return WebConfig.isInnerDomain(HttpUtil.getDomain(url));
  }

  /**
   * 是否是移动端网站访问
   * 
   * @param request
   * @return
   */
  public static boolean isMobileSite(HttpServletRequest request) {
    return request.getServerName().startsWith("m.");
  }

}
