package com.xxx.collect.core.util.httpclient.impl;

import com.xxx.collect.core.util.httpclient.model.HttpResult;
import com.xxx.collect.core.util.httpclient.model.HttpClientConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.Map;

/**
 * @author Astar 发生http异常后连续访问
 */
public class HttpClientRetry extends HttpClientCommon {

  private Log log = LogFactory.getLog(HttpClientRetry.class);
  private int retryNum = 10; // 默认10次

  public int getRetryNum() {
    return retryNum;
  }

  public void setRetryNum(int retryNum) {
    this.retryNum = retryNum;
  }

  public HttpClientRetry() {
  }

  /**
   * 默认重复访问10次
   */
  public static HttpClientRetry getDefaultHttpClient() {
    return getHttpClient(10);
  }

  public static HttpClientRetry getHttpClient(int retryNum) {
    HttpClientRetry client = new HttpClientRetry();
    client.setRetryNum(retryNum);
    return client;
  }

  @Override
  public void download(File localFile, String url) {
    download(localFile, url, null);
  }

  @Override
  public void download(File localFile, String url, HttpClientConfig httpClientConfig) {
    int temp = 0;
    if (httpClientConfig == null) httpClientConfig = new HttpClientConfig();
    while (temp < retryNum) {
      temp++;
      try {
        super.download(localFile, url, httpClientConfig);
        return;
      } catch (Exception e) {
        log.debug("url - " + e.toString());
        log.debug("exception download url : " + url + " - localFile:" + localFile.getAbsolutePath());
        continue;
      }
    }
    throw new RuntimeException("HttpClientRetry访问了" + this.retryNum + "次后，依然失败！ url :" + url + " - localFile:" + localFile.getAbsolutePath());
  }

  @Override
  public HttpResult visit(String url) {
    return this.visit(url, null);
  }

  @Override
  public HttpResult visit(String url, HttpClientConfig config) {
    if (config == null)
      config = new HttpClientConfig();
    int temp = 0;
    while (temp < retryNum) {
      temp++;
      try {
        HttpResult response = super.visit(url, config);
        return response;
      } catch (Exception e) {
        log.debug("url - " + e.toString());
        log.debug("exception download url : " + url);
        continue;
      }
    }
    throw new RuntimeException("HttpClientRetry访问了" + this.retryNum + "次后，依然失败！ url :" + url);
  }

  @Override
  public HttpResult post(String url, Map<String, String> parm) {
    int temp = 0;
    while (temp < retryNum) {
      temp++;
      try {
        HttpResult response = super.post(url, parm);
        return response;
      } catch (Exception e) {
        log.debug("url - " + e.toString());
        log.debug("exception post url : " + url);
        continue;
      }
    }
    throw new RuntimeException("HttpClientRetry访问了" + this.retryNum + "次后，依然失败！ url :" + url);
  }
}
