package com.xxx.collect.core.tool;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBeanUtil {
  public static ClassPathXmlApplicationContext getBeanFactoryWithLocal() {
    String[] locations = {"classpath*:com/gei/resources/applicationContext-*.xml" };
    ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext(locations);
    return beanFactory;
  }

  public static ClassPathXmlApplicationContext getBeanFactoryWithLocalSprider() {
    String[] locations = {"classpath*:com/gei/resources/applicationContext-*.xml"};
    ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext(locations);
    return beanFactory;
  }

  public static ClassPathXmlApplicationContext getBeanFactory() {
    String[] locations = {"classpath*:com/gei/resources/applicationContext-*.xml"};
    ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext(locations);
    return beanFactory;
  }
}
