package com.xxx.collect.core.service;

import com.xxx.collect.core.util.io.IOUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * 将java对象序列化到磁盘的缓存服务
 * 目前没有使用了，因为速度和直接从数据库取差不多
 */
@Service
public class DiskCacheService {

  private static final Log log = LogFactory.getLog(DiskCacheService.class);
  private static File diskCacheDir = new File("/geiDiskCache");

  static {
    if (!diskCacheDir.exists())
      diskCacheDir.mkdirs();
  }

  @SuppressWarnings("unchecked")
  public <T> T get(String key, Class<T> cls) {
    Object obj = this.getObj(key);
    if (obj == null)
      return null;
    log.debug("取出磁盘缓存:" + key);
    return (T) obj;
  }

  public void put(String key, Object obj) {
    log.debug("开始存放磁盘缓存:" + key);
    ObjectOutputStream out;
    try {
      out = new ObjectOutputStream(new FileOutputStream(getObjFile(key)));
      out.writeObject(obj);
      out.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    log.debug("结束存放磁盘缓存:" + key);
  }

  public void clear(String key) {
    File objFile = this.getObjFile(key);
    if (objFile.exists())
      IOUtil.fileDelete(objFile);
    log.debug("清除磁盘缓存:" + key);
  }

  private File getObjFile(String key) {
    File file = new File(diskCacheDir, key);
    if (!file.getParentFile().exists())
      file.getParentFile().mkdirs();
    return file;
  }

  private Object getObj(String key) {
    File file = getObjFile(key);
    if (!file.exists())
      return null;
    log.debug("DiskCacheService开始从本地取出,key=" + key);
    ObjectInputStream in;
    Object object = null;
    try {
      in = new ObjectInputStream(new FileInputStream(file));
      object = (Object) in.readObject();
      in.close();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException();
    }
    return object;
  }
}
