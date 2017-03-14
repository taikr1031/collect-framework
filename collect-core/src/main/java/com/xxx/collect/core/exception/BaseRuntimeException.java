package com.xxx.collect.core.exception;

@SuppressWarnings("serial")
public class BaseRuntimeException extends RuntimeException {

  private ExceptionOperate operate;

  public BaseRuntimeException() {
  }

  public BaseRuntimeException(ExceptionOperate operate) {
    this.operate = operate;
  }

  public ExceptionOperate getOperate() {
    return operate;
  }

  public void setOperate(ExceptionOperate operate) {
    this.operate = operate;
  }
}
