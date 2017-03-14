package com.xxx.collect.core.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.xxx.collect.core.util.string.StringUtil;

/**
 * 16进制转换工具，用于修正百度爬虫错误的中文url解析
 */
public class HexUtil {
  public static void main(String[] args) {
    System.out.println(baiduSpiderHexToString("/xe6/x88/x98/xe6/x96/x97/xe5/x8f/xa4/xe9/xa3/x8e"));
  }

  public static String baiduSpiderHexToString(String hexStr) {
    String[] strs = hexStr.split("/x");
    List<String> listStr = new ArrayList<String>();
    for (String hex : strs) {
      if (StringUtil.isBlank(hex))
        continue;
      listStr.add(hex);
    }
    byte[] bytes = new byte[listStr.size()];
    for (int i = 0; i < listStr.size(); i++) {
      bytes[i] = Integer.valueOf(listStr.get(i), 16).byteValue();
    }
    String str = null;
    try {
      str = new String(bytes, "utf8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return str;
  }
}
