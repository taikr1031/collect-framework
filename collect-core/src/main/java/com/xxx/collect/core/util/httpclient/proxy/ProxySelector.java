package com.xxx.collect.core.util.httpclient.proxy;

import com.xxx.collect.core.util.RegexUtil;
import com.xxx.collect.core.util.date.DateCalcUtil;
import com.xxx.collect.core.util.httpclient.impl.HttpClientCommon;
import com.xxx.collect.core.util.httpclient.model.HttpResult;
import com.xxx.collect.core.util.io.IOFileUtil;
import com.xxx.collect.core.util.log.LogCatalog;
import com.xxx.collect.core.util.thread.ThreadUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 从本地文件取代理IP
 */
@Service
public class ProxySelector {
  public static void main(String[] args) {
    Proxy proxy = new Proxy("123", 123);
    List<Proxy> list = new ArrayList<Proxy>();
    list.add(proxy);
    System.out.println(list.contains(new Proxy("123", 1231)));
  }

  @Autowired
  private ProxyDao proxyDao;

  private List<Proxy> proxyList;// 静态代理列表
  private String filePath = "E:\\gei-work\\Spider\\proxy\\proxy.txt";
  private int maxSeriesError = 1;// 允许最大失败次数，失败后则剔除队列
  private Logger log = LogCatalog.httpProxy;
  private int index;
  /**
   * 代理池中的代理数量，如果低于这个数量，则自动去网上拿
   */
  private int porxyPoolLength = 50;
  /**
   * 每次从网上查询的数量,如果数据库里面没有保存，则这个值应该和 代理池要求的值一致
   */
  private int netGetOnceNum = 30;
  // 设置静态线程代理注册，保证一个线程始终只用一个代理
  private Map<String, Proxy> threadProxy = new HashMap<String, Proxy>();

  private ProxySelector() {

  }

  private List<Proxy> getProxyList() {
    synchronized (syncLock) {
      if (proxyList == null) {
        proxyList = new ArrayList<Proxy>();
        Collections.synchronizedCollection(proxyList);
        initProxyList();
      }
      return proxyList;
    }
  }

  private static boolean isFirstScheduledSaveCurrentProxyListToDb = true;

  /**
   * syncLock,同步锁
   */
  private static Object syncLock = new Object();

  /**
   * 定时把当前正在使用的代理信息存到数据库，下次可以直接加载使用
   * 
   * @Scheduled(fixedRate = 300 * 1000) public void
   *                      scheduledSaveCurrentProxyListToDb() { synchronized
   *                      (syncLock) { if
   *                      (isFirstScheduledSaveCurrentProxyListToDb) {
   *                      isFirstScheduledSaveCurrentProxyListToDb = false;
   *                      return; } log.debug("保存当前使用的代理队列到数据库:开始!");
   *                      this.proxyDao
   *                      .saveCurrentProxyListToDb(getProxyList());
   *                      log.debug("保存当前使用的代理队列到数据库:完成!"); } }
   */

  /**
   * 向代理队列添加可用代理
   */
  public void addProxy(Proxy proxy) {
    synchronized (syncLock) {
      if (!getProxyList().contains(proxy)) {
        getProxyList().add(proxy);
        // 存入数据库
        proxy.setStatus(Proxy.STATUS_ISUSE);
        this.proxyDao.save(proxy);
        //log.debug("补充代理：" + proxy+ " 当前代理队列数：" + getProxyList().size());
      }
    }
  }

  private void initProxyList() {
    synchronized (syncLock) {
      log.debug("初始加载代理列表：");
      // 从数据库中取上次正在使用的代理
      List<Proxy> lastUserProxyList = proxyDao.listUsed();
      log.debug("取得上次使用的代理:" + lastUserProxyList.size() + "个");
      for (Proxy proxy : lastUserProxyList) {
        if (!proxyList.contains(proxy)) {
          log.debug("添加代理：" + proxy);
          proxyList.add(proxy);
        }
      }
      // 加载文件中新增的代理
      // boolean haveFileProxyAddToDb = false;
      if ((new File(filePath)).exists()) {
        String string = IOFileUtil.loadFileToString(filePath);
        List<Proxy> list = parseProxyFromFile(string);
        for (Proxy proxy : list) {
          if (!proxyList.contains(proxy)) {
            proxyList.add(proxy);
            //log.debug("添加代理：" + proxy.toString());
            this.proxyDao.save(proxy);
            // haveFileProxyAddToDb = true;
          }
        }
      }
      // if (haveFileProxyAddToDb)
      // scheduledSaveCurrentProxyListToDb();
      log.debug("代理列表总数：" + proxyList.size());
    }
  }

