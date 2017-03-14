package com.xxx.collect.core.util;

import com.xxx.collect.core.tool.CacheTool;
import com.xxx.collect.core.util.io.IOUtil;
import com.xxx.collect.core.util.string.StringUtil;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

public class HttpUtil {

  public static void main(String[] args) {
    System.out.println(getDomain("http://www.2gei.co"));
  }

  /**
   * 是否是访问的网站html页面
   *
   * @return
   */
  public static boolean isVisitHtmlPage(String servletPath) {
    return servletPath != null
        && !servletPath.startsWith("/favicon.ico")
        && !servletPath.startsWith("/assets/")
        && !servletPath.startsWith("/f/")
        && !servletPath.startsWith("/crossdomain.xml")
        && !servletPath.startsWith("/playLog/saveBeat")
        && !servletPath.startsWith("/file/down/");
  }

  /**
   * 返回字符串的结果
   *
   * @param response
   * @throws ServletException
   * @throws IOException
   */

  public static void returnString(HttpServletResponse response, String returnStr) {
    response.setHeader("Content-Type", "text/html;charset=utf-8");
    try {
      IOUtil.copyAndClose(new ByteArrayInputStream(returnStr.getBytes("utf-8")), response.getOutputStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void return404Page(HttpServletRequest request, HttpServletResponse response) throws ServletException,
      IOException {
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/notfound");
    requestDispatcher.forward(request, response);
    return;
  }


  public static void return403Page(HttpServletRequest request, HttpServletResponse response) throws ServletException,
      IOException {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/no-authority");
    requestDispatcher.forward(request, response);
    return;
  }

  public static void return503Page(String pageMessage, HttpServletRequest request, HttpServletResponse response) throws ServletException,
      IOException {
    response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    request.setAttribute("pageMessage", pageMessage);
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/page503");
    try {
      requestDispatcher.forward(request, response);
    } catch (IllegalStateException e) {

    }
    return;
  }

  /**
   * 获取请求的url根路径
   *
   * @param request
   * @return
   */
  public static String getRequestRootPathUrl(HttpServletRequest request) {
    return request.getScheme() + "://" + request.getServerName() + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort());
  }

  /**
   * 解析一个url的域名
   *
   * @param url
   * @return
   */
  public static String getDomain(String url) {
    String httpHeader = null;
    if (url == null)
      return null;
    if (url.startsWith("http://"))
      httpHeader = "http://";
    if (url.startsWith("https://"))
      httpHeader = "https://";
    if (httpHeader == null)
      return null;
    url = url.substring(httpHeader.length(), url.length());
    int indexOf = url.indexOf("/");
    if (indexOf > 0)
      return url.substring(0, indexOf);
    return url;
  }

  public static String getReferer(HttpServletRequest request) {
    String referer = request.getHeader("referer");
    return referer;
  }

  /**
   * 在url后面追加参数，会自动判断是加?还是&
   *
   * @param url
   * @param name
   * @param value
   * @return
   */
  public static String urlAddParm(String url, String name, String value) {
    return url + addQueryChar(url) + name + "=" + value;
  }

  /**
   * 判断url是否包含?，返回?或者&
   *
   * @param url
   * @return
   */
  public static String addQueryChar(String url) {
    return !url.contains("?") ? "?" : "&";
  }

  /**
   * 拼一个http的完整url
   *
   * @param domain
   * @param port
   * @param pathUrl
   * @return
   */
  public static String genHttpUrl(String domain, int port, String pathUrl) {
    String url = "http://" + domain + (port == 80 ? "" : ":" + port);
    if (!StringUtil.isBlank(pathUrl)) {
      if (!pathUrl.startsWith("/"))
        url += "/";
      url += pathUrl;
    }
    return url;
  }

  /**
   * 获取格式如 http://www.2gei.com:8080的格式url
   *
   * @param request
   * @return
   */
  public static String getHttpDomianAndPortUrl(ServletRequest request) {
    String url = "http://" + request.getServerName()
        + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort());
    return url;
  }

  /**
   * 从完整url中解析domain
   *
   * @param url
   * @return
   */
  public static String parseDomain(String url) {
    if (url == null)
      return null;
    String domain = RegexUtil.searchBetween(url, "^https?://([^:/]+):?\\d*/");
    if (domain == null)
      domain = RegexUtil.searchBetween(url, "^https?://([^:/]+):?\\d*$");
    return domain;
  }

  /**
   * 改变url的域名
   */
  public static String changeDomainUrl(HttpServletRequest request, String domainSrc, String domainReplace) {
    String serverName = request.getServerName();
    String url = HttpUtil.genHttpUrl(serverName.replace(domainSrc, domainReplace), request.getServerPort(),
        HttpUtil.getFullUrl(request));
    // 设置安全url
    return SecurityUtil.transHtmlCharForUrl(url);
  }


  /**
   * 获取url完整路径，包括域名端口和url
   *
   * @param request
   * @return
   */
  public static String getDomainPortAndFullUrl(HttpServletRequest request) {
    String host = request.getServerName();
    int serverPort = request.getServerPort();
    String url = "http://" + host + (serverPort == 80 ? "" : ":" + serverPort) + HttpUtil.getFullUrl(request);
    return url;
  }

  /**
   * 获取url完整路径，可以自动拼接post和get的参数，并且会保证参数的顺序正确
   *
   * @param request
   * @return
   */
  public static String getFullUrl(HttpServletRequest request) {
    return getFullUrl(request, new String[0]);
  }

  /**
   * 获取url完整路径，可以自动拼接post和get的参数，并且会保证参数的顺序正确
   *
   * @param request
   * @return
   */
  public static String getFullUrl(HttpServletRequest request, String... ignoreParms) {
    List<String> ignoreParmsList = new ArrayList<String>();
    if (ignoreParms != null && ignoreParms.length > 0)
      ignoreParmsList = Arrays.asList(ignoreParms);
    String urlPath = request.getServletPath();
    Enumeration<String> parameterNames = request.getParameterNames();
    List<String> parameterNameList = new ArrayList<String>();
    while (parameterNames.hasMoreElements()) {
      String paramName = parameterNames.nextElement();
      if (ignoreParmsList.contains(paramName))
        continue;
      parameterNameList.add(paramName);
    }
    //tomcat 7 ,传递的参数顺序是正确的，不需要反序
    //Collections.reverse(parameterNameList);
    if (parameterNameList.size() > 0)
      urlPath += "?";
    int i = 0;
    for (String paramName : parameterNameList) {
      i++;
      String[] paramValues = request.getParameterValues(paramName);
      for (String paramValue : paramValues) {
        urlPath += paramName + "=";
        urlPath += paramValue;
      }
      if (i < parameterNameList.size())
        urlPath += "&";
    }
    return urlPath;
  }

  public static String getAgent(HttpServletRequest request) {
    String header = request.getHeader("USER-AGENT");
    if (header == null)
      return "";
    return header;
  }

  /**
   * 设置http文件下载的头
   *
   * @param fileName
   * @param request
   * @param response
   * @throws UnsupportedEncodingException
   */
  public static void setFileNameHead(String fileName, HttpServletRequest request, HttpServletResponse response)
      throws UnsupportedEncodingException {
    String userAgentHeader = request.getHeader("User-Agent");
    if (userAgentHeader != null && userAgentHeader.toLowerCase().contains("msie")) {
      fileName = URLEncoder.encode(fileName, "UTF-8");
    } else {
      fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");// firefox浏览器
    }
    response.setContentType("application/octet-stream");
    response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
  }

  public static void httpDownloadCache(File file, WebRequest webRequest, HttpServletResponse response)
      throws IOException {
    // 实现页面级缓存，保证每一次访问都缓存，因为图片id是唯一的，图片不会修改，只会新增
    // 如果确实需要刷新客户端缓存，请在url后面加?最后修改时间
    if (webRequest.checkNotModified(CacheTool.getFixedLastModifyDate()))
      return;
    httpDownload(file, response);
  }

  public static void httpDownload(File file, HttpServletResponse response) throws IOException {
    InputStream is = new FileInputStream(file);
    ServletOutputStream os = response.getOutputStream();
    IOUtil.copy(is, os);
    is.close();
    os.close();
  }
}
