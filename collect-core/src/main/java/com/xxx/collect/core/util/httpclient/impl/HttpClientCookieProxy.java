package com.xxx.collect.core.util.httpclient.impl;

import com.xxx.collect.core.util.httpclient.model.HttpResult;
import com.xxx.collect.core.util.httpclient.proxy.Proxy;
import com.xxx.collect.core.util.httpclient.proxy.ProxyResponseValidate;
import com.xxx.collect.core.util.httpclient.model.HttpClientConfig;
import com.xxx.collect.core.util.httpclient.proxy.ProxySelector;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Astar 带cookie，带代理访问,
 *         这里的cookie是放在文件夹E:\gei-work\Spider\Cookie中的，
 *         HttpClientCookieProxy已经在xml中配置了
 */
public class HttpClientCookieProxy extends HttpClientCookie {

  @Autowired
  private ProxySelector proxySelector;

  public HttpResult visit(String url) throws IOException {
    Proxy proxy = this.proxySelector.nextProxy();
    return visit(url, proxy, new HttpClientConfig());
  }

  @Override
  public HttpResult visit(String url, HttpClientConfig config) throws IOException {
    Proxy proxy = this.proxySelector.nextProxy();
    return visit(url, proxy, config);
  }

  public HttpResult visit(String url, Proxy proxy, HttpClientConfig config) throws IOException {
    config.setProxy(proxy);
    HttpResult response = null;
    try {
      response = super.visit(url, config);
      if (response.getStatus() != 200)
        throw new RuntimeException("代理返回信息校验不通过，可能该代理已经被屏蔽!原因:状态不为200,状态="+response.getStatus());
      if (!ProxyResponseValidate.validateResponse(response)) {
        throw new RuntimeException("代理返回信息校验不通过，可能该代理已经被屏蔽!");
      }
    } catch (Exception e) {
      proxy.addSeriesError();
      throw e;
    }
    proxy.clearSeriesError();
    return response;
  }

  @Override
  public void download(File localFile, String url) throws Exception {
    Proxy proxy = this.proxySelector.nextProxy();

    HttpClientConfig config = new HttpClientConfig();
    config.setProxy(proxy);
    try {
      super.download(localFile, url, config);
    } catch (Exception e) {
      proxy.addSeriesError();
      throw e;
    }
    proxy.clearSeriesError();
  }

  @Override
  public HttpResult post(String url, Map<String, String> parm) throws Exception {
    Proxy proxy = this.proxySelector.nextProxy();
    HttpClientConfig config = new HttpClientConfig();
    config.setProxy(proxy);
    HttpResult response = null;
    try {
      response = super.post(url, parm, config);
      if (response.getStatus() != 200)
        throw new RuntimeException("代理返回信息校验不通过，可能该代理已经被屏蔽!原因:状态不为200,状态="+response.getStatus());
      if (!ProxyResponseValidate.validateResponse(response)) {
        throw new RuntimeException("代理返回信息校验不通过，可能该代理已经被屏蔽!");
      }
    } catch (Exception e) {
      proxy.addSeriesError();
      throw e;
    }
    proxy.clearSeriesError();
    return response;
  }

}
