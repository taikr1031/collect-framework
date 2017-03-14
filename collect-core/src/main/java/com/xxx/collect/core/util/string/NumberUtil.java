package com.xxx.collect.core.util.string;

import java.text.NumberFormat;

public class NumberUtil {

  public static String format(Object x, int maxFraction) {
    NumberFormat ddf1 = NumberFormat.getNumberInstance();
    ddf1.setMaximumFractionDigits(maxFraction);
    String s = ddf1.format(x);
    s = s.replace(",","");
    return s;
  }

  public static Integer intNullToZero(Integer i) {
    return i == null ? 0 : i;
  }
}
