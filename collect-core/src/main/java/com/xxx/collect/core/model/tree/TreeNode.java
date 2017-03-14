package com.xxx.collect.core.model.tree;

import java.util.List;

public class TreeNode {

  private TreeNode parent;

  private List<TreeNode> children;

  /**
   * 遍历cat的接口
   *
   * @param visitor
   */
  public void visitParent(TreeNodeVisitor visitor) {
    visitParent(this, visitor);
  }

  private void visitParent(TreeNode node, TreeNodeVisitor visitor) {
    if (visitor.isOver())
      return;
    visitor.visit(node);
    if (node.getParent() == null)
      return;
    visitParent(node.getParent(), visitor);
  }

  /**
   * 遍历cat的接口
   *
   * @param visitor
   */
  public void visitChild(TreeNodeVisitor<TreeNode> visitor) {
    visitChild(this, visitor);
  }

  private void visitChild(TreeNode node, TreeNodeVisitor<TreeNode> visitor) {
    if (visitor.isOver())
      return;
    visitor.visit(node);
    if (node.children == null)
      return;
    for (TreeNode child : node.children) {
      visitChild(child, visitor);
    }
  }

  public TreeNode getParent() {
    return parent;
  }

  public void setParent(TreeNode parent) {
    this.parent = parent;
  }
}
