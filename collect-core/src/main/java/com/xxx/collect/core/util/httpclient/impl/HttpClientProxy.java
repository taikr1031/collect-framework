package com.xxx.collect.core.util.httpclient.impl;

import com.xxx.collect.core.util.httpclient.model.HttpClientConfig;
import com.xxx.collect.core.util.httpclient.model.HttpResult;
import com.xxx.collect.core.util.httpclient.proxy.Proxy;
import com.xxx.collect.core.util.httpclient.proxy.ProxyResponseValidate;
import com.xxx.collect.core.util.httpclient.proxy.ProxySelector;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Astar 带cookie，带代理访问
 */
public class HttpClientProxy extends AbstractHttpClientBase {

  @Autowired
  private ProxySelector proxySelector;

  @Override
  public void download(File localFile, String url) throws IOException {
    download(localFile,url,new HttpClientConfig());
  }

  @Override
  public void download(File localFile, String url,HttpClientConfig config) throws IOException {
    Proxy proxy = this.proxySelector.nextProxy();
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
  public HttpResult visit(String url) throws IOException {
    Proxy proxy = this.proxySelector.nextProxy();
    return visit(url, proxy);
  }

  public HttpResult visit(String url, Proxy proxy) throws IOException {
    HttpClientConfig clientConfig = new HttpClientConfig();
    clientConfig.setProxy(proxy);
    HttpResult response = null;
    try {
      response = super.visit(url, clientConfig);
      // 如果校验不通过，也增加错误
      if (response.getStatus() != 200)
        throw new RuntimeException("代理返回信息校验不通过，可能该代理已经被屏蔽!原因:状态不为200,状态=" + response.getStatus());
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
  public HttpResult post(String url, Map<String, String> parm, HttpClientConfig httpClientConfig) throws IOException {
    Proxy proxy = this.proxySelector.nextProxy();
    httpClientConfig.setProxy(proxy);
    HttpResult response = null;
    try {
      response = super.post(url, parm, httpClientConfig);
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
  public HttpResult post(String url, Map<String, String> parm) throws IOException {
    return post(url, parm, new HttpClientConfig());
  }

}
