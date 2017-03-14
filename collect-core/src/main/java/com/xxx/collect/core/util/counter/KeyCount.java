package com.xxx.collect.core.util.counter;

/**
 * Created by Tony on 2016/10/24.
 */
public class KeyCount<T>{

  public KeyCount(T key) {
    this.key = key;
  }

  private T key;
  private int count;

  public void add() {
    count++;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    KeyCount<?> keyCount = (KeyCount<?>) o;

    return key != null ? key.equals(keyCount.key) : keyCount.key == null;

  }

  @Override
  public int hashCode() {
    return key != null ? key.hashCode() : 0;
  }

  public T getKey() {
    return key;
  }

  public void setKey(T key) {
    this.key = key;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
