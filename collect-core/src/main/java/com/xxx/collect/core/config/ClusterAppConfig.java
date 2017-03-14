package com.xxx.collect.core.config;

/**
 * 每一个应用自己的配置
 * Created by Tony on 2016/8/25.
 */
public class ClusterAppConfig {

  //为了避免所有服务器同时执行定时任务对数据库造成瓶颈，每台服务器设置一个延时执行
  private int taskDelay;//单位：秒

  //是否由此app来执行计划任务，全部集群只能有一个来执行，并且也保证它常活
  private boolean isScheduledApp;

  public boolean isScheduledApp() {
    return isScheduledApp;
  }

  public void setScheduledApp(boolean scheduledApp) {
    isScheduledApp = scheduledApp;
  }

  public int getTaskDelay() {
    return taskDelay;
  }

  public void setTaskDelay(int taskDelay) {
    this.taskDelay = taskDelay;
  }
}
