package com.xxx.collect.base.tool.task.manager.test.baseManager;

import com.xxx.collect.core.util.BeanUtil;
import com.xxx.collect.core.util.thread.ThreadUtil;
import com.xxx.collect.base.tool.task.manager.ABaseManager;
import com.xxx.collect.base.tool.task.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MockAbstractTaskManagerImpl extends ABaseManager {

  private int taskIndex = 0;
  private int genTaskNum = 15;
  //private int genTaskMax = 99999999;

  public List<Task> genTask() {
    List<Task> taskList = new ArrayList<Task>();
    for (int i = 0; i < genTaskNum; i++) {
      // if (taskIndex >= genTaskMax) {// ������������
      // System.out.println("������������" + genTaskMax);
      // return taskList;
      // }
      this.taskIndex++;
      //ThreadUtil.sleep(100);
      taskList.add(new Task("MockIGen", "" + this.taskIndex));
    }
    System.out.println("��������" + taskList.size());
    ThreadUtil.sleep(50);
    return taskList;
  }

  @Override
  public void execTask(Task taskInfo) {
    ThreadUtil.sleep(10);
    System.out.println("ִ�У�" + BeanUtil.toString(taskInfo));
  }

}
