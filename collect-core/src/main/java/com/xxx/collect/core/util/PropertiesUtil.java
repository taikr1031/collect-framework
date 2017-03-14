package com.xxx.collect.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by luju on 2015-11-03.
 */
public class PropertiesUtil {

  public static Properties read(String path) {
    InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(path);
    Properties p = new Properties();
    try {
      p.load(inputStream);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    return p;
  }

}