  private List<Proxy> parseProxyFromFile(String text) {
    synchronized (syncLock) {
      List<Proxy> listProxy = new ArrayList<Proxy>();
      List<String> list = RegexUtil.searchList(text, ".+\\:\\d+");
      for (String string2 : list) {
        String[] strings = string2.split(":");
        Proxy proxy = new Proxy(strings[0], Integer.valueOf(strings[1]));
        if (!listProxy.contains(proxy)) {
          listProxy.add(proxy);
        }
      }
      return listProxy;
    }
  }

  public Proxy nextProxy() {
    synchronized (syncLock) {
      Proxy proxy = null;
      // 每个线程使用固定一个proxy
      String threadName = Thread.currentThread().getName();
      Proxy threadOldProxy = threadProxy.get(threadName);// 如果此线程已经注册了proxy则返回固定的
      if (threadOldProxy != null && threadOldProxy.isUse()) {
        proxy = threadOldProxy;
      } else {// 在代理队列中取代理
        proxy = getNextProxyFromProxyList();
        this.threadProxy.put(threadName, proxy);
      }
      // 如果失败次数过多，则删除掉此代理，不再使用
      if ((proxy.getSeriesError() == null ? 0 : proxy.getSeriesError()) >= this.maxSeriesError) {
        removeProxy(proxy);
        return nextProxy();
      }
      return proxy;
    }
  }

  /**
   * 将不用的代理从队列和线程代理池中移除
   */
  private void removeProxy(Proxy proxy) {
    synchronized (syncLock) {
      // 删除线程池
      List<String> delList = new ArrayList<String>();
      for (String key : this.threadProxy.keySet()) {
        if (proxy.equals(this.threadProxy.get(key))) {
          delList.add(key);
        }
      }
      for (String key : delList) {
        this.threadProxy.remove(key);
      }
      // 删除队列
      getProxyList().remove(proxy);
      this.proxyDao.updateProxyNotUse(proxy);
      //this.log.debug("连续失败次数超过" + this.maxSeriesError + ",删除此代理：" + proxy.toString()+"   当前代理队列数量：" + getProxyList().size());
    }
  }

  /**
   * 在代理队列中取代理
   */
  private Proxy getNextProxyFromProxyList() {
    synchronized (syncLock) {
      if (getProxyList() == null || getProxyList().size() < porxyPoolLength) {
        log.debug("代理列表数量为" + getProxyList().size() + "，重新补充代理！");
        addProxyFromNet();
      }
      if ((index + 1) > getProxyList().size()) {
        index = 0;
      }
      Proxy proxy = getProxyList().get(index);
      index++;
      return proxy;
    }
  }

  private static Date last_add_from_net = new Date();

  /**
   * 从网上填充代理ip
   */
  public void addProxyFromNet() {
    synchronized (syncLock) {
      log.debug("从网上查询填充代理ip！开始");
      //小舒
      String url = "http://www.71https.com/api.asp?key=20141012171029688&getnum=" + netGetOnceNum + "&filter=1&area=1&proxytype=0";

      //快代理
      //String url = "http://svip.kuaidaili.com/api/getproxy/?orderid=914677918397137&num="+netGetOnceNum+"&protocol=1&method=1&quality=2&sort=2&dedup=1&format=text&sep=1";
      HttpClientCommon client = new HttpClientCommon();
      try {
        HttpResult resp = client.visit(url);
        String html = resp.getHtml();
        List<Proxy> proxyListFromNet = parseProxyFromFile(html);
        for (Proxy proxy : proxyListFromNet) {
          this.addProxy(proxy);
        }
        log.debug("从网上查询填充代理ip！ 完成：填充数量:" + proxyListFromNet.size()+"  当前代理队列数量:" + this.getProxyList().size());
        // 如果间隔小于5秒，则sleep
        if (DateCalcUtil.betweenSecond(last_add_from_net, new Date()) <= 5) {
          ThreadUtil.sleepSeccond(5);
        }
        last_add_from_net = new Date();
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
