package com.xxx.collect.core.util.htmlparse;

import com.xxx.collect.core.util.RegexUtil;
import com.xxx.collect.core.util.string.StringUtil;
import org.htmlparser.*;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class HtmlParser {

  private Parser parser = null;

  public Parser getParser() {
    return parser;
  }

  public void setParser(Parser parser) {
    this.parser = parser;
  }

  public static HtmlParser getParser(String html) {
    return new HtmlParser(html);
  }

  public HtmlParser(String html) {
    try {
      parser = new Parser();
      parser.setInputHTML(html);
    } catch (ParserException e) {
      e.printStackTrace();
    }
  }

  public void visit(NodeVisitor visitor) {
    try {
      this.parser.reset();
      this.parser.visitAllNodesWith(visitor);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    }
  }

  private List<Tag> nodeList2TagList(NodeList nodeList) {
    List<Tag> tagList = new ArrayList<Tag>();
    if (nodeList != null) {
      for (Node node : nodeList.toNodeArray()) {
        tagList.add((Tag) node);
      }
    }
    return tagList;
  }

  public String getTitle() {
    parser.reset();
    NodeFilter filter = new TagNameFilter("title");
    List<LinkTag> list = new ArrayList<LinkTag>();
    NodeList nodeList = null;
    try {
      nodeList = this.parser.extractAllNodesThatMatch(filter);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    }
    if (nodeList != null && nodeList.size() > 0) {
      return nodeList.remove(0).toPlainTextString();
    } else {
      return null;
    }
  }

  public <T> T tagOne(T queryTag) {
    List<T> list = tag(queryTag);
    if (list.size() > 0) {
      return list.get(0);
    } else {
      return null;
    }
  }

  /**
   * @param <T>
   * @param queryTag
   * @return
   */
  public <T> List<T> tag(T queryTag) {
    parser.reset();
    List<T> list = new ArrayList<T>();
    Tag tag = (Tag) queryTag;
    Vector<Attribute> attrList = tag.getAttributesEx();
    List<NodeFilter> nodeFilterList = new ArrayList<NodeFilter>();
    NodeFilter nodeClassFilter = new NodeClassFilter(tag.getClass());
    nodeFilterList.add(nodeClassFilter);
    for (Attribute attr : attrList) {
      if (!attr.isEmpty() && !StringUtil.isBlank(attr.getName())) {
        HasAttributeFilter hasAttributeFilter = new HasAttributeFilter(attr.getName(), attr.getValue());
        nodeFilterList.add(hasAttributeFilter);
      }
    }
    AndFilter andFilter = new AndFilter(nodeFilterList.toArray(new NodeFilter[0]));
    NodeList nodeList;
    try {
      nodeList = this.parser.extractAllNodesThatMatch(andFilter);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    }
    for (Node node : nodeList.toNodeArray()) {
      T tag2 = (T) node;
      list.add(tag2);
    }
    return list;
  }


  /**
   * 必须只找到一个，多于一个或者没有则抛出异常
   *
   * @return
   */
  public Tag tagOneMust(String tagName, String attribute, String value) {
    parser.reset();
    List<Tag> list = tag(tagName, attribute, value);
    if (list == null || list.size() == 0)
      throw new RuntimeException("查找到0个tag,tagName=" + tagName + " attribute=" + attribute + " value=" + value);
    if (list.size() > 1)
      throw new RuntimeException("查找到" + list.size() + "个tag,多于要求的1个,tagName=" + tagName + " attribute=" + attribute + " value=" + value);
    return list.get(0);
  }

  /**
   * @return
   */
  public Tag tagOne(String tagName, String attribute, String value) {
    parser.reset();
    List<Tag> list = tag(tagName, attribute, value);
    if (list != null && list.size() > 0) {
      return list.get(0);
    } else {
      return null;
    }
  }

  /**
   * @return
   */
  public List<Tag> tag(String tagName, String attribute, String value) {
    parser.reset();
    List<Node> list = new ArrayList<Node>();
    NodeFilter tagNameFilter = new TagNameFilter(tagName);
    NodeFilter hasAttributeFilter = new HasAttributeFilter(attribute, value);
    AndFilter andFilter = new AndFilter(tagNameFilter, hasAttributeFilter);
    NodeList nodeList = null;
    try {
      nodeList = this.parser.extractAllNodesThatMatch(andFilter);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    }
    return nodeList2TagList(nodeList);
  }

  /**
   * @return
   */
  public Tag tagOne(String tagName) {
    parser.reset();
    List<Node> list = new ArrayList<Node>();
    NodeFilter nodeFilter = new TagNameFilter(tagName);
    NodeList nodeList;
    try {
      nodeList = this.parser.extractAllNodesThatMatch(nodeFilter);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    }
    if (nodeList.size() > 0) {
      return (Tag)nodeList.elementAt(0);
    } else {
      return null;
    }
  }

  /**
   * @return
   */
  public List<Tag> tag(String tagName) {
    parser.reset();
    List<Node> list = new ArrayList<Node>();
    NodeFilter nodeFilter = new TagNameFilter(tagName);
    NodeList nodeList = null;
    try {
      nodeList = this.parser.extractAllNodesThatMatch(nodeFilter);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    }
    return nodeList2TagList(nodeList);
  }

  /**
   * 提取所有链接
   *
   * @return
   * @
   */
  public List<LinkTag> link() {
    parser.reset();
    NodeFilter extract = new NodeClassFilter(LinkTag.class);
    List<LinkTag> list = new ArrayList<LinkTag>();
    NodeList nodeList;
    try {
      nodeList = this.parser.extractAllNodesThatMatch(extract);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    }
    for (Node node : nodeList.toNodeArray()) {
      LinkTag link = (LinkTag) node;
      list.add(link);
    }
    return list;
  }

  /**
   * 正则表达式匹配,url
   *
   * @param regex
   * @return
   * @
   */
  public List<LinkTag> linkRegex(String regex) {
    parser.reset();
    List<LinkTag> list = new ArrayList<LinkTag>();
    List<LinkTag> listAll = link();
    for (LinkTag link : listAll) {
      if (RegexUtil.match(link.getLink(), regex)) {
        list.add(link);
      }
    }
    return list;
  }

  /**
   * 多正则表达式匹配,url,多个条件之间是并且的关系
   *
   * @param regexs
   * @return
   * @
   */
  public List<LinkTag> linkRegex(String... regexs) {
    parser.reset();
    List<LinkTag> list = new ArrayList<LinkTag>();
    List<LinkTag> listAll = link();
    for (LinkTag link : listAll) {
      boolean isMatch = true;
      for (String regex : regexs) {
        // 如果有一个不匹配就跳出
        if (!RegexUtil.match(link.getLink(), regex)) {
          isMatch = false;
          break;
        }
      }
      if (isMatch) {
        list.add(link);
      }
    }
    return list;
  }

  /**
   * 提取所有链接(返回带http前缀和域名的绝对地址)
   *
   * @param parentUrl : 父链接，也必须是带http前缀和域名的绝对地址
   * @return
   * @
   */
  public List<LinkTag> absoluteLink(String parentUrl) {
    parser.reset();
    NodeFilter extract = new NodeClassFilter(LinkTag.class);
    List<LinkTag> list = new ArrayList<LinkTag>();
    NodeList nodeList;
    try {
      nodeList = this.parser.extractAllNodesThatMatch(extract);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    }
    for (Node node : nodeList.toNodeArray()) {
      LinkTag link = (LinkTag) node;
      String absoluteUrl = UrlParser.getAbsoluteUrl(parentUrl, link.getLink());
      link.setLink(absoluteUrl);
      list.add(link);
    }
    return list;
  }

  /**
   * 正则表达式匹配(返回带http前缀和域名的绝对地址)
   *
   * @param parentUrl : 父链接，也必须是带http前缀和域名的绝对地址
   * @param regex
   * @return
   * @
   */
  public List<LinkTag> absoluteLinkRegex(String parentUrl, String regex) {
    parser.reset();
    List<LinkTag> list = new ArrayList<LinkTag>();
    List<LinkTag> listAll = link();
    for (LinkTag link : listAll) {
      if (RegexUtil.match(link.getLink(), regex)) {
        String absoluteUrl = UrlParser.getAbsoluteUrl(parentUrl, link.getLink());
        link.setLink(absoluteUrl);
        list.add(link);
      }
    }
    return list;
  }

  /**
   * 多正则表达式匹配(返回带http前缀和域名的绝对地址)
   *
   * @param parentUrl : 父链接，也必须是带http前缀和域名的绝对地址
   * @param regexs
   * @return
   * @
   */
  public List<LinkTag> absoluteLinkRegex(String parentUrl, String[] regexs) {
    parser.reset();
    List<LinkTag> list = new ArrayList<LinkTag>();
    List<LinkTag> listAll = link();
    for (LinkTag link : listAll) {
      boolean isMatch = true;
      for (String regex : regexs) {
        // 如果有一个不匹配就跳出
        if (!RegexUtil.match(link.getLink(), regex)) {
          isMatch = false;
          break;
        }
      }
      if (isMatch) {
        String absoluteUrl = UrlParser.getAbsoluteUrl(parentUrl, link.getLink());
        link.setLink(absoluteUrl);
        list.add(link);
      }
    }
    return list;
  }

  /**
   * 获得纯文本
   */
  public String toPlainTextString() {
    String str = "";
    StringBean sb = new StringBean();
    // 设置不需要得到页面所包含的链接信息
    sb.setLinks(false);
    // 设置将不间断空格由正规空格所替代
    sb.setReplaceNonBreakingSpaces(true);
    // 设置将一序列空格由一个单一空格所代替
    sb.setCollapse(true);
    try {
      parser.visitAllNodesWith(sb);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    }
    str = sb.getStrings();
    if (!StringUtil.isBlank(str)) {
      str = str.replace("\n", ",");
      str = str.replace("\r", "");
    }
    return str;
  }

}
