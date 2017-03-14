package com.xxx.collect.core.util;

import com.xxx.collect.core.util.httpclient.impl.HttpClientCommon;
import com.xxx.collect.core.util.httpclient.model.HttpResult;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class IpUtil {

  public static void main(String[] args) throws IOException {
//
//    String ip1 = "192.168.0.1";
//    int intIp = Ip2Int(ip1);
//    String ip2 = int2Ip(intIp);
//    System.out.println(ip2.equals(ip1));

    System.out.println(getLocalIp());
  }

  /**
   * 通过访问百度获取本机ip
   * @return
   * @throws IOException
   */
  public static String getLocalIp() throws IOException {
    HttpClientCommon clientIp = new HttpClientCommon();
    HttpResult rest = clientIp.visit("http://www.baidu.com/s?wd=ip");
    String ip = RegexUtil.searchBetween(rest.getHtml(), "<span class=\"c-gap-right\">本机IP:&nbsp;(\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})</span>");
    CheckUtil.notBlank(ip, "pc ip");
    return ip;
  }


  /**
   * 如果启用了apache反向代理，需要使用这个方法拿真实ip
   * @param request
   * @return
   */
  public static String getIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }



  /**
   * 将字符串型ip转成int型ip
   * 
   * @param strIp
   * @return
   */
  public static int Ip2Int(String strIp) {
    String[] ss = strIp.split("\\.");
    if (ss.length != 4) {
      return 0;
    }
    byte[] bytes = new byte[ss.length];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) Integer.parseInt(ss[i]);
    }
    return byte2Int(bytes);
  }

  /**
   * 将int型ip转成String型ip
   * 
   * @param intIp
   * @return
   */
  public static String int2Ip(int intIp) {
    byte[] bytes = int2byte(intIp);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      sb.append(bytes[i] & 0xFF);
      if (i < 3) {
        sb.append(".");
      }
    }
    return sb.toString();
  }

  private static byte[] int2byte(int i) {
    byte[] bytes = new byte[4];
    bytes[0] = (byte) (0xff & i);
    bytes[1] = (byte) ((0xff00 & i) >> 8);
    bytes[2] = (byte) ((0xff0000 & i) >> 16);
    bytes[3] = (byte) ((0xff000000 & i) >> 24);
    return bytes;
  }

  private static int byte2Int(byte[] bytes) {
    int n = bytes[0] & 0xFF;
    n |= ((bytes[1] << 8) & 0xFF00);
    n |= ((bytes[2] << 16) & 0xFF0000);
    n |= ((bytes[3] << 24) & 0xFF000000);
    return n;
  }

}
