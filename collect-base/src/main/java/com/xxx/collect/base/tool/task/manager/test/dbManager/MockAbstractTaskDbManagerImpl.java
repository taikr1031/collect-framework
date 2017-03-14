package com.xxx.collect.base.tool.task.manager.test.dbManager;

import com.xxx.collect.core.util.thread.ThreadUtil;
import com.xxx.collect.base.tool.task.manager.ATaskDbManager;
import com.xxx.collect.base.tool.task.model.Task;
import org.springframework.stereotype.Service;

@Service
public class MockAbstractTaskDbManagerImpl extends ATaskDbManager {

  private static int taskNum = 0;

  public void execTaskDb(Task taskInfo) {
    ThreadUtil.sleep(10);
    System.out.println("ִ������" + taskInfo.getInfoId() + " ������ " + taskNum++);
  }

}
