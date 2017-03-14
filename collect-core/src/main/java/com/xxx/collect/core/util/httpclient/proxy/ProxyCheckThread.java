package com.xxx.collect.core.util.httpclient.proxy;

import com.xxx.collect.core.util.log.LogCatalog;
import com.xxx.collect.core.util.thread.ThreadUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProxyCheckThread extends Thread {
  public static void main(String[] args) {
    // 初始化总表 List<Proxy> proxyAllList =
    // this.proxyDao.listUsed();
    // log.debug("总检查代理：" + proxyAllList.size());
    ProxyCheckThread thread = new ProxyCheckThread();
    thread.start();
  }

  @Autowired
  private ProxyDao proxyDao;

  private int CHECK_DURING = 2;// 检查一个代理中的间隔时间，秒
  private List<Proxy> allProxyList;// 总代理表
  private static Logger log = LogCatalog.httpProxyCheckThread;
  private int index; // 检查指针
  /*
   * public ProxyCheckThread() { }
   */
  ProxyCheck proxyCheck = new ProxyCheck();

  @Autowired
  private ProxySelector proxySelector;

  @Override
  public void run() {
    while (true) {
      if ((index + 1) > this.allProxyList.size()) {
        index = 0;
      }
      Proxy proxy = this.allProxyList.get(index);
      index++;
      boolean isValidate = proxyCheck.vailidate(proxy);
      if (isValidate) {
        proxySelector.addProxy(proxy);
      }
      ThreadUtil.sleepSeccond(CHECK_DURING);// 间隔2秒检查一次代理
    }
  }
}
