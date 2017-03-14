package com.xxx.collect.core.model;

import com.xxx.collect.core.util.date.DateCalcUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 * 业务锁:按业务id锁服务，实现，相同业务id串行，不同业务id并行
 * Created by Tony on 2016/5/4.
 */
public class BusLock {

  private static final Log log = LogFactory.getLog(BusLock.class);

  private boolean isLock;

  private String remark;

  private Date startLockTime;

  private int timeoutSecond = 10;//设置锁等待超时时间,默认10秒

  public BusLock(String remark) {
    this.remark = remark;
  }

  /**
   * 获取锁，如果已经被锁了，则一直待定
   */
  public synchronized void lock() {
    Date startTime = new Date();
    while (isLock) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      if (DateCalcUtil.betweenSecond(startTime, new Date()) > timeoutSecond) {
        log.error("获取锁超时,超过" + timeoutSecond + "秒，强制执行,remark=" + remark);
        break;
      }
    }
    isLock = true;
  }

  public void unlock() {
    isLock = false;
  }

  public int getTimeoutSecond() {
    return timeoutSecond;
  }

  public void setTimeoutSecond(int timeoutSecond) {
    this.timeoutSecond = timeoutSecond;
  }

  public boolean isLock() {
    return isLock;
  }

  public void setLock(boolean lock) {
    isLock = lock;
  }

  public Date getStartLockTime() {
    return startLockTime;
  }

  public void setStartLockTime(Date startLockTime) {
    this.startLockTime = startLockTime;
  }
}
