package com.xxx.collect.core.service;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 基础异步队列服务，用于异步保存日志信息
 */
public abstract class BaseAsyncQueueService<T> {

  /**
   * 处理，消费
   *
   * @param t
   * @return
   */
  public abstract boolean asyncProc(T t);

  /**
   * 队列
   */
  private ConcurrentLinkedQueue<Object> LOG_QUEUE;

  public BaseAsyncQueueService() {
    LOG_QUEUE = new ConcurrentLinkedQueue();
  }

  public synchronized boolean isEmpty() {
    return LOG_QUEUE.isEmpty();
  }

  public synchronized int getSize() {
    return LOG_QUEUE.size();
  }

  @SuppressWarnings("unchecked")
  public synchronized T poll() {
    return (T) LOG_QUEUE.poll();
  }

  public synchronized void offer(T bean) {
    LOG_QUEUE.offer(bean);
  }
}
