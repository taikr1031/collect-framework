package com.xxx.collect.base.tool.task.manager.test.dbManager;

import com.xxx.collect.core.tool.SpringBeanUtil;

/**
 * 测试数据库管理任务状态 - 任务管理器
 */
public class TaskTaskDbManger {
  public static void main(String[] args) {
    MockAbstractTaskDbManagerImpl taskManager = ((MockAbstractTaskDbManagerImpl) SpringBeanUtil
        .getBeanFactoryWithLocalSprider().getBean("mockAbstractTaskDbManagerImpl"));

    String taskName = "TEST_TASK";// 手工在数据库中添加
    taskManager.setTaskName(taskName);
    taskManager.setFetchTaskRows(100);
    taskManager.setThreadNum(30);
    taskManager.start();
    // ThreadUtil.sleep(1000 * 100);
  }
}
