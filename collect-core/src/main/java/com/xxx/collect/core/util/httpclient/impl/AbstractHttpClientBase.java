package com.xxx.collect.core.util.httpclient.impl;

import com.xxx.collect.core.tool.DeleteFileThread;
import com.xxx.collect.core.util.RegexUtil;
import com.xxx.collect.core.util.htmlparse.HtmlParser;
import com.xxx.collect.core.util.httpclient.IHttpClient;
import com.xxx.collect.core.util.httpclient.model.HttpClientConfig;
import com.xxx.collect.core.util.httpclient.model.HttpResult;
import com.xxx.collect.core.util.httpclient.proxy.Proxy;
import com.xxx.collect.core.util.io.IOUtil;
import com.xxx.collect.core.util.log.LogCatalog;
import com.xxx.collect.core.util.string.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.*;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.htmlparser.Tag;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * 使用同一个HttpClient对象，反复使用
 */
public abstract class AbstractHttpClientBase implements IHttpClient {
  private static final Logger log = LogCatalog.httpLog;

  private static final Log log2 = LogFactory.getLog(AbstractHttpClientBase.class);

  /**
   * 每一次都必须new一个新的，否则有问题
   */
  // private HttpClient getHttpClient() {
  // HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
  // HttpClient httpClient = httpClientBuilder.build();
  // return httpClient;
  // }
  private HttpClient getHttpClient(HttpClientConfig httpClientConfig) {

    HttpClientBuilder builder = HttpClients.custom();

    // 设置默认header
    String agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36";
    httpClientConfig.getHeaders().add(new BasicHeader(HttpHeaders.USER_AGENT, agent));
    builder.setDefaultHeaders(httpClientConfig.getHeaders());

    CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
      public CookieSpec create(HttpContext context) {
        return new BrowserCompatSpec() {
          @Override
          public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
          }
        };
      }
    };
    Registry<CookieSpecProvider> r = RegistryBuilder.<CookieSpecProvider>create()
        .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
        .register(CookieSpecs.BROWSER_COMPATIBILITY, new BrowserCompatSpecFactory()).register("easy", easySpecProvider)
        .build();
    builder.setDefaultCookieSpecRegistry(r);

    // 手工指定cookie字符串
    if (httpClientConfig.getCookie() != null) {
      //log2.debug("使用cookie:"+httpClientConfig.getCookie());
      httpClientConfig.getHeaders().add(new BasicHeader("Cookie", httpClientConfig.getCookie()));
    }
    // 设置cookieStore
    else {
      if (httpClientConfig.getCookieStore() == null) {
        httpClientConfig.setCookieStore(new BasicCookieStore());
      }
      builder.setDefaultCookieStore(httpClientConfig.getCookieStore());
    }

    // 设置代理
    Proxy configProxy = httpClientConfig.getProxy();
    if (configProxy != null) {
      HttpHost proxy = new HttpHost(configProxy.getIp(), configProxy.getPort(), "http");
      builder.setProxy(proxy);
      if (StringUtil.isNotBlank(configProxy.getUserName())) {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
            new AuthScope(configProxy.getIp(), configProxy.getPort()),
            new UsernamePasswordCredentials(configProxy.getUserName(), configProxy.getPassword()));
        builder.setDefaultCredentialsProvider(credsProvider);
      }
    }

    // 设置超时
    int timeOut = httpClientConfig.getTimeOut() == null ? 1000 * 120 : httpClientConfig.getTimeOut();
    RequestConfig requestConfig = RequestConfig.custom().setCookieSpec("easy").setConnectionRequestTimeout(timeOut)
        .setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();
    builder.setDefaultRequestConfig(requestConfig);

    builder.setRedirectStrategy(new DefaultRedirectStrategy() {
      public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) {
        // 这里取消了301个302的自动跳转,如果返回false，则可以自动跳转
        boolean isRedirect = false;
        try {
          isRedirect = super.isRedirected(request, response, context);
        } catch (ProtocolException e) {
          throw new RuntimeException(e);
        }
        if (isRedirect) {
          int responseCode = response.getStatusLine().getStatusCode();
          if (responseCode == 301 || responseCode == 302) {
            return false;
          }
        }
        return isRedirect;
      }
    });
    CloseableHttpClient httpclient = builder.build();
    return httpclient;
  }

  private void setRequestConfig(HttpRequestBase request, HttpClientConfig httpClientConfig) {
    // header
  }

  /**
   * 每次访问完成后，根据response添加config设置，cookie、encode等
   */
  private void responseSetConfig(HttpResponse clientResponse, HttpClientConfig httpClientConfig) {
    // HttpEntity entity = clientResponse.getEntity();
    Header[] allHeaders = clientResponse.getAllHeaders();
    String encoding = httpClientConfig.getEncoding();
    encoding = encoding == null ? "UTF-8" : encoding;// 默认字符集
    for (Header header : allHeaders) {
      // String name = header.getName();
      String value = header.getValue();
      // 设置字符集
      String charsetFlag = "charset=";
      int charsetIndex = value.indexOf(charsetFlag);
      if (charsetIndex >= 0) {
        String realEncoding = value.substring(charsetIndex + charsetFlag.length());
        encoding = realEncoding;
      }
    }
    httpClientConfig.setEncoding(encoding);
  }

  /**
   * 获得HttpResponse信息
   */
  private HttpResult executeAndGetHttpResponse(HttpRequestBase request, HttpClientConfig httpClientConfig)
      throws IOException {
    setRequestConfig(request, httpClientConfig);
    String url = request.getURI().toString();
    HttpClient httpClient = getHttpClient(httpClientConfig);
    HttpClientContext context = new HttpClientContext();
    HttpResponse clientResponse = httpClient.execute(request, context);
    responseSetConfig(clientResponse, httpClientConfig);
    return getResult(url, clientResponse, httpClientConfig, context);
  }

  private HttpResult getResult(String url, HttpResponse clientResponse, HttpClientConfig httpClientConfig,
                               HttpClientContext context) throws IllegalStateException, IOException {
    String logStr = "";// 记录每一次的信息
    logStr = url;
    logStr += " 线程[" + Thread.currentThread().getName() + "]";
    logStr += " - 字符集[" + httpClientConfig.getEncoding() + "]";
    logStr += " - 代理[" + httpClientConfig.getProxy() + "]";
    if (httpClientConfig.getCookieStore() != null)
      logStr += " Cookie[" + httpClientConfig.getCookieStore().toString() + "]";
    if (httpClientConfig.getCookie() != null)
      logStr += " Cookie[" + httpClientConfig.getCookie() + "]";
    logStr = "【成功】 " + logStr;
    log.debug(logStr);

    HttpResult result = new HttpResult();
    result.setLogInfo(logStr);
    CookieStore cookieStore = context.getCookieStore();
    result.setCookieStore(cookieStore);
    HttpEntity entity = clientResponse.getEntity();
    // String html = EntityUtils.toString(entity);
    InputStream inputStream = entity.getContent();
    byte[] bs = IOUtil.inputStream2Bytes(inputStream);
    String encoding = httpClientConfig.getEncoding();
    String html = new String(bs, encoding);
    //不管之前设置的编码是什么，这里都已网页中<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">的编码为准
    List<Tag> metaList = HtmlParser.getParser(html).tag("meta");
    for (Tag metaTag : metaList) {
      String httpEquiv = metaTag.getAttribute("http-equiv");
      if (httpEquiv == null || !"content-type".equals(httpEquiv.toLowerCase()))
        continue;
      String content = metaTag.getAttribute("content");
      String htmlEncoding = RegexUtil.searchBetween(content, "charset=(\\S+)");
      if (htmlEncoding != null && !encoding.equals(htmlEncoding)) {
        encoding = htmlEncoding;
        if (encoding.trim().equals("gbk2312"))
          encoding = "gb2312";
        html = new String(bs, encoding);
      }
    }
    result.setEncoding(encoding);
    result.setHtml(html);
    result.setBytes(bs);
    result.setStatus(clientResponse.getStatusLine().getStatusCode());
    result.setUrl(url);
    result.setCookieStore(httpClientConfig.getCookieStore());
    result.setProxy(httpClientConfig.getProxy());
    return result;
  }

  public HttpEntity getInputStream(String url, HttpClientConfig httpClientConfig) throws IOException {
    // String encodeUrl = URLEncoder.encode(url, "utf-8");
    HttpGet httpGet = new HttpGet(markUrl(url));
    setRequestConfig(httpGet, httpClientConfig);
    HttpResponse clientResponse = getHttpClient(httpClientConfig).execute(httpGet);
    HttpEntity entity = clientResponse.getEntity();
    return entity;
  }

  public HttpResponse getHttpResponse(String url, HttpClientConfig httpClientConfig) throws IOException {
    // String encodeUrl = URLEncoder.encode(url, "utf-8");
    HttpGet httpGet = new HttpGet(markUrl(url));
    setRequestConfig(httpGet, httpClientConfig);
    HttpResponse clientResponse = getHttpClient(httpClientConfig).execute(httpGet);
    return clientResponse;
  }

  public HttpResponse getHttpResonse(String url, HttpClientConfig httpClientConfig) throws IOException {
    HttpGet request = new HttpGet(markUrl(url));
    setRequestConfig(request, httpClientConfig);
    request.getURI().toString();
    HttpClient httpClient = getHttpClient(httpClientConfig);
    HttpClientContext context = new HttpClientContext();
    HttpResponse clientResponse = httpClient.execute(request, context);
    responseSetConfig(clientResponse, httpClientConfig);
    return clientResponse;
  }

  /**
   * 下载先进临时目录，然后再拷贝到正式文件，避免只下载一般的文件出现
   *
   * @param localFile
   * @param url
   * @param httpClientConfig
   * @throws IOException
   */
  public void download(File localFile, String url, HttpClientConfig httpClientConfig) throws IOException {
    File tempFile = File.createTempFile("httpDownTempFile_", localFile.getName());
    HttpGet httpGet = new HttpGet(markUrl(url));
    setRequestConfig(httpGet, httpClientConfig);
    try {
      HttpResponse clientResponse = getHttpClient(httpClientConfig).execute(httpGet);
      HttpEntity entity = clientResponse.getEntity();
      InputStream inputStream = entity.getContent();
      FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
      IOUtil.copy(inputStream, fileOutputStream);
      fileOutputStream.close();
      if (localFile.exists()) {
        log2.debug("http下载文件，发现文件已存在，删除:" + localFile.getAbsolutePath());
        localFile.delete();
      }
      if (!localFile.getParentFile().exists())
        localFile.getParentFile().mkdirs();
      IOUtil.copyFile(tempFile, localFile);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      DeleteFileThread.deleteFile(tempFile);
    }
  }

  public HttpResult post(String url, Map<String, String> parm, HttpClientConfig httpClientConfig) throws IOException {
    HttpPost httpRequest = new HttpPost(markUrl(url));
    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
    // 添加post参数
    Set<String> parmSet = parm.keySet();
    for (String key : parmSet) {
      formparams.add(new BasicNameValuePair(key, parm.get(key)));
    }
    // 创建参数队列
    // 参数名为pid，值是2
    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
    httpRequest.setEntity(entity);
    HttpResult httpResponse = this.executeAndGetHttpResponse(httpRequest, httpClientConfig);
    return httpResponse;
  }

  /**
   * post 文本
   *
   * @param url
   * @param content
   * @param httpClientConfig
   * @return
   * @throws IOException
   */
  public HttpResult postStr(String url, String content, HttpClientConfig httpClientConfig) throws IOException {
    HttpPost httpRequest = new HttpPost(markUrl(url));
    StringEntity se = new StringEntity(content);
    httpRequest.setEntity(se);
    HttpResult httpResponse = this.executeAndGetHttpResponse(httpRequest, httpClientConfig);
    return httpResponse;
  }


  /**
   * upload
   */
  public HttpResult upload(String url, InputStream inputStream, HttpClientConfig httpClientConfig) throws IOException {
    HttpPost httpRequest = new HttpPost(markUrl(url));
    InputStreamEntity inputStreamEntity = new InputStreamEntity(inputStream);
    httpRequest.setEntity(inputStreamEntity);
    HttpResult httpResponse = this.executeAndGetHttpResponse(httpRequest, httpClientConfig);
    return httpResponse;
  }

  public HttpResult visit(String url, HttpClientConfig httpClientConfig) throws IOException {
    HttpGet httpGet = new HttpGet(markUrl(url));
    HttpResult httpResponse = this.executeAndGetHttpResponse(httpGet, httpClientConfig);
    return httpResponse;
  }

  public static String markUrl(String url) {
    return url.replace(" ", "%20").replace("[", "%5b").replace("]", "%5d");
  }

}
