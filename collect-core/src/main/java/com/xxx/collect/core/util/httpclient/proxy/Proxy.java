package com.xxx.collect.core.util.httpclient.proxy;

import com.xxx.collect.core.db.dbmodel.ProxyInfo;
import com.xxx.collect.core.util.BeanUtil;
import com.xxx.collect.core.util.CheckUtil;
import com.xxx.collect.core.util.RegexUtil;

import java.util.List;

public class Proxy extends ProxyInfo {

  public static void main(String[] args) {
    Proxy proxy = new Proxy("111.111.11.11:9090");
    System.out.println(proxy.getIp() + " - " + proxy.getPort());
  }

  public static final int STATUS_ISNOTUSE = 0;
  public static final int STATUS_ISUSE = 1;


  public static final Integer LAST_USE = 1;

  private int succTotalCount = 0;
  private int failTotalCount = 0;

  private String userName;
  private String password;

  // 在程序中手工废除这个代理
  private boolean isUse = true;

  public boolean isUse() {
    return isUse;
  }

  public void setUse(boolean isUse) {
    this.isUse = isUse;
  }

  public Proxy(String ip, int port) {
    super();
    this.setIp(ip);
    this.setPort(port);
  }

  /**
   * ip:port的格式解析 ：
   * 60.217.36.62:25875
   *
   * @param ipport
   */
  public Proxy(String ipport) {
    List<String> strings = RegexUtil.searchBetweenList(ipport.trim(), "^(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d{1,5})$");
    CheckUtil.listSizeEqual(strings, 2, "代理ip格式不合法");
    this.setIp(strings.get(0));
    this.setPort(Integer.valueOf(strings.get(1)).intValue());
  }

  public Proxy(String ip, int port, String userName, String password) {
    this(ip, port);
    this.userName = userName;
    this.password = password;
  }

  public Proxy() {

  }

  public Proxy(ProxyInfo info) {
    BeanUtil.copyProperties(this, info);
  }


  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Proxy && obj != null) {
      Proxy proxy2 = (Proxy) obj;
      if (this.getIp().equals(proxy2.getIp()) && this.getPort() == proxy2.getPort()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return this.getIp() + ":" + this.getPort()
        + (this.getSeriesError() != null && this.getSeriesError() > 0 ? " - seriesError:" + this.getSeriesError() : "") + " "
        + (this.getSuccTotalCount() > 0 ? " succTotalCount=" + this.getSuccTotalCount() : "") + " "
        + (this.getFailTotalCount() > 0 ? " failTotalCount=" + this.getFailTotalCount() : "");
  }

  public void addSeriesError() {
    if (this.getSeriesError() == null)
      this.setSeriesError(0);
    this.setSeriesError(this.getSeriesError() + 1);
  }

  public void addSuccTotalCount() {
    this.succTotalCount++;
  }

  public void addFailTotalCount() {
    this.failTotalCount++;
  }

  public void clearSeriesError() {
    this.setSeriesError(0);
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getSuccTotalCount() {
    return succTotalCount;
  }

  public void setSuccTotalCount(int succTotalCount) {
    this.succTotalCount = succTotalCount;
  }

  public int getFailTotalCount() {
    return failTotalCount;
  }

  public void setFailTotalCount(int failTotalCount) {
    this.failTotalCount = failTotalCount;
  }
}
