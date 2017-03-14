package com.xxx.collect.base.tool.task.manager;

import com.xxx.collect.core.util.BeanUtil;
import com.xxx.collect.core.util.date.DateUtil;
import com.xxx.collect.core.util.log.LogCatalog;
import com.xxx.collect.core.util.log.LogUtil;
import com.xxx.collect.base.tool.errorlog.ErrorLogService;
import com.xxx.collect.base.tool.task.exception.TaskDbNoShowLogException;
import com.xxx.collect.base.tool.task.exception.TaskTerminateException;
import com.xxx.collect.base.tool.task.model.Task;
import com.xxx.collect.base.tool.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 实现了基于数据库保存Task状态
 */
@Service
public abstract class ATaskDbManager extends com.xxx.collect.base.tool.task.manager.ABaseManager {

  protected abstract void execTaskDb(Task task) throws Exception;

  @Autowired
  private TaskService taskService;

  @Autowired
  private ErrorLogService errorLogService;

  /**
   * 只执行一次 实现原理：开始执行前，取最大批次号，状态为NEW的任务。 如果有，则执行。当取不到任务时，结束。
   */
  public static final String TAKS_GEN_TYPE_ONE = "TAKS_GEN_TYPE_ONE";

  @Override
  public void start() {
    super.start();
  }

  private String taskName;

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  /**
   * 一次从数据库中加载的任务数量
   */
  private int fetchTaskRows = 5000;

  private boolean isSaveError = false;

  /**
   * 是否随机打乱任务顺序
   */
  private boolean isRandFetchTask = false;

  public boolean isRandFetchTask() {
    return isRandFetchTask;
  }

  public void setRandFetchTask(boolean randFetchTask) {
    isRandFetchTask = randFetchTask;
  }

  public boolean isSaveError() {
    return isSaveError;
  }

  public void setSaveError(boolean isSaveError) {
    this.isSaveError = isSaveError;
  }

  /**
   * @param fetchTaskRows 每次此数据库获取多少行任务
   */
  public void setFetchTaskRows(int fetchTaskRows) {
    this.fetchTaskRows = fetchTaskRows;
  }

  private String taskStatus;

  public String getTaskStatus() {
    return taskStatus;
  }

  public void setTaskStatus(String taskStatus) {
    this.taskStatus = taskStatus;
  }

  /**
   * 是否把成功后的任务删除掉
   */
  private boolean isDeleteSuccessTask = false;

  public boolean isDeleteSuccessTask() {
    return isDeleteSuccessTask;
  }

  public void setDeleteSuccessTask(boolean isDeleteSuccessTask) {
    this.isDeleteSuccessTask = isDeleteSuccessTask;
  }

  @Override
  public List<Task> genTask() {
    LogCatalog.dbTaskManager.info("通过DB获取任务!开始！" + fetchTaskRows + "个");
    List<Task> list = this.taskService.listOrderAsc(taskName, taskStatus, fetchTaskRows);
    Collections.shuffle(list);
    LogCatalog.dbTaskManager.info("通过DB获取任务!完成！获得任务数量:" + list.size());
    return list;
  }

  @Override
  public void execTask(Task task) {
    // 设置开始执行
    this.taskService.updateStartDateCurrent(task);
    try {
      LogCatalog.dbTaskManager.debug("开始执行任务：" + BeanUtil.toString(task));
      execTaskDb(task);
    } catch (TaskTerminateException et) {
      LogCatalog.app.fatal("程序运行中抛出不可恢复异常！程序将终止运行！");
      LogCatalog.app.fatal(et, et);
      this.stop();
      return;
    } catch (Exception e) {// 所有异常设置为状态ERROR，保留日志进数据库中
      LogCatalog.dbTaskManager.debug("异常执行任务：" + BeanUtil.toString(task));
      if (e instanceof TaskDbNoShowLogException == false) {
        LogCatalog.app.error("任务执行异常：" + BeanUtil.toString(task));
        LogCatalog.app.error(e, e);
      }
      task.setEndDate(DateUtil.getNowTimeDesc());
      task.setStatus(Task.STATUS_ERROR);
      long erroNum = task.getErroNum() == null ? 0 : task.getErroNum();
      erroNum++;
      task.setErroNum(erroNum);
      this.taskService.update(task);
      // 插入ERROR日志
      if (isSaveError) {
        LogCatalog.app.fatal("以下异常已存入ERROR_LOG表");
        errorLogService.insertErrorLog(task.getTaskName(), task.getInfoId(),
            LogUtil.exceptionToString(e));
      }
      return;
    }
    LogCatalog.dbTaskManager.debug("完成执行任务：" + BeanUtil.toString(task));
    // 更新成功执行后的任务状态
    if (!isDeleteSuccessTask) {
      task.setEndDate(DateUtil.getNowTimeDesc());
      task.setStatus(Task.STATUS_SUCCESS);
      long succNum = task.getSuccNum() == null ? 0 : task.getSuccNum();
      succNum++;
      task.setSuccNum(succNum);
      this.taskService.update(task);
    }
    // 删除成功执行的任务
    else {
      this.taskService.deleteByKey(task.getTaskName(), task.getInfoId());
    }

  }
}
