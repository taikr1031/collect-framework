package com.xxx.collect.base.tool.task.service;

import com.xxx.collect.core.util.date.DateUtil;
import com.xxx.collect.core.util.string.StringUtil;
import com.gei.pd.base.dbpd.dbmapper.TaskInfoMapper;
import com.gei.pd.base.dbpd.dbmodel.TaskInfo;
import com.gei.pd.base.dbpd.dbmodel.TaskInfoExample;
import com.gei.pd.base.dbpd.dbmodel.TaskInfoKey;
import com.gei.pd.base.dbpd.querymapper.QueryTaskMapper;
import com.xxx.collect.base.tool.task.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

  @Autowired
  private TaskInfoMapper taskInfoMapper;

  @Autowired
  private QueryTaskMapper queryTaskMapper;

  public int count(String taskName, String status) {
    TaskInfoExample exp = new TaskInfoExample();
    TaskInfoExample.Criteria criteria = exp.createCriteria().andTaskNameEqualTo(taskName);
    if (status != null)
      criteria.andStatusEqualTo(status);
    return this.taskInfoMapper.countByExample(exp);
  }

  /**
   * 列出所有
   *
   * @param taskName
   * @return
   */
  public List<Task> listByTaskName(String taskName) {
    return listOrderAsc(taskName, null, null);
  }

  /**
   * 列出所有
   *
   * @param taskName
   * @return
   */
  public List<Task> listByTaskNameAndStatus(String taskName, String taskStatus) {
    return listOrderAsc(taskName, taskStatus, null);
  }

  public List<Task> listOrderAsc(String taskName, String taskStatus, Integer fetchRows) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("taskName", taskName);
    if (!StringUtil.isBlank(taskStatus))
      map.put("taskStatus", taskStatus);
    if (fetchRows == null)
      map.put("fetchRows", 999999999);
    else
      map.put("fetchRows", fetchRows);
    map.put("orderByStartDate", true);
    List<Task> list = this.queryTaskMapper.list(map);
    return list;
  }


  public void insertIfNotExistStatusErro(String taskName, String infoId) {
    insertIfNotExist(taskName, infoId, Task.STATUS_ERROR);
  }

  public void insertIfNotExist(String taskName, String infoId, String status) {
    Task task = new Task(taskName, infoId);
    task.setStatus(status);
    insertIfNotExist(task);
  }

  public void insertIfNotExist(Task task) {
    if (this.getById(task.getTaskName(), task.getInfoId()) == null) {
      this.taskInfoMapper.insert(task);
    }
  }

  public Task getById(String taskName, String infoId) {
    TaskInfoKey key = new TaskInfoKey();
    key.setTaskName(taskName);
    key.setInfoId(infoId);
    TaskInfo taskInfo = this.taskInfoMapper.selectByPrimaryKey(key);
    return taskInfo == null ? null : new Task(taskInfo);
  }

  public void insert(Task task) {
    task.setCreateTime(new Date());
    this.taskInfoMapper.insert(task);
  }

  public void updateSelective(Task task) {
    this.taskInfoMapper.updateByPrimaryKeySelective(task);
  }


  public void updateStatus(String taskName, String id, String status) {
    TaskInfo key = new TaskInfo();
    key.setTaskName(taskName);
    key.setInfoId(id);
    key.setStatus(status);
    this.taskInfoMapper.updateByPrimaryKeySelective(key);
  }

  public void updateStartDateCurrent(Task task) {
    TaskInfo taskInfo = new TaskInfo();
    taskInfo.setTaskName(task.getTaskName());
    taskInfo.setInfoId(task.getInfoId());
    taskInfo.setStartDate(DateUtil.getNowTimeDesc());
    this.taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
  }

  public void save(Task task) {
    TaskInfo info = this.taskInfoMapper.selectByPrimaryKey(task);
    if (info == null)
      this.insert(task);
    else
      update(task);
  }

  public void update(Task task) {
    this.taskInfoMapper.updateByPrimaryKey(task);
  }

  public void deleteByTaskName(String taskName) {
    TaskInfoExample exp = new TaskInfoExample();
    exp.createCriteria().andTaskNameEqualTo(taskName);
    this.taskInfoMapper.deleteByExample(exp);
  }

  public void deleteByKey(TaskInfoKey taskInfoKey) {
    this.taskInfoMapper.deleteByPrimaryKey(taskInfoKey);
  }


  public void deleteByKey(String taskName, String infoId) {
    TaskInfoKey key = new TaskInfoKey();
    key.setTaskName(taskName);
    key.setInfoId(infoId);
    this.taskInfoMapper.deleteByPrimaryKey(key);
  }

}
