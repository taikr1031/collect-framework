package com.xxx.collect.base.dao;

import com.xxx.collect.base.tool.task.model.Task;

import java.util.List;
import java.util.Map;

public interface QueryTaskMapper {

  List<Task> list(Map<String, Object> map);

}