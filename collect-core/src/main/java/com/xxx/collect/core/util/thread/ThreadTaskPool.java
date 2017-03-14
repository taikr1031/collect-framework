package com.xxx.collect.core.util.thread;

import com.xxx.collect.core.util.ListUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 多线程任务池实现
 * Created by Tony on 2016/9/4.
 */
public class ThreadTaskPool<T> {

  private static final Log log = LogFactory.getLog(ThreadTaskPool.class);

  private List<T> list;
  private int threadNum = 10;
  private Consumer<T> consumer;
  private boolean isBlock = true;//是否阻塞式执行

  /**
   * 阻塞式的任务实现
   */
  public void start() {
    List<List<T>> groups = ListUtil.splitByGroupCount(list, threadNum);
    List<Thread> threads = new ArrayList<>();
    for (List<T> groupList : groups) {
      ThreadTask threadTask = new ThreadTask(groupList, consumer);
      threads.add(threadTask);
      threadTask.start();
      ThreadUtil.sleepSeccond(3);
    }
    if (this.isBlock)
      for (Thread thread : threads) {
        try {
          thread.join();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
  }

  public boolean isBlock() {
    return isBlock;
  }

  public void setBlock(boolean block) {
    isBlock = block;
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public int getThreadNum() {
    return threadNum;
  }

  public void setThreadNum(int threadNum) {
    this.threadNum = threadNum;
  }

  public Consumer<T> getConsumer() {
    return consumer;
  }

  public void setConsumer(Consumer<T> consumer) {
    this.consumer = consumer;
  }

  class ThreadTask extends Thread {
    private List<T> tasks;
    private Consumer<T> consumer;

    ThreadTask(List<T> tasks, Consumer<T> consumer) {
      this.tasks = tasks;
      this.consumer = consumer;
    }

    @Override
    public void run() {
      for (int i = tasks.size() - 1; i >= 0; i--) {
        T t = tasks.get(i);
        try {
          consumer.accept(t);
        } catch (Exception e) {
          log.error(e, e);
        }
        tasks.remove(i);
      }
    }
  }

}
