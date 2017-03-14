package com.xxx.collect.core.service;

import com.xxx.collect.core.db.dbmapper.TaskLockMapper;
import com.xxx.collect.core.db.dbmodel.TaskLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * Created by Tony on 2016/8/25.
 */
@Service
public class TaskLockService {

  @Autowired
  private TaskLockMapper taskLockMapper;

  /**
   * 用户集群环境下应用获取唯一锁来执行任务
   */
  public boolean tryLock(String uniqueKey) {
    TaskLock taskLock = new TaskLock();
    taskLock.setUniqueKey(uniqueKey);
    try {
      this.taskLockMapper.insert(taskLock);
    } catch (DuplicateKeyException e) {
      return false;
    }
    return true;
  }

}
