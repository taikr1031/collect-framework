package com.xxx.collect.core.task;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class BaseTask<T> {

  /**
   * 两次执行任务直接最少的间隔,子类可以改变它
   */
  protected int interval = 100;

  /**
   * 处理任务
   * 
   * @param obj
   */
  public abstract void procTask(T obj);

  protected void baseTask() {
    while (!this.isEmpty()) {
      T obj = this.poll();
      this.procTask(obj);
      try {
        Thread.sleep(interval);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private ConcurrentLinkedQueue<T> queue;

  ConcurrentLinkedQueue<T> getQueue() {
    if (queue == null)
      queue = new ConcurrentLinkedQueue<T>();
    return queue;
  }

  public void add(T obj) {
    this.getQueue().add(obj);
  }

  public T poll() {
    return this.getQueue().poll();
  }

  public boolean isEmpty() {
    return this.getQueue().isEmpty();
  }

  public int size() {
    return this.getQueue().size();
  }

}
