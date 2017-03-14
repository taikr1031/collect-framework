package com.xxx.collect.core.model.tree;


public abstract class TreeNodeVisitor<T extends TreeNode> {
  /**
   * 递归接口，按深度遍历
   *          遍历到的cat
   * @return true继续递归 false递归终止
   */
  abstract public void visit(T node);

  private boolean isOver;

  public boolean isOver() {
    return isOver;
  }

  public void setOver(boolean isOver) {
    this.isOver = isOver;
  }
}
