package com.xxx.collect.core.util.string;

import java.util.ArrayList;
import java.util.List;

import com.xxx.collect.core.util.RegexUtil;

public class EnglishUtil {

  public static void main(String[] args) {
    String hump = "oneAppleEat fuck You";
    System.out.println(humpToUnderLine(hump));
  }

  /**
   * 将驼峰转换为小写字母+下划线
   */
  public static String humpToUnderLine(String str) {
    String lastStr = "";
    List<Integer> underLinePosList = new ArrayList<Integer>();

    for (int i = 0; i < str.length(); i++) {
      String strTemp = new Character(str.charAt(i)).toString();
      if (RegexUtil.match(lastStr, "[a-z]") && RegexUtil.match(strTemp, "[A-Z]")) {
        underLinePosList.add(i);
      }
      lastStr = strTemp;
    }

    String newStr = str.toLowerCase();

    int tempAddCount = 0;
    for (Integer pos : underLinePosList) {
      newStr = newStr.substring(0, pos + tempAddCount) + "_" + newStr.substring(pos + tempAddCount);
      tempAddCount++;
    }

    return newStr;

  }
}
