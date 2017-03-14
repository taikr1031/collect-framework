package com.xxx.collect.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

/**
 * bean工厂，可以用名字拿bean
 * Created by luju on 2015-09-01.
 */
@Service
public class BeanFactoryUtil implements BeanFactoryAware {

  private BeanFactory beanFactory;

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }


  /**
   * 从当前IOC获取bean
   *
   * @param id bean的id
   * @return
   */
  public Object getBean(String id) {
    return beanFactory.getBean(id);
  }


  /**
   * 从当前IOC获取bean
   *
   * @return
   */
  public <T> T getBean(Class<T> var1) {
    return beanFactory.getBean(var1);
  }

  public <T> T getBean(String name, Class<T> cls) {
    return this.beanFactory.getBean(name, cls);
  }
}
