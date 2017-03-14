package com.xxx.collect.base.tool.task.exception;

/**
 * 打断正在执行的任务，比如被网站屏蔽时
 */
public class TaskTerminateException extends RuntimeException {
	/**
   * 
   */
  private static final long serialVersionUID = 1L;

  public TaskTerminateException(String msg){
		super(msg);
	}
}
