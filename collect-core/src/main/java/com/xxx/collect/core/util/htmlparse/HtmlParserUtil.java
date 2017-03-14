package com.xxx.collect.core.util.htmlparse;

import com.xxx.collect.core.util.RegexUtil;
import org.htmlparser.Node;
import org.htmlparser.Text;
import org.htmlparser.util.NodeList;

import com.xxx.collect.core.util.string.StringUtil;

public class HtmlParserUtil {

  /**
   * @param args
   */
  public static void main(String[] args) {
    HtmlParser parser = HtmlParser.getParser("<li>234324</li>");
    // parser.
  }

  /**
   * 压缩html内容，去除换行和多余的空格
   */
  public static String compressHtml(String html) {
    if (StringUtil.isBlank(html))
      return "";
    html = html.replaceAll("\\s+", " ");
    html = html.replace("> <", "><");
    html = html.trim();
    return html;
  }

  /**
   * 获得内部html
   */
  public static String getInnerHtml(Node node) {
    String str = "";
    if (node == null)
      return str;
    NodeList nodeList = node.getChildren();
    if (nodeList == null || nodeList.toNodeArray().length == 0) {
      return str;
    }
    for (Node cNode : nodeList.toNodeArray()) {
      str += cNode.toHtml();
    }
    return str;
  }

  /**
   * 遍历所有的txt
   */
  public static String getInnerText(Node node) {
    return getInnerText(node, "");
  }

  /**
   * 遍历所有的txt
   */
  public static String getInnerText(Node node, String splitStr) {
    String str = "";
    if (node == null)
      return str;
    if (node instanceof Text) {
      str += ((Text) node).getText();
      return str;
    }
    NodeList nodeList = node.getChildren();
    if (nodeList == null || nodeList.toNodeArray().length == 0) {
      return str;
    }
    for (Node cNode : nodeList.toNodeArray()) {
      str += getInnerText(cNode, splitStr) + splitStr;
    }
    if (splitStr.length() > 0) {
      str = RegexUtil.replace(str, "\\s+", "");
      str = RegexUtil.replace(str, splitStr + "+", splitStr);
    }
    return str;
  }
}
