package com.xxx.collect.core.util.httpclient.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.xxx.collect.core.util.RegexUtil;
import com.xxx.collect.core.util.httpclient.model.HttpResult;
import com.xxx.collect.core.util.httpclient.model.HttpClientConfig;
import com.xxx.collect.core.util.httpclient.proxy.Proxy;
import com.xxx.collect.core.util.httpclient.proxy.ProxyResponseValidate;
import com.xxx.collect.core.util.string.StringUtil;

/**
 * @author Astar 在xml指定带cookie，带代理访问
 */
public class HttpClientCookieProxyDefine extends AbstractHttpClientBase {

  private String proxy;


  private Proxy proxyObj;

  private String cookie;

  private HttpClientConfig getConfig() {
    if (proxyObj == null && !StringUtil.isBlank(proxy)) {
      List<String> list = RegexUtil.searchBetweenList(proxy, "(\\d+.\\d+.\\d+.\\d+):(\\d+)");
      proxyObj = new Proxy(list.get(0), Integer.valueOf(list.get(1)));
    }
    HttpClientConfig config = new HttpClientConfig();
    config.setProxy(proxyObj);
    config.setCookie(cookie);
    return config;
  }

  @Override
  public HttpResult visit(String url) throws Exception {
    return visit(url, getConfig());
  }

  @Override
  public void download(File localFile, String url) throws Exception {
    try {
      super.download(localFile, url, getConfig());
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public HttpResult post(String url, Map<String, String> parm) throws Exception {
    HttpResult response = null;
    try {
      response = super.post(url, parm, getConfig());
      if (!ProxyResponseValidate.validateResponse(response)) {
        throw new RuntimeException("代理返回信息校验不通过，可能该代理已经被屏蔽!");
      }
    } catch (Exception e) {
      throw e;
    }
    return response;
  }

  public String getProxy() {
    return proxy;
  }

  public void setProxy(String proxy) {
    this.proxy = proxy;
  }

  public String getCookie() {
    return cookie;
  }

  public void setCookie(String cookie) {
    this.cookie = cookie;
  }
}
