package com.xxx.collect.base.tool.util;

import com.xxx.collect.core.util.httpclient.proxy.Proxy;

/**
 * Created by Tony on 2016/12/1.
 */
public class ProxyPool {

  private static String AbuProxyHost = "proxy.abuyun.com";
  private static int AbuProxyProt = 9010;

  public static Proxy AbuProxy = new Proxy(AbuProxyHost, AbuProxyProt, "H4ZO9E7F73J36WGD", "F53DC10758E59107");

  public static Proxy KuaiProxy = new Proxy("139.199.69.111", 16816, "lujuju", "4n7yw2nk");


//  public static Proxy proxy2 = new Proxy(proxyHost, proxyProt, "H4ZO9E7F73J36WGD", "F53DC10758E59107");

}
