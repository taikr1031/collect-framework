package com.xxx.collect.core.util;

import com.xxx.collect.core.util.string.StringUtil;

/**
 * 用于过滤客户端提交的数据
 */
public class SecurityUtil {

  public static void main(String[] args) {
    String message = " The King of Fighters '94: ReBout";
    System.out.println(geiTransHtml(message));
    System.out.println(isInvalidFileStorePath("sdf../dd"));
  }

  private static String[] trans = {"&amp;", "&lt;", "&gt;", "&quot;", "&apos;", "\\\\"};
  private static String[] htmls = {"&", "<", ">", "\"", "'", "\\"};//&一定要放在第一个

  //url保留
  private static String urlReserve = "&";

  //爱给在数据库中保留了的字符，需要转义
  private static String[] geiDbTrans = {"'"};
  private static String[] geiDbHtmls = {"&quot;"};

  /**
   * 是否是服务器规定的文件存储path字符串
   * 主要是防止用户任意提交内容，出现跳出临时目录存储下载任意文件
   * @return
   */
  public static boolean isInvalidFileStorePath(String path){
    return path.contains("../") || path.contains("..\\");
  }

  /**
   * 爱给特定的保留字符转义html
   *
   * @return
   */
  public static String geiTransHtml(String message) {
    if (message == null)
      return null;
    String result = message;
    for (int i = 0; i < geiDbTrans.length; i++) {
      result = StringUtil.replace(result, geiDbTrans[i], geiDbHtmls[i]);
    }
    return (result.toString());
  }

  /**
   * 删除html特殊字符
   *
   * @return
   */
  public static String removeHtmlChar(String message) {
    if (message == null)
      return null;
    String result = message;
    for (int i = 0; i < htmls.length; i++) {
      result = StringUtil.replace(result, htmls[i], "");
    }
    return (result.toString());
  }


  /**
   * 恢复被转义的html特殊字符
   *
   * @return
   */
  public static String recoverHtmlChar(String message) {
    if (message == null)
      return null;
    String result = message;
    for (int i = 0; i < trans.length; i++) {
      result = StringUtil.replace(result, trans[i], htmls[i]);
    }
    return (result.toString());
  }

  /**
   * 转义html特殊字符
   *
   * @return
   */
  public static String transHtmlChar(String message) {
    if (message == null)
      return null;
    String result = message;
    for (int i = 0; i < trans.length; i++) {
      result = StringUtil.replace(result, htmls[i], trans[i]);
    }
    return (result.toString());
  }

  /**
   * 过滤html特殊字符,但是不转换&符号
   *
   * @return
   */
  public static String transHtmlCharForUrl(String message) {
    if (message == null)
      return null;
    String result = message;
    for (int i = 0; i < trans.length; i++) {
      if (htmls[i].equals(urlReserve))
        continue;
      result = StringUtil.replace(result, htmls[i], trans[i]);
    }
    return (result.toString());
  }
}
