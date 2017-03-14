package com.xxx.collect.core.util.httpclient.impl;

import com.xxx.collect.core.util.httpclient.model.HttpClientConfig;
import com.xxx.collect.core.util.httpclient.model.HttpResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/* 
 * @author Astar
 *
 */
public class HttpClientCommon extends AbstractHttpClientBase {

  private static final Log log = LogFactory.getLog(HttpClientCommon.class);

  public HttpEntity getInputStream(String url) throws IOException {
    return getInputStream(url, new HttpClientConfig());
  }

  public void download(File localFile, String url) throws IOException {
    download(localFile, url, new HttpClientConfig());
  }

  public void download(File localFile, String url, HttpClientConfig clientConfig) throws IOException {
    super.download(localFile, url, clientConfig);
  }

  public HttpResult post(String url, Map<String, String> parm, HttpClientConfig config) throws IOException {
    return super.post(url, parm, config);
  }

  public HttpResult post(String url, Map<String, String> parm) throws IOException {
    return super.post(url, parm, new HttpClientConfig());
  }


  public HttpResult upload(String url, InputStream inputStream) throws IOException{
    return super.upload(url,inputStream,new HttpClientConfig());
  }

  public HttpResult upload(String url, InputStream inputStream, HttpClientConfig httpClientConfig) throws IOException{
    return super.upload(url,inputStream,httpClientConfig);
  }

  public HttpResult postStr(String url, String content, HttpClientConfig config) throws IOException {
    return super.postStr(url, content, config);
  }

  public HttpResult postStr(String url, String content) throws IOException {
    return super.postStr(url, content, new HttpClientConfig());
  }


  public HttpResult visit(String url) throws IOException {
    return visit(url, new HttpClientConfig());
  }

  public HttpResponse getHttpResonse(String url) throws IOException {
    return super.getHttpResonse(url, new HttpClientConfig());
  }

  public HttpResult visit(String url, HttpClientConfig httpClientConfig) throws IOException {
    return super.visit(url, httpClientConfig);
  }

  public static void main(String[] args) throws IOException {
    HttpClientConfig config = new HttpClientConfig();
    config.setCookie("bdshare_firstime=1448249933691; JSESSIONID=D316F672196BE72E0DF7EA2C1EF2F5A8; 1li1li1l=\"dIi/d/yqC5HUXXbHwP+EFQ==\"; CNZZDATA5697895=cnzz_eid%3D1829321414-1448249495-%26ntime%3D1449368833; geiweb-v=zZ+S93HA1QeoEmZ2DTiIenHdNSzwzbV9fSARkDu/cjMDdDHeZDNqQihW5eabSonQ");
    HttpClientCommon client = new HttpClientCommon();
    HttpResult rest = client.visit("http://www.2gei.com");
    System.out.println(rest.getHtml());
  }

}
