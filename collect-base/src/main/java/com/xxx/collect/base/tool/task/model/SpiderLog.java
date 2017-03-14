package com.xxx.collect.base.tool.task.model;

public class SpiderLog {
  private int threadNum = 1;
  private int taskListSize = 0; // task队列内url数量

  private int taskNum = 0;// 总任务数
  private int succTaskNum = 0;// 成功任务数

  private int urlNum = 0;// 发起的url请求数量
  private int succUrlNum = 0;// 正确访问url数量

  private int pageNum = 0;// 下载页面数量
  private int succPageNum = 0;// 正确页面数量

  private int processInfoNum = 0;// 处理信息数量
  
  private String lastHttpLogInfo ;
  private String taskStatus ;

  public String getTaskStatus() {
    return taskStatus;
  }

  public void setTaskStatus(String taskStatus) {
    this.taskStatus = taskStatus;
  }

  public String getLastHttpLogInfo() {
    return lastHttpLogInfo;
  }

  public void setLastHttpLogInfo(String lastHttpLogInfo) {
    this.lastHttpLogInfo = lastHttpLogInfo;
  }

  public int getThreadNum() {
    return threadNum;
  }

  public void setThreadNum(int threadNum) {
    this.threadNum = threadNum;
  }

  public int getTaskListSize() {
    return taskListSize;
  }

  public int getSuccPageNum() {
    return succPageNum;
  }

  public void setSuccPageNum(int succPageNum) {
    this.succPageNum = succPageNum;
  }

  public void setTaskListSize(int taskListSize) {
    this.taskListSize = taskListSize;
  }

  public int getTaskNum() {
    return taskNum;
  }

  public void setTaskNum(int taskNum) {
    this.taskNum = taskNum;
  }

  public int getSuccTaskNum() {
    return succTaskNum;
  }

  public void setSuccTaskNum(int succTaskNum) {
    this.succTaskNum = succTaskNum;
  }

  public int getUrlNum() {
    return urlNum;
  }

  public void setUrlNum(int urlNum) {
    this.urlNum = urlNum;
  }

  public int getSuccUrlNum() {
    return succUrlNum;
  }

  public void setSuccUrlNum(int succUrlNum) {
    this.succUrlNum = succUrlNum;
  }

  public int getPageNum() {
    return pageNum;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

  public int getProcessInfoNum() {
    return processInfoNum;
  }

  public void setProcessInfoNum(int processInfoNum) {
    this.processInfoNum = processInfoNum;
  }

  public void addTaskNum() {
    this.taskNum++;
  }

  public void addSuccTaskNum() {
    this.succTaskNum++;
  }

  public void addProcessInfoNum(int num) {
    processInfoNum += num;
  }

  public void addUrlNum() {
    urlNum++;
  }

  public void addSuccUrlNum() {
    this.succUrlNum++;
  }

  public void addSuccPageNum() {
    succPageNum++;
  }

  public void addPageNum() {
    pageNum++;
  }

}
