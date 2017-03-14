package com.xxx.collect.core.util;

/**
 * Created by luju on 2015-10-20.
 */
public class ColorUtil {
  public static void main(String[] args) {
    System.out.println(1%5);
  }

  static String[] colorArr = {"Black", "Red", "Blue", "Maroon", "Green", "Purple", "Navy", "maroon", "navy", "indigo", "purple", "chocolate"};

  public static String getRandColor(int randSeed) {
    return colorArr[randSeed % colorArr.length];
  }

}
