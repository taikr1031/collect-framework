package com.xxx.collect.base.tool.file.copy;

import com.xxx.collect.base.tool.task.manager.ABaseManager;
import com.xxx.collect.base.tool.task.model.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

/**
 * 基于多线程的文件拷贝服务
 * Created by Administrator on 2016/7/15 0015.
 *
 *
 */
@Service
public class CopyTaskManager extends ABaseManager {

  private static final Log log = LogFactory.getLog(CopyTaskManager.class);
  private SynchronousQueue<Task> queue = new SynchronousQueue<>();

  private int existsFiles = 0;
  private int succFiles = 0;

  public CopyTaskManager() {
    this.setThreadNum(10);
    this.setIfNoTaskStop(false);
    this.setShowEasyLog(true);
  }

  public synchronized void addFile(File src, File target) {
    Task task = new Task();
    task.setInfoA(src.getAbsolutePath());
    task.setInfoId(target.getAbsolutePath());
    queue.add(task);
    if (!this.isStarted())
      this.start();
  }

  @Override
  public synchronized List<Task> genTask() {
    List<Task> tasks = new ArrayList<>();
    while (!queue.isEmpty()) {
      tasks.add(queue.poll());
    }
    return tasks;
  }

  @Override
  public void execTask(Task taskInfo) {
    try {
      File targetFile = new File(taskInfo.getInfoId());
      File srcFile = new File(taskInfo.getInfoA());
      DirFileUtil.copyIfExistIgnore(srcFile, targetFile);
      succFiles++;
      if (succFiles % 10 == 1)
        log.debug(succFiles + " 成功复制:" + srcFile.getAbsolutePath() + "         >>>         " + targetFile.getAbsolutePath());
    } catch (Exception e) {
      log.error(e, e);
    }
  }
}
