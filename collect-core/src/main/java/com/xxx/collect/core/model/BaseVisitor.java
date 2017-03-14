package com.xxx.collect.core.model;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseVisitor<T> {
  /**
   * 递归接口，按深度遍历
   *
   *          遍历到的cat
   * @return true继续递归 false递归终止
   */
  abstract public boolean visit(T t);
  
  /**
   * 在遍历完所有的子节点后调用，可用于删除子节点
   * @param t
   */
  public void visitChildrenOver(T t){
    
  };
  
  /**
   * 在开始调用下级节点时调用
   * @param t
   */
  public void visitChildrenLevelStart(T t){
    
  }

  private Map<String, Object> params = new HashMap<String, Object>();
  private Map<String, Object> rests = new HashMap<String, Object>();

  public Map<String, Object> getParams() {
    return params;
  }

  public void setParams(Map<String, Object> params) {
    this.params = params;
  }

  public Map<String, Object> getRests() {
    return rests;
  }

  public void setRests(Map<String, Object> rests) {
    this.rests = rests;
  }
}
