package com.xxx.collect.core.util;

import com.xxx.collect.core.util.string.StringUtil;

import java.util.List;

/**
 * Created by luju on 2015-11-04.
 */
public class CheckUtil {


  public static void listSizeNotEmpty(List<?> list, String msg) {
    largeThen(list.size(), 0, msg);
  }


  public static void listSizeEqual(List<?> list, int n, String msg) {
    if (list == null)
      throw new RuntimeException("list 不允许为空! : " + msg);
    if (list.size() != n)
      throw new RuntimeException("list.size() 必须等于 " + n + " : " + msg);
  }

  public static void largeThen(int obj, int largeThen, String msg) {
    if (!(obj > largeThen))
      throw new RuntimeException("必须大于" + largeThen + " msg=" + msg);
  }

  public static void equal(Object obj, Object mustEqObj, String msg) {
    notNull(obj, msg);
    if (!obj.equals(mustEqObj))
      throw new RuntimeException("对象必须相等! obj=" + obj + " 必须等于目标对象:" + mustEqObj + " msg=" + msg);
  }

  public static void notNull(Object obj) {
    notNull(obj, "");
  }

  public static void notNull(Object obj, String msg) {
    if (obj == null)
      throw new RuntimeException("对象不允许空! : " + msg);
  }

  public static void notBlank(String str) {
    notBlank(str, "");
  }

  public static void notBlank(String str, String msg) {
    if (StringUtil.isBlank(str))
      throw new RuntimeException("String 不允许空! : " + msg);
  }

  public static void contain(String str, String containStr, String msg) {
    if (str == null || !str.contains(containStr)) {
      throw new RuntimeException(str + " 字符串必须包含：" + containStr + " : " + msg);
    }
  }


}
