package com.xxx.collect.core.model;

public class Dimension extends java.awt.Dimension {

  private static final long serialVersionUID = 1L;

  public Dimension() {
  }

  public Dimension(Dimension d) {
    this(d.width, d.height);
  }

  public Dimension(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public Integer getHeightInt() {
    return (int) super.getHeight();
  }

  public Integer getWidthInt() {
    return (int) super.getWidth();
  }
}
