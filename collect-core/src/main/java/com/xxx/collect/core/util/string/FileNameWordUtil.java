package com.xxx.collect.core.util.string;

import com.xxx.collect.core.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名称的文字处理工具类
 */
public class FileNameWordUtil {


  public static void main(String[] args) {
    String str = "JB HD-1";
    System.out.println(StringUtil.join(smartSplie(str,true,0),","));

  }

  /**
   * 智能分割文件或者文件夹的名字
   *
   * @param text
   * @param isHumpSplitEnglist 是否进行英文的驼峰分割
   * @return
   */
  public static List<String> smartSplie(String text, boolean isHumpSplitEnglist) {
    return smartSplie(text,isHumpSplitEnglist,2);
  }
  /**
   * 智能分割文件或者文件夹的名字
   *
   * @param text
   * @param isHumpSplitEnglist 是否进行英文的驼峰分割
   * @return
   */
  public static List<String> smartSplie(String text, boolean isHumpSplitEnglist,int minWordLength) {
    List<String> wordList = RegexUtil.searchListBetween(text, "([^-^_^\\d^\\\\^\\s^：^.^的]+)");
    // 再把英文和中文分开
    List<String> wordList2 = new ArrayList<String>();
    for (String word : wordList) {
      List<String> engList = RegexUtil.searchList(word, "\\w+");
      // 将英文单词再进行驼峰的分割
      List<String> engList2 = new ArrayList<>();
      if (isHumpSplitEnglist) {
        for (String eng : engList) {
          List<String> englistWords = GetEnglistHumpWord.getHumpWords(eng, minWordLength);
          engList2.addAll(englistWords);
        }
        engList = engList2;
      }
      List<String> zhList = RegexUtil.searchList(word, "[\\u4e00-\\u9fa5]+");
      wordList2.addAll(engList);
      wordList2.addAll(zhList);
    }
    return wordList2;
  }

  /**
   * 判断唯一时需要去除的字符
   */
  private static String[] inUniqueChars = {"\\d+$", " +", "-+", "_+"};

  /**
   * 把文件名进行压缩，全转小写，并且去除空格和-_等字符
   */
  private static String getUniqueName(String name) {
    String rest = name.toLowerCase().trim();
    //只保留中文和英文,去除相同字符
    List<String> strs = new ArrayList<>();
    for (int i = 0; i < rest.length() - 1; i++) {
      String chr = rest.substring(i, i + 1);
      if (RegexUtil.containsZhongWen(chr) || RegexUtil.match(chr, "[a-z]"))
        strs.add(chr);
    }
    //strs.sort((o1, o2) -> o1.compareTo(o2));
    return StringUtil.join(strs, "");
  }


  /**
   * 文件名称是否包含另外一个文件名称
   *
   * @param srcFileName
   * @param tarFileName
   * @return
   */
  public static boolean fileNameIsContain(String srcFileName, String tarFileName) {
    return getUniqueName(srcFileName).contains(getUniqueName(tarFileName));
  }


  /**
   * 文件名称是否包含另外一个文件名称的所有字符,按字符统计
   *
   * @param srcFileName
   * @param tarFileName
   * @return
   */
  public static boolean fileNameIsContainAllChar(String srcFileName, String tarFileName) {
    boolean isContain = true;
    srcFileName = srcFileName.toLowerCase();
    tarFileName = tarFileName.toLowerCase().trim();
    //只比较中文和英文
    for (int i = 0; i < tarFileName.length() - 1; i++) {
      String chr = tarFileName.substring(i, i + 1);
      if (RegexUtil.containsZhongWen(chr) || RegexUtil.match(chr, "[a-z]"))
        if (!srcFileName.contains(chr))
          return false;
    }
    return true;
  }

}
