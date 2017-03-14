package com.xxx.collect.base.tool.util.analysisExtName;

public class ExtNameCounter implements Comparable<ExtNameCounter> {

  @Override
  public int compareTo(ExtNameCounter o) {
    return o.count - this.count;
  }

  public ExtNameCounter(String extName, int count) {
    super();
    this.extName = extName;
    this.count = count;
  }

  private String extName;
  private int count;

  public String getExtName() {
    return extName;
  }

  public void setExtName(String extName) {
    this.extName = extName;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

}
