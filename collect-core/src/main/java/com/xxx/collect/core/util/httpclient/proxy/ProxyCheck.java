package com.xxx.collect.core.util.httpclient.proxy;

import java.util.Date;

import com.xxx.collect.core.util.date.DateCalcUtil;
import com.xxx.collect.core.util.httpclient.impl.HttpClientCommon;
import com.xxx.collect.core.util.httpclient.model.HttpClientConfig;
import com.xxx.collect.core.util.httpclient.model.HttpResult;

public class ProxyCheck {

  public static void main(String[] args) {
    ProxyCheck check = new ProxyCheck();
    check.vailidate(new Proxy("121.13.236.100", 8080));
  }

  public static final String validateUrl = "http://www.baidu.com";
  public static final String validateChar = "百度";

  public boolean vailidate(Proxy proxy) {
    return vailidate(proxy, 1);
  }

  public boolean vailidate(Proxy proxy, int validateNum) {
    Date startDate = new Date();
    HttpClientCommon client = new HttpClientCommon();
    HttpClientConfig httpClientConfig = new HttpClientConfig();
    httpClientConfig.setProxy(proxy);
    try {
      while (validateNum > 0) {
        validateNum--;
        HttpResult resp = client.visit(ProxyCheck.validateUrl,httpClientConfig);
        long second = DateCalcUtil.betweenSecond(startDate, new Date());
        if (resp.getHtml().contains(ProxyCheck.validateChar)) {
          System.out.println("成功：" + (second + "秒") + " " + proxy.toString());
        } else {
          throw new RuntimeException("不包括：" + (second + "") + " " + proxy.toString());
        }
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      long second = DateCalcUtil.betweenSecond(startDate, new Date());
      System.out.println("失败：" + (second + "秒") + e.getMessage() + " - " + proxy.toString());
      return false;
    }
  }

}