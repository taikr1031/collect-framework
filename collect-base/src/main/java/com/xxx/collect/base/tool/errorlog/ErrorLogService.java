package com.xxx.collect.base.tool.errorlog;

import com.xxx.collect.base.dao.ErrorLogMapper;
import com.xxx.collect.base.model.ErrorLog;
import com.xxx.collect.core.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErrorLogService {

  @Autowired
  private ErrorLogMapper errorLogMapper;

  public void insertErrorLog(ErrorLog errorLog) {
    this.errorLogMapper.insert(errorLog);
  }

  public void insertErrorLog(String taskName, String infoId, String logDev) {
    ErrorLog errorLog = new ErrorLog();
    errorLog.setTaskName(taskName);
    errorLog.setInfoId(infoId);
    errorLog.setLogDev(logDev);
    errorLog.setCurDate(DateUtil.getNowTimeDescMillisecond());
    this.insertErrorLog(errorLog);
  }
}
