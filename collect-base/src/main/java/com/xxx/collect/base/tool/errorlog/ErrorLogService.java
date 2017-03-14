package com.xxx.collect.base.tool.errorlog;

import com.xxx.collect.core.util.date.DateUtil;
import com.gei.pd.base.dbpd.dbmapper.ErrorLogMapper;
import com.gei.pd.base.dbpd.dbmodel.ErrorLog;
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
