package com.xxx.collect.base.tool.task.model;

import com.xxx.collect.core.util.httpclient.model.HttpResult;

import java.util.HashMap;
import java.util.Map;

/*
 * url种子，每个加入urllist队列中的，待下载url，都应该是一个url种子
 * 它必须携带url地址，此外还可以携带用于解析时使用的信息
 * 这些信息会放在map里面，通过键值对的形式保存
 */
public class Url {

  private String url;
  private Map<String, Object> map;
  private HttpResult httpResponse;
  private Task task;


  public Url() {

  }

  public Url(String url) {
    this.url = url;
  }

  public String getHtml() {
    return this.httpResponse.getHtml();
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

  public Map<String, Object> getMap() {
    return map;
  }

  public void setMap(Map<String, Object> map) {
    this.map = map;
  }

  public HttpResult getHttpResponse() {
    return httpResponse;
  }

  public void setHttpResponse(HttpResult httpResponse) {
    this.httpResponse = httpResponse;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void put(String key, Object obj) {
    if (map == null) {
      map = new HashMap<String, Object>();
    }
    map.put(key, obj);
  }

  public Object get(String key) {
    return map.get(key);
  }

}
