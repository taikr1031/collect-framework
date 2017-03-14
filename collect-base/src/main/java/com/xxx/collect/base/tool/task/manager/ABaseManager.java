package com.xxx.collect.base.tool.task.manager;

import com.xxx.collect.core.util.log.LogCatalog;
import com.xxx.collect.core.util.thread.ThreadUtil;
import com.xxx.collect.base.tool.task.model.Task;
import com.xxx.collect.base.tool.task.model.TaskList;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Astar 实现基础的生产者和消费者队列
 */
public abstract class ABaseManager {

  public static Logger log = LogCatalog.baseTaskManager;

  public abstract List<Task> genTask();

  public abstract void execTask(Task taskInfo);

  /**
   * 设置执行任务线程数
   *
   * @param threadNum
   */
  public void setThreadNum(int threadNum) {
    this.threadNum = threadNum;
  }

  private int threadNum = 1;// 默认为1

  public int getThreadNum() {
    return this.threadNum;
  }

  private int sleep = 0;

  /**
   * 设置每次任务之间间隔,单位，秒
   */
  public void setSleep(int sleep) {
    this.sleep = sleep;
  }

  private int execNum = 0;

  /**
   * 设置执行次数，每个线程执行这么多次以后就终止
   */
  public void setExecNum(int execNum) {
    this.execNum = execNum;
  }

  private int groupExecNum = 0;
  private int groupSleep = 0;

  /**
   * 设置每一组执行多少次
   */
  public void setGroupExecNum(int groupExecNum) {
    this.groupExecNum = groupExecNum;
  }

  /**
   * 设置组之间的间隔,以秒为单位
   */
  public void setGroupSleep(int groupSleep) {
    this.groupSleep = groupSleep;
  }

  private boolean isStop = false;

  private boolean isStarted = false;

  private boolean isShowEasyLog = false;//是否显示简单日志

  private boolean ifNoTaskStop = true;//如果没有任务了就退出

  public boolean isShowEasyLog() {
    return isShowEasyLog;
  }

  public void setShowEasyLog(boolean showEasyLog) {
    isShowEasyLog = showEasyLog;
  }

  public boolean isIfNoTaskStop() {
    return ifNoTaskStop;
  }

  public void setIfNoTaskStop(boolean ifNoTaskStop) {
    this.ifNoTaskStop = ifNoTaskStop;
  }

  public boolean isStarted() {
    return isStarted;
  }

  public void setStarted(boolean started) {
    isStarted = started;
  }

  public void stop() {
    this.isStop = true;
  }

  private int delayStart = 1;// 每个线程之间启动间隔时间(默认1秒)

  public void setDelayStart(int delayStart) {
    this.delayStart = delayStart;
  }

  private TaskList taskList = new TaskList();
  private List<ExecTaskThread> execTaskThreadList;

  public TaskList getTaskList() {
    return taskList;
  }

  public void start() {
    taskList.addAll(genTask());
    GenTaskThread genTaskThread = new GenTaskThread();
    genTaskThread.start(); //TODO ZM 如果 taskList.size()=0，还是否需要继续执行
    this.execTaskThreadList = new ArrayList<>();
    for (int i = 0; i < this.threadNum; i++) {
      execTaskThreadList.add(new ExecTaskThread());
    }
    for (ExecTaskThread execTaskThread : execTaskThreadList) {
      execTaskThread.start();
      this.isStarted = true;
      ThreadUtil.sleep(delayStart * 1000);
    }
  }

  class GenTaskThread extends Thread {
    @Override
    public void run() {
      while (!isStop) {
        if (isShowEasyLog) {
          ABaseManager.log.info("当前任务队列:" + taskList.size());
        }
        if (taskList.isEmpty()) {
          List<Task> genTaskList = genTask();
          if ((genTaskList == null || genTaskList.size() == 0) && ifNoTaskStop) {
            ABaseManager.log.info("任务获取队列长度为0!" + " 任务无法继续结束!");
            isStop = true;
          } else {
            ABaseManager.log.info("获取新的任务队列：" + genTaskList.size());
            taskList.addAll(genTaskList);
          }
          ThreadUtil.sleepSeccond(10);
        } else {
          try {
            Thread.sleep(10000);
            // 判断线程状态，如果任务执行者全部退出，则生产者退出
            boolean allTerminated = true;
            for (ExecTaskThread execTaskThread : execTaskThreadList) {
              if (!State.TERMINATED.equals(execTaskThread.getState())) {
                allTerminated = false;
                break;
              }
              ABaseManager.log.warn("任务执行者线程:" + execTaskThread.getName()
                  + " 状态为TERMINATED , Thread.State=" + State.TERMINATED);
            }
            if (allTerminated && ifNoTaskStop) {
              ABaseManager.log.info("任务执行者全部退出，生产者退出!" + " - " + " 任务结束!");
              isStop = true;
            }
          } catch (InterruptedException e) {
            log.error(e, e);
          }
          continue;
        }
      }
      ABaseManager.log.info("任务生产者线程退出!");
    }
  }

  class ExecTaskThread extends Thread {

    private int curExecNum = 0;

    private int curGroupExecNum = 0;

    @Override
    public void run() {
      while (!isStop) {
        Task taskInfo = taskList.poll();
        if (taskInfo != null) {
          execTask(taskInfo);
          this.curExecNum++;
          this.curGroupExecNum++;
          if (execNum > 0 && curExecNum >= execNum) {
            break;
          }
          if (groupExecNum > 0 && groupSleep > 0 && this.curGroupExecNum >= groupExecNum) {
            ThreadUtil.sleep(groupSleep * 1000);
            this.curGroupExecNum = 0;
          }
          if (sleep > 0) {
            ThreadUtil.sleep(sleep * 1000);
          }
        } else {
          try {
            Thread.yield();
            Thread.sleep(1000 * 10);
          } catch (InterruptedException e) {
            log.error(e, e);
          }
          continue;
        }
      }
      ABaseManager.log.info("任务执行者线程:" + Thread.currentThread().getName() + " - 退出！");
    }
  }
}
