package com.xxx.collect.core.util.httpclient.proxy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxx.collect.core.db.dbmapper.ProxyInfoMapper;
import com.xxx.collect.core.db.dbmodel.ProxyInfo;
import com.xxx.collect.core.db.dbmodel.ProxyInfoExample;

@Service
public class ProxyDao {

  @Autowired
  private ProxyInfoMapper proxyInfoMapper;

  public List<Proxy> listUsed() {
    ProxyInfoExample exp = new ProxyInfoExample();
    exp.createCriteria().andStatusEqualTo(Proxy.STATUS_ISUSE);
    List<Proxy> list = new ArrayList<Proxy>();
    List<ProxyInfo> list2 = this.proxyInfoMapper.selectByExample(exp);
    for (ProxyInfo proxyInfo : list2) {
      list.add(new Proxy(proxyInfo));
    }
    return list;
  }

  /*
  public void saveCurrentProxyListToDb(List<Proxy> proxyList) {
    Iterator<Proxy> iterator = proxyList.iterator();
    while (iterator.hasNext()) {
      Proxy proxy = iterator.next();
      proxy.setStatus(Proxy.STATUS_ISUSE);
      save(proxy);
    }
  }*/

  public void updateProxyNotUse(Proxy proxy) {
    // 首先全部更新为没有使用
    proxy.setStatus(Proxy.STATUS_ISNOTUSE);
    this.proxyInfoMapper.updateByPrimaryKeySelective(proxy);
  }

  public void save(Proxy proxy) {
    ProxyInfo proxyInfo = this.proxyInfoMapper.selectByPrimaryKey(proxy);
    if (proxyInfo != null)
      this.proxyInfoMapper.updateByPrimaryKey(proxy);
    else {
      if (proxy.getStatus() == null)
        proxy.setStatus(Proxy.STATUS_ISNOTUSE);
      this.proxyInfoMapper.insert(proxy);
    }
  }

}
