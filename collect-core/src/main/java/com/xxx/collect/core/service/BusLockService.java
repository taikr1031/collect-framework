package com.xxx.collect.core.service;

import com.xxx.collect.core.model.BusLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.Map;

/**
 * 按业务id锁服务，实现，相同业务id串行，不同业务id并行
 * 使用的时候unBusLock要放到finally中
 * Created by Tony on 2016/5/4.
 */
@Service
@Scope("prototype")
public class BusLockService {

  private static final Log log = LogFactory.getLog(BusLockService.class);

  private Map<String, BusLock> busBusLockMap = new Hashtable<>();

//
//  private String uuid;
//
//  public BusLockService(){
//    uuid = UuidUtil.uuid();
//  }

  private synchronized BusLock findBusLock(String busId) {
    BusLock busLock = busBusLockMap.get(busId);
    if (busLock == null) {
      busLock = new BusLock("busId="+busId);
      busBusLockMap.put(busId, busLock);
    }
    return busLock;
  }

  /**
   * 获取锁，如果已经被锁定则等待完成
   *
   * @param busId
   */
  public void lock(String busId) {
    findBusLock(busId).lock();
  }

  /**
   * 释放锁
   *
   * @param busId
   */
  public void unlock(String busId) {
    findBusLock(busId).unlock();
  }

}
