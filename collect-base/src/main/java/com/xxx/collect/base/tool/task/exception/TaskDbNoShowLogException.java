package com.xxx.collect.base.tool.task.exception;

/**
 * 希望出现此异常时，不显示日志则用此异常，可以让task状态为erro，但是不显示日志
 */
public class TaskDbNoShowLogException extends RuntimeException{

  public TaskDbNoShowLogException(String msg){
    super(msg);
  }
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

}
