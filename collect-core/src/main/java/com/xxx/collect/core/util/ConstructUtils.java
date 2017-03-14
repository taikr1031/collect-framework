package com.xxx.collect.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author squall
 *
 */
public final class ConstructUtils {
  public static final Map<String, Object> EMPTY_MAP = new HashMap<String, Object>();
  
  private ConstructUtils() {
  }

  @SuppressWarnings("unchecked")
  public static <K, V> Map<K, V> map(Object... args) {
    if(args == null || args.length == 0) {
      return new HashMap<K, V>();
    }

    if(args.length % 2 != 0) {
      throw new RuntimeException("数组长度必须为2的倍数");
    }

    Map<K, V> result = new HashMap<K, V>(args.length / 2);
    for(int i = 0; i < args.length - 1; i += 2) {
      result.put((K) args[i], (V) args[i + 1]);
    }
    return result;
  }

  public static <T> List<T> list(T... args) {
    if(args == null || args.length == 0) {
      return new ArrayList<T>();
    }

    List<T> result = new ArrayList<T>(args.length);
    for(T o : args) {
      result.add(o);
    }
    return result;
  }

  public static <T> Set<T> set(T... args) {
    if(args == null || args.length == 0) {
      return new HashSet<T>();
    }

    Set<T> result = new HashSet<T>(args.length);
    for(T o : args) {
      result.add(o);
    }
    return result;
  }
}
