package com.xxx.collect.core.task;

/**
 * 用于不停的循环处理task基础统计
 */
public class BaseAsyncCounter {

  private int successCount;
  private int errorCount;

  public void addSuccessCount() {
    this.successCount++;
  }

  public void addErrorCount() {
    this.errorCount++;
  }

  public int getSuccessCount() {
    return successCount;
  }

  public void setSuccessCount(int successCount) {
    this.successCount = successCount;
  }

  public int getErrorCount() {
    return errorCount;
  }

  public void setErrorCount(int errorCount) {
    this.errorCount = errorCount;
  }

}
