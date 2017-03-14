package com.xxx.collect.core.exception;

/**
 * 抛出此异常，将会执行301跳转
 */
public class Redirect301Exception extends BaseRuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String url;

  private String message;

  public Redirect301Exception(String message, String url) {
    this.message = message;
    this.url = url;
  }
  
  public String getMessage() {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
