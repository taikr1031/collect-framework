package com.xxx.collect.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luju on 2015-10-22.
 */
public class ListUtil {

  public static void main(String[] args) {
    List<String> strs = ConstructUtils.list("1","2","3","4","5","6","7","8","9","10","11");
    List<List<String>> lists = splitByGroupCount(strs, 3);
    for (List<String> list : lists) {
      System.out.println("group:"+list.size());
      for (String s : list) {
        System.out.println(s);
      }
    }
  }

  /**
   * 把list分割成二维list,指定多少个组
   *
   * @param groupCount 指定分成多少个组
   * @param <T>
   * @return
   */
  public static <T> List<List<T>> splitByGroupCount(List<T> records, int groupCount) {
    List<List<T>> listBatch = new ArrayList<>();
    for (int i = 0; i < groupCount; i++) {
      listBatch.add(new ArrayList<>());
    }
    int index = 0;
    for (T record : records) {
      List<T> list = listBatch.get(index);
      list.add(record);
      index++;
      if (index >= listBatch.size())
        index = 0;
    }
    return listBatch;
  }

  /**
   * 把list分割成二维list,指定每一组的数量
   *
   * @param perGroupCount 切记，这里是每一组切分的数量，不是一共切分为多少组
   * @param <T>
   * @return
   */
  public static <T> List<List<T>> splitByPerGroupCount(List<T> records, int perGroupCount) {
    List<List<T>> listBatch = new ArrayList<>();
    int groupIndex = 0;
    List<T> currentItemList = null;
    for (T t : records) {
      if (groupIndex == 0) {
        currentItemList = new ArrayList<T>();
        listBatch.add(currentItemList);
      }
      currentItemList.add(t);
      groupIndex++;
      if (groupIndex >= perGroupCount)
        groupIndex = 0;
    }
    return listBatch;
  }

}
