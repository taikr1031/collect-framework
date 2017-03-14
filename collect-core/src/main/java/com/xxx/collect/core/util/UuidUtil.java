package com.xxx.collect.core.util;

import java.util.UUID;

/**
 * @author 鲁炬
 * 
 */
public class UuidUtil {
  public static String uuid() {
    String uuid = UUID.randomUUID().toString().replace("-", "");
    // uuid 中不要包含 av 非法关键词
    if (uuid.contains("av"))
      return uuid();
    return uuid;
  }
}
