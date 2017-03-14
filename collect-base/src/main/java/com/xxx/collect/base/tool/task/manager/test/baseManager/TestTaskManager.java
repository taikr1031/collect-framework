package com.xxx.collect.base.tool.task.manager.test.baseManager;

import junit.framework.TestCase;

import com.xxx.collect.core.util.thread.ThreadUtil;

/**
 * 测试基本生产者和消费者 - 任务管理器
 */
public class TestTaskManager extends TestCase {

  public static void main(String[] args) {

    MockAbstractTaskManagerImpl taskManager = new MockAbstractTaskManagerImpl();
    taskManager.setThreadNum(10);
    // taskManager.setSleep(5000);
    //taskManager.setExecNum(15);
    // taskManager.setGroupExecNum(3);
    // taskManager.setGroupSleep(10);
    taskManager.start();
    ThreadUtil.sleep(1000000 * 10);

  }

}
