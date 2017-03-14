package com.xxx.collect.base.tool.task.model;

import com.xxx.collect.core.util.BeanUtil;
import com.gei.pd.base.dbpd.dbmodel.TaskInfo;

public class Task extends TaskInfo {
  public static final String STATUS_NEW = "NEW";
  public static final String STATUS_SUCCESS = "SUCC";
  public static final String STATUS_ERROR = "ERRO";

  public Task() {
    this.setErroNum(0l);
    this.setSuccNum(0l);
  }

  public Task(TaskInfo taskInfo) {
    BeanUtil.copyProperties(this, taskInfo);
  }

  public Task(String taskName) {
    this.setTaskName(taskName);
  }

  public Task(String taskName, String infoId) {
    this.setTaskName(taskName);
    this.setInfoId(infoId);
  }

  public Task(String taskName, String infoId, String infoA) {
    this.setTaskName(taskName);
    this.setInfoId(infoId);
    this.setInfoA(infoA);
  }

}
