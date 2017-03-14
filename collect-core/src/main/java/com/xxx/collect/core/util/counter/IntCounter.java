package com.xxx.collect.core.util.counter;

/**
 * Created by Tony on 2016/8/8.
 */
public class IntCounter {



  private int num=0;
  public void addOne(){
    num++;
  }

  public int getNum() {
    return num;
  }

  public boolean isModEq1(int mod){
    return this.getNum()%mod ==1;
  }

  public void setNum(int num) {
    this.num = num;
  }
}
