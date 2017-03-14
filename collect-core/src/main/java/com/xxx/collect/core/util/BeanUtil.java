package com.xxx.collect.core.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.awt.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

public class BeanUtil {

  public static void main(String[] args) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("width", 123);
    map.put("height", 345);
    Dimension dim = new Dimension();
    copyProperties(dim, map);
    System.out.println(dim.getWidth());
  }

  /**
   * 比较两个对象是不是相等，可以比较null,如果都是null就是true
   *
   * @param obj1
   * @param obj2
   * @return
   */
  public static boolean equals(Object obj1, Object obj2) {
    if (obj1 == null && obj2 == null)
      return true;
    if (obj1 != null && obj2 == null)
      return false;
    if (obj1 == null && obj2 != null)
      return false;
    return obj1.equals(obj2);
  }

  public static String toString2(Object bean) {
    String str = toString(bean);
    str = str.replace(" ; ", "\n");
    return str;
  }

  public static String toString(Object bean) {
    String info = "";
    java.beans.BeanInfo beanInfo;
    try {
      beanInfo = java.beans.Introspector.getBeanInfo(bean.getClass());
    } catch (IntrospectionException e1) {
      throw new RuntimeException(e1);
    }
    info += beanInfo.getBeanDescriptor().getName() + " : ";
    PropertyDescriptor[] descriptors = getAvailablePropertyDescriptors(bean);
    for (int i = 0; descriptors != null && i < descriptors.length; i++) {
      Method readMethod = descriptors[i].getReadMethod();
      try {
        Object value = readMethod.invoke(bean, null);
        if (value != null) {
          info += descriptors[i].getName() + "=" + value + " ; ";
        }
      } catch (Exception e) {
//        e.printStackTrace();
      }
    }
    return info;
  }

  public static String toStringDetail(Object bean) {
    String info = "";
    PropertyDescriptor[] descriptors = getAvailablePropertyDescriptors(bean);
    for (int i = 0; descriptors != null && i < descriptors.length; i++) {
      Method readMethod = descriptors[i].getReadMethod();
      try {
        Object value = readMethod.invoke(bean, null);
        if (value != null)
          info += "[" + bean.getClass().getName() + "]." + descriptors[i].getName() + "("
              + descriptors[i].getPropertyType().getName() + ") = " + value;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return info;
  }

  /**
   * 将javaBean转换成Map
   *
   * @param javaBean javaBean
   * @return Map对象
   */
  public static Map<String, String> beanToMap(Object javaBean) {
    Map<String, String> result = new HashMap<String, String>();
    Method[] methods = javaBean.getClass().getDeclaredMethods();

    for (Method method : methods) {
      try {
        if (method.getName().startsWith("get")) {
          String field = method.getName();
          field = field.substring(field.indexOf("get") + 3);
          field = field.toLowerCase().charAt(0) + field.substring(1);

          Object value = method.invoke(javaBean, (Object[]) null);
          result.put(field, null == value ? "" : value.toString());
        }
      } catch (Exception e) {
      }
    }

    return result;
  }

  public static void copyProperties(Object target, Object source) {
    BeanUtils.copyProperties(source, target);
  }


  public static <T> T copyPropertiesReturnTarget(T target, Object source) {
    BeanUtils.copyProperties(source, target);
    return target;
  }

  /**
   * 拷贝属性，原对象中的属性为null，不拷贝
   *
   * @param target
   * @param source
   */
  public static void copyPropertiesExcludeNull(Object target, Object source) {
    BeanUtils.copyProperties(source, target,getNullPropertyNames(source));
  }

  /**
   * 获取所有为空的属性
   *
   * @param source
   * @return
   */
  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<String>();
    for (java.beans.PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null) emptyNames.add(pd.getName());
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

  /**
   * * 从 bean 中读取有效的属性描述符. * * NOTE: 名称为 class 的 PropertyDescriptor 被排除在外. * * @param
   * bean * Object - 需要读取的 Bean * @return PropertyDescriptor[] - 属性列表
   */
  public static PropertyDescriptor[] getAvailablePropertyDescriptors(Object bean) {
    try {
      // 从 Bean 中解析属性信息并查找相关的 write 方法
      java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(bean.getClass());
      if (info != null) {
        PropertyDescriptor pd[] = info.getPropertyDescriptors();
        Vector columns = new Vector();
        for (int i = 0; i < pd.length; i++) {
          String fieldName = pd[i].getName();
          if (fieldName != null && !fieldName.equals("class")) {
            columns.add(pd[i]);
          }
        }
        PropertyDescriptor[] arrays = new PropertyDescriptor[columns.size()];
        for (int j = 0; j < columns.size(); j++) {
          arrays[j] = (PropertyDescriptor) columns.get(j);
        }
        return arrays;
      }
    } catch (Exception ex) {
      System.out.println(ex);
      return null;
    }
    return null;
  }
}
