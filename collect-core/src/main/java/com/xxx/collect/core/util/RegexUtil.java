package com.xxx.collect.core.util;

import com.xxx.collect.core.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

  public static void main(String[] args) {
    String[] split = "j,i^1".split(",|\\^");
    for (String s : split) {
      System.out.println(s);
    }


//    String strReplace = "char charjjj char charT char";
//    String replace = "char";
//    String replaceTo = "人物";
//    String char$ = search(strReplace, "char$");
//    String replace1 = replace(strReplace, "char$", "人物");
//    System.out.println(replace1);
//    System.out.println(replace(replace1, "char ", "人物 "));
//
//    List<String> scripts = RegexUtil.searchBetweenList("html的内容", "<script>([\\s|\\S]+?)</script>");
//    for (String script : scripts) {
//      System.out.println(script);
//    }
//
//    System.out.println(replace(replace("1!2#$   2_3-1", "[^a-z^0-9]", "_"), "_+", "_"));
//
//    System.out.println(isEnglishWord("abcD1"));
//
//    String str = RegexUtil.searchBetween("html的内容", "g_page_config = (\\{[\\s|\\S]+?\\});");
//
//    String string = "Water Birds; Asian Darter And Spo(水 鸟; 亚洲 镖 和 Spo)";
//    String regex = "^([\\s\\S]+?)\\(([\\s\\S]*)\\)$";
//    List<String> list = RegexUtil.searchBetweenList(string, regex);
//    System.out.println(list.size());
//
//    System.out.println(replace("Naruto Shipp&#363;den: Dairansen! Kage Bunshin Emaki", "&#\\d{3,4};", " "));
//
//    System.out.println(searchList("/sss/sss", "s").size());
//    System.out.println(findCount("/sss/sss", "s"));
  }

  public static String LANG_ZHONGWEN = "\\u4e00-\\u9fa5";
  public static String LANG_GET_ZHONGWEN = "[" + LANG_ZHONGWEN + "]+";

  public static String search(String string, String regex, int from) {
    return search(string.substring(from), regex);
  }

  /**
   * 是否完全匹配
   *
   * @param string
   * @param regex
   * @return
   */
  public static boolean match(String string, String regex) {
    if (string == null || regex == null)
      return false;
    return string.equals(search(string, regex)) ? true : false;
  }

  public static boolean isZhongWen(String string) {
    return match(string, LANG_GET_ZHONGWEN);
  }

  /**
   * 是否英文单次的格式
   *
   * @param string
   * @return
   */
  public static boolean isEnglishWord(String string) {
    return match(string.toLowerCase(), "[a-z]+");
  }

  public static boolean containsZhongWen(String string) {
    return search(string, LANG_GET_ZHONGWEN) != null;
  }

  public static int indexOf(String string, String regex) {
    Pattern ps = Pattern.compile(regex);
    Matcher matcher = ps.matcher(string);
    if (matcher.find()) {
      int ret = matcher.start();
      return ret;
    }
    return -1;
  }

  public static boolean contain(String str, String regex) {
    return indexOf(str, regex) != -1;
  }

  public static String search(String string, String regex) {
    Pattern ps = Pattern.compile(regex);
    Matcher matcher = ps.matcher(string);
    if (matcher.find()) {
      String ret = matcher.group();
      return ret;
    }
    return null;
  }

  /**
   * 通过正则表达式返回list
   *
   * @param string
   * @param regex
   * @return
   */
  public static List<String> searchList(String string, String regex) {
    Pattern ps = Pattern.compile(regex);
    Matcher matcher = ps.matcher(string);
    List<String> list = new ArrayList<String>();
    String ret;
    while (matcher.find()) {
      ret = matcher.group();
      list.add(ret);
    }
    return list;
  }


  /**
   * 查找字符串出现的次数
   *
   * @param str
   * @param regex
   * @return
   */
  public static int findCount(String str, String regex) {
    return searchList(str, regex).size();
  }

  /**
   * 第一层list放正则匹配list，第二层放，正则里面的多个小括号匹配list
   *
   * @param string
   * @param regex
   * @return
   */
  public static List<List<String>> searchListList(String string, String regex) {
    Pattern ps = Pattern.compile(regex);
    Matcher matcher = ps.matcher(string);
    List<List<String>> list = new ArrayList<List<String>>();
    String ret;
    while (matcher.find()) {
      List<String> list2 = new ArrayList<String>();
      for (int i = 1; i <= matcher.groupCount(); i++) {
        ret = matcher.group(i);
        list2.add(ret);
      }
      list.add(list2);
    }
    return list;
  }

  /**
   * 用括号中的内容，替换正则匹配的全部内容,也就是把一整句，替换成一个部分 比如：<a href='http://www.hao123.com'
   * >(hao123)</a> = hao123
   */
  public static String replaceBetweenReplace(String orgStr, String regex) {
    List<String> allList = searchList(orgStr, regex);
    List<String> partList = searchListBetween(orgStr, regex);
    for (int i = 0; i < allList.size(); i++) {
      String all = allList.get(i);
      String part = partList.get(i);
      orgStr = StringUtil.replaceOnce(orgStr, all, part);
    }
    return orgStr;
  }

  /**
   * 将正则表达式匹配的部分替换成空白
   */
  public static String replaceToNull(String orgStr, String regex) {
    Pattern p = Pattern.compile(regex, Pattern.DOTALL);
    String str = p.matcher(orgStr).replaceAll("");
    return str;
  }

  /**
   * 将正则表达式匹配的部分替换成指定字符
   */
  public static String replace(String orgStr, String regex, String newStr) {
    Pattern p = Pattern.compile(regex, Pattern.DOTALL);
    String str = p.matcher(orgStr).replaceAll(newStr);
    return str;
  }

  /**
   * 将正则表达式匹配的部分替换成指定字符
   */
  public static String replaceBetween(String orgStr, String regex, String newStr) {
    String rest = searchBetween(orgStr, regex);
    if (StringUtil.isBlank(rest))
      return orgStr;
    String replace = orgStr.replace(rest, newStr);
    return replace;
  }

  /**
   * 先正则返回list，再提取一个括号，最后返回括号中的list，只支持一个括号
   *
   * @param string
   * @param regex
   * @return
   */
  public static List<String> searchListBetween(String string, String regex) {
    Pattern ps = Pattern.compile(regex);
    Matcher matcher = ps.matcher(string);
    List<String> list = new ArrayList<String>();
    String ret = null;
    while (matcher.find()) {
      if (matcher.groupCount() > 0) {
        ret = matcher.group(1);
      }
      list.add(ret);
    }
    return list;
  }

  /**
   * 可以使用小括号提取,提取第一个
   *
   * @param string
   * @param regexGroup
   * @return
   */
  public static String searchBetween(String string, String regexGroup) {
    if(StringUtil.isBlank(string))
      return null;
    Pattern ps = Pattern.compile(regexGroup);
    Matcher matcher = ps.matcher(string);
    String ret = null;
    if (matcher.find()) {
      if (matcher.groupCount() > 0) {
        ret = matcher.group(1);
      }
      return ret;
    }
    return null;
  }

  // 前后替换去掉提取
  public static String searchBetween(String string, String sRegex, String eRegex) {
    String str = RegexUtil.search(string, sRegex + ".*" + eRegex);
    str = str.replaceAll(sRegex, "");
    str = str.replaceAll(eRegex, "");
    return str;
  }

  /**
   * 使用括号来提取匹配的字符，多个括号使用
   *
   * @param string
   * @param regexGroup
   * @return
   */
  public static List<String> searchBetweenList(String string, String regexGroup) {
    Pattern ps = Pattern.compile(regexGroup);
    Matcher matcher = ps.matcher(string);
    String ret = null;
    List<String> list = new ArrayList<String>();
    if (matcher.find()) {
      for (int i = 1; i <= matcher.groupCount(); i++) {
        ret = matcher.group(i);
        list.add(ret);
      }
    }
    return list;
  }

  /*
   * splitRegex代表每一项的开始，endRegex表示最后一项的结束，这些项是连续的，因此除了最后一项，前面
   * 的项都没有结束，只有靠下一项的开始来代表结束
   */
  public static List<String> searchSerial(String string, String splitRegex, String endRegex) {
    Pattern ps = Pattern.compile(splitRegex);
    Matcher matcher = ps.matcher(string);
    List<String> list = new ArrayList<String>();
    int startPos, endPos;
    if (matcher.find()) {
      startPos = matcher.start();
    } else {
      return list;
    }
    while (matcher.find()) {
      endPos = matcher.start();
      list.add(string.substring(startPos, endPos));
      startPos = endPos;
    }
    // 处理最后一个项
    endPos = indexOf(string, endRegex);
    list.add(string.substring(startPos, endPos));
    return list;
  }

  /**
   * 相当于String.split,但是会保留split的分割字符
   *
   * @param org
   * @param regex
   * @return
   */
  public static List<String> splitReserveSplitChar(String org, String regex) {
    List<String> rest = new ArrayList<>();
    do {
      String splitChar = search(org, regex);
      if (splitChar == null)
        break;
      int indexOf = org.indexOf(splitChar) + splitChar.length();
      rest.add(org.substring(0, indexOf));
      org = org.substring(indexOf);
    } while (true);
    rest.add(org);
    return rest;
  }

}
