package com.xxx.collect.core.util.httpclient.model;

import com.xxx.collect.core.util.httpclient.proxy.Proxy;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;

public class HttpClientConfig {
  public final static String METHOD_POST = "post";

  private String encoding;
  private String method;
  private Integer timeOut;//定义连接超时时间，单位毫秒
  private Proxy proxy;

  private CookieStore cookieStore;

  private ArrayList<Header> headers ;

  private String cookie;// 字符串形式的cookie
  
  public HttpClientConfig(){
    headers = new ArrayList<Header>();
  }

  public void addHeader(String name,String value){
    this.headers.add(new BasicHeader(name,value));
  }

  public String getCookie() {
    return cookie;
  }

  public void setCookie(String cookie) {
    this.cookie = cookie;
  }

  public ArrayList<Header> getHeaders() {
    return headers;
  }

  public void setHeaders(ArrayList<Header> headers) {
    this.headers = headers;
  }

  public CookieStore getCookieStore() {
    return cookieStore;
  }

  public void setCookieStore(CookieStore cookieStore) {
    this.cookieStore = cookieStore;
  }

  public Proxy getProxy() {
    return proxy;
  }

  public void setProxy(Proxy proxy) {
    this.proxy = proxy;
  }

  public Integer getTimeOut() {
    return timeOut;
  }

  public void setTimeOut(Integer timeOut) {
    this.timeOut = timeOut;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }
}
