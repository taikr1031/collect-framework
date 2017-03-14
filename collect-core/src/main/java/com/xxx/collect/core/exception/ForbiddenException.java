package com.xxx.collect.core.exception;

@SuppressWarnings("serial")
public class ForbiddenException extends BaseRuntimeException {
  public ForbiddenException() {
  }

  public ForbiddenException(ExceptionOperate operate) {
    super(operate);
  }

}
