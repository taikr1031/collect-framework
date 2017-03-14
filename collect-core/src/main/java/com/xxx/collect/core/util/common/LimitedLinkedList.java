package com.xxx.collect.core.util.common;

import java.util.LinkedList;

/**
 * 限制长度的LinkedList，超长后添加会自动弹出
 */
public class LimitedLinkedList extends LinkedList<Double> {
  private int maxSize = Integer.MAX_VALUE;
  private final Object lock = new Object();

  public LimitedLinkedList(int maxSize) {
    super();
    this.maxSize = maxSize;
  }

  public double[] getLimits(int limit) {
    int size = size();
    int max = limit >= size ? size : limit;
    double[] datas = new double[max];
    for (int i = 0; i < max; i++) {
      datas[max - i - 1] = get(size - i - 1);
    }
    return datas;
  }

  public void addLastSafe(Double addLast) {
    synchronized (lock) {
      while (size() >= maxSize) {
        poll();
      }
      addLast(addLast);
    }
  }

  public Double pollSafe() {
    synchronized (lock) {
      return poll();
    }
  }

  public void setMaxSize(int maxSize) {
    if (maxSize < this.maxSize) {
      synchronized (lock) {
        while (size() > maxSize) {
          poll();
        }
      }
    }
    this.maxSize = maxSize;
  }

  public int getMaxSize() {
    return this.maxSize;
  }
}
