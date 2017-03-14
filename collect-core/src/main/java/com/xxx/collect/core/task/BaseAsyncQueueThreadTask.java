package com.xxx.collect.core.task;

import com.xxx.collect.core.service.BaseAsyncQueueService;
import com.xxx.collect.core.util.thread.ThreadUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;

/**
 * 异步消费处理task,并有统计功能
 */
public abstract class BaseAsyncQueueThreadTask extends BaseAsyncCounter {

  /**
   * 是否处理
   *
   * @return
   */
  protected abstract boolean isProc();

  protected abstract BaseAsyncQueueService getAsyncQueueService();

  public int getSize(){
    return this.getAsyncQueueService().getSize();
  }

  /**
   * 开启线程数量
   *
   * @return
   */
  public int getThreadNum() {
    return 1;
  }

  private static final Log log = LogFactory.getLog(BaseAsyncQueueThreadTask.class);


  /**
   * 改成非定时器模式，因为当一次任务没有执行完成，会有多次任务重复执行，最终会导致任务阻塞
   */
  @PostConstruct
  public void task() {
    for (int i = 0; i < getThreadNum(); i++) {
      new TaskThread().start();
    }
  }

  class TaskThread extends Thread {

    @Override
    public void run() {
      while (true) {
        try {
          Object bean = getAsyncQueueService().poll();
          if (!isProc())
            return;
          if (bean == null) {
            // 如果是空，则休息2秒
            ThreadUtil.sleepSeccond(2);
          } else {
//            log.debug("异步处理：" + getAsyncQueueService().getSize() + " - " + BeanUtil.toString(bean));
            boolean rest = getAsyncQueueService().asyncProc(bean);
            if (rest)
              addSuccessCount();
            else
              addErrorCount();
          }
        } catch (Exception e) {
          log.error(e, e);
          addErrorCount();
        }
      }
    }

  }

}
