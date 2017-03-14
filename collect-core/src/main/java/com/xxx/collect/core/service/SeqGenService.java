package com.xxx.collect.core.service;

import com.xxx.collect.core.db.querymapper.QuerySysMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 序列服务，用于生产全局唯一的数字id
 */
@Deprecated
@Service
public class SeqGenService {
  @Autowired
  private CacheSimplyService localCacheService;

  @Autowired
  private QuerySysMapper querySysMapper;

  @Value("#{app['sys.nextSeqRandomSetpSize']}")
  private int nextSeqRandomSize;

  /**
   * 表主键序列cache是否初始化
   */
  private static Map<String, Boolean> TABLE_HAS_INIT = new HashMap<String, Boolean>();

  synchronized public int getNextSeq(String tableName) {
    Boolean hasInit = TABLE_HAS_INIT.get(tableName);
    String tableSeqCacheId = "table_seq_id:" + tableName;
    // 先判断是否已经初始化，如果没有则查数据库max(id)进行初始化
    if (hasInit == null) {
      Object curSeq = localCacheService.get(tableName);
      if (curSeq == null) {// 进行cache的初始化
        Integer currentSeq = queryCurrentSeqByDb(tableName);
        localCacheService.set(tableSeqCacheId, currentSeq);
      }
      TABLE_HAS_INIT.put(tableName, true);
    }
    Random random = new Random();
    // 同步块，防止主键重复
    long nextSeq = localCacheService.incr(tableSeqCacheId, random.nextInt(nextSeqRandomSize) + 1);
    return Long.valueOf(nextSeq).intValue();
  }

  /**
   * 启动时通过数据库初始化所有的表数据
   */
  public int queryCurrentSeqByDb(String tableName) {
    Integer maxId = querySysMapper.queryTableMaxId(tableName);
    return maxId == null ? 0 : maxId;
  }

}
