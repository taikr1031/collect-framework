package com.xxx.collect.core.util.httpclient.model;

import org.apache.http.client.CookieStore;

import com.xxx.collect.core.util.httpclient.proxy.Proxy;

public class HttpResult {
  private String html;
  private int status;
  private String encoding;
  private byte[] bytes;
  private String url;
  private CookieStore cookieStore;
  private Proxy proxy;
  private String logInfo;

  public String getLogInfo() {
    return logInfo;
  }

  public void setLogInfo(String logInfo) {
    this.logInfo = logInfo;
  }

  public Proxy getProxy() {
    return proxy;
  }

  public void setProxy(Proxy proxy) {
    this.proxy = proxy;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }

  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public String getHtml() {
    return html;
  }

  public void setHtml(String html) {
    this.html = html;
  }

  public int getStatus() {
    return status;
  }

  public CookieStore getCookieStore() {
    return cookieStore;
  }

  public void setCookieStore(CookieStore cookieStore) {
    this.cookieStore = cookieStore;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
