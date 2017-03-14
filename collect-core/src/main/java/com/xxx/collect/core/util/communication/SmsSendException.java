package com.xxx.collect.core.util.communication;

import com.xxx.collect.core.exception.BaseException;

/**
 * Created by luju on 2016/3/14.
 */
public class SmsSendException extends BaseException {
  public SmsSendException(String message) {
    this.message = message;
  }

  private String message;

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
