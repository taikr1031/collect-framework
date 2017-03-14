package com.xxx.collect.core.util.string;

import com.xxx.collect.core.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 智能提取句子中的英文单词
 */
public class GetEnglistHumpWord {

  public static void main(String[] args) {
    String str = "MDElement1.wav";
    List<String> list = getHumpWords(str, 2);
    for (String string : list) {
      System.out.println(string);
    }
  }

  /**
   * 至少3位
   */
  public static List<String> getEnglistWords(String str) {
    return getHumpWords(str, 3);
  }

  /**
   * 进行英文的驼峰解析
   * @param str
   * @param lestLength
   * @return
   */
  public static List<String> getHumpWords(String str, int lestLength) {
    // 1、提取一个句子中的英文单词，驼峰式或者全大写，全小写
    List<String> list1 = RegexUtil.searchList(str, "[A-Z]{0,10}[a-z]{"+(lestLength<=1?1:lestLength)+",20}+|[A-Z]{1,20}");
    // 2、只保留3个以上字符的单词，去重
    List<String> list2 = new ArrayList<String>();
    for (String eng1 : list1) {
      if (eng1.length() < lestLength)
        continue;
      if (!list2.contains(eng1)) {
        list2.add(eng1);
      }
    }
    return list2;
  }

}
