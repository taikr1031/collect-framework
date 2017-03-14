package com.xxx.collect.core.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Function;

@Service
public class CacheService {
  private static final Log log = LogFactory.getLog(CacheService.class);
  @Resource(name = "cacheManager")
  private EhCacheCacheManager ehCacheCacheManager;

  /**
   * 定义那些需要持久化到磁盘的缓存,这样服务器重启后依然可以继续使用
   */
  private String[] diskStoreCache = {"cat", "getResc", "CatGroupQuery", "RescGroupQuery"};

  public EhCacheCacheManager getEhCacheCacheManager() {
    return ehCacheCacheManager;
  }

  public void setEhCacheCacheManager(EhCacheCacheManager ehCacheCacheManager) {
    this.ehCacheCacheManager = ehCacheCacheManager;
  }


  public void clearAll() {
    ehCacheCacheManager.getCacheManager().clearAll();
    return;
  }

  /**
   * 按缓存name+key的前缀来清除缓存
   *
   * @param cacheName
   */
  public void clearByKeyPrefix(String cacheName, Function<String,Boolean> isRemove) {
    Cache cache = ehCacheCacheManager.getCacheManager().getCache(cacheName);
    if (cache == null)
      return;
    List keys = cache.getKeys();
    for (Object key : keys) {
      String keyStr = key.toString();
      if (isRemove.apply(keyStr))
        cache.remove(key);
    }
  }

  /**
   * 清除所有，指定保留
   *
   * @param caches
   */
  public void clearAllExcept(List<String> caches) {
    String[] cacheNames = ehCacheCacheManager.getCacheManager().getCacheNames();
    for (String cacheName : cacheNames) {
      if (caches.contains(cacheName))
        continue;
      ehCacheCacheManager.getCacheManager().getCache(cacheName).removeAll();
    }
    return;
  }

  public void clear(String cacheName) {
    Cache cache = ehCacheCacheManager.getCacheManager().getCache(cacheName);
    if (cache == null) {
      throw new RuntimeException("cacheName不存在:" + cacheName);
    }
    cache.removeAll();
    return;
  }

  public void shutdown() {
    CacheManager.getInstance().shutdown();
  }

  /**
   * 手工主动刷新缓存
   *
   * @param cacheName
   */
  public void replaceCache(String cacheName, Object key, Object value) {
    Cache cache = ehCacheCacheManager.getCacheManager().getCache(cacheName);
    Element element = new Element(key, value);
    cache.replace(element);
  }

  public void flushDiskStore() {
    for (String cacheName : diskStoreCache) {
      Cache cache = ehCacheCacheManager.getCacheManager().getCache(cacheName);
      cache.flush();
      log.debug("flush缓存:" + cacheName + " 到本地磁盘: " + " InMemorySize:" + cache.calculateInMemorySize() / 1000 / 1000
          + "M OnDiskSize:" + cache.calculateOnDiskSize() / 1000 / 1000 + "M memoryStoreSize:"
          + cache.getMemoryStoreSize() + " diskStoreSize:" + cache.getDiskStoreSize());
    }
  }

}
