package com.xxx.collect.core.tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Tony on 2016/4/6.
 */
@Component
public class DbTool {

  private static final Log log = LogFactory.getLog(DbTool.class);

  /**
   * 主站服务器
   */
  @Value("#{db['db.url']}")
  private String dbUrl;

  @PostConstruct
  public void init() {
    log.info("当前数据库:" + dbUrl);
  }


}
