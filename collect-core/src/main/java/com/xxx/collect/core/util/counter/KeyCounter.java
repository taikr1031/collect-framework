package com.xxx.collect.core.util.counter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 一个map计数器
 */
public class KeyCounter<Key> {

  private List<KeyCount<Key>> list = new ArrayList<>();

  public int getCount(Key key) {
    KeyCount keyCount = getKeyCount(key);
    return keyCount == null ? 0 : keyCount.getCount();
  }

  public KeyCount<Key> getKeyCount(Key key) {
    int indexOf = list.indexOf(key);
    return indexOf > -1 ? list.get(indexOf) : null;
  }

  public int add(Key key) {
    KeyCount<Key> keyCount = this.getKeyCount(key);
    if (keyCount == null) {
      keyCount = new KeyCount<>(key);
      list.add(keyCount);
    }
    keyCount.add();
    return keyCount.getCount();
  }

  /**
   * 返回count最多的一个key
   *
   * @return
   */
  public KeyCount getMaxKeyCount() {
    list.sort(((o1, o2) -> o2.getCount() - o1.getCount()));
    return list.isEmpty() ? null : list.get(0);
  }

  /**
   * 迭代所有的计算器结果
   *
   * @param consumer
   */
  public void resultVisit(Consumer<KeyCount> consumer) {
    list.stream().forEach(key -> consumer.accept(key));
  }

}
