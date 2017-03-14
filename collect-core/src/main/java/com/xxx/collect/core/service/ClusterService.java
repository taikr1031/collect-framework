package com.xxx.collect.core.service;

import com.xxx.collect.core.config.ClusterAppConfig;
import com.xxx.collect.core.util.thread.ThreadUtil;
import com.gei.framework.web.filter.VariableInitFilter;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 集群环境，每个app的配置
 * Created by Tony on 2016/8/25.
 */
@Service
public class ClusterService {

  private static final Log log = LogFactory.getLog(ClusterService.class);

  private static ClusterAppConfig clusterAppConfig;

  private static String configXml = "ClusterApp";

  /**
   * 初始化集群配置参数，为了实现一台机器配置多个app，使用文件名-端口.xml的名字来配置
   * 此方法调用前必须先获取app的端口
   */
  public void initConfig(File geiWorkDir) {
    Integer myServerPort = VariableInitFilter.MY_SERVER_PORT;
    if (myServerPort == null)
      throw new RuntimeException("myServerPort未初始化");
    XStream xstream = new XStream();
    xstream.alias("config", ClusterAppConfig.class);
    InputStream is;
    try {
      is = new FileInputStream(new File(geiWorkDir, configXml + "-" + myServerPort + ".xml"));
    } catch (FileNotFoundException e) {
      log.error(e, e);
      throw new RuntimeException(e);
    }
    clusterAppConfig = (ClusterAppConfig) xstream.fromXML(is);
    log.info("加载集群配置参数：TaskDelay=" + clusterAppConfig.getTaskDelay());
  }

  public ClusterAppConfig getAppConfig() {
    if (clusterAppConfig == null) {
      throw new RuntimeException("集群配置config未初始化!");
    }
    return clusterAppConfig;
  }

  /**
   * 根据配置进行delay
   */
  public void theadDelay() {
    int taskDelay = this.getAppConfig().getTaskDelay();
    if (taskDelay > 0) {
      log.info("集群delay:" + taskDelay + "秒");
      ThreadUtil.sleepSeccond(taskDelay);
    }
  }

  public boolean isScheduledApp() {
    return this.getAppConfig().isScheduledApp();
  }

}
