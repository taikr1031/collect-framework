package com.xxx.collect.core.util.httpclient.impl;

import com.xxx.collect.core.util.UrlParser;
import com.xxx.collect.core.util.httpclient.ICookieStore;
import com.xxx.collect.core.util.httpclient.model.HttpClientConfig;
import com.xxx.collect.core.util.httpclient.model.HttpResult;
import com.xxx.collect.core.util.string.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class HttpClientCookie extends AbstractHttpClientBase {

  private ICookieStore cookieStore;

  public void setCookieStore(ICookieStore cookieStore) {
    this.cookieStore = cookieStore;
  }

  public HttpClientCookie() {
    this.cookieStore = new CookieStoreFile();
  }

  @Override
  public HttpResult visit(String url) throws Exception{
    return visit(url, null);
  }

  @Override
  public HttpResult visit(String url, HttpClientConfig config) throws IOException {
    if (config == null)
      config = new HttpClientConfig();
    String site = UrlParser.getHostName(url);
    if(config.getCookie()==null) {
      String cooike = cookieStore.nextCookie(site);
      if (!StringUtil.isBlank(cooike)) {
        config.setCookie(cooike);
      }
    }
    return super.visit(url, config);
  }

  @Override
  public HttpResult post(String url, Map<String, String> parm) throws Exception {
    return post(url, parm, null);
  }

  @Override
  public HttpResult post(String url, Map<String, String> parm, HttpClientConfig config)
      throws IOException {
    if (config == null)
      config = new HttpClientConfig();
    String site = UrlParser.getHostName(url);
    String cooike = cookieStore.nextCookie(site);
    if (!StringUtil.isBlank(cooike)) {
      config.setCookie(cooike);
    }
    return super.post(url, parm, config);
  }

  @Override
  public void download(File localFile, String url) throws Exception {
    download(localFile, url, null);
  }

  @Override
  public void download(File localFile, String url, HttpClientConfig config) throws IOException {
    if (config == null)
      config = new HttpClientConfig();
    String site = UrlParser.getHostName(url);
    String cooike = cookieStore.nextCookie(site);
    if (!StringUtil.isBlank(cooike)) {
      config.setCookie(cooike);
    }
    super.download(localFile, url, config);
  }

}
