package com.xxx.collect.core.util.math;

import java.util.Random;

public class RandomUtils {
  public static void main(String[] args) {
    for (int i=0;i<1000;i++) {

    System.out.println(nextInt(10));
    }
  }

  public static final Random JVM_RANDOM = new JVMRandom();

  public static boolean nextBoolean() {
    return nextBoolean(JVM_RANDOM);
  }

  public static boolean nextBoolean(Random random) {
    return random.nextBoolean();
  }

  public static double nextDouble() {
    return nextDouble(JVM_RANDOM);
  }

  public static double nextDouble(Random random) {
    return random.nextDouble();
  }

  public static float nextFloat() {
    return nextFloat(JVM_RANDOM);
  }

  public static float nextFloat(Random random) {
    return random.nextFloat();
  }

  public static int nextInt() {
    return nextInt(JVM_RANDOM);
  }

  /**
   * 0-n之前的随机数,>=0，<n
   * @param n
   * @return
   */
  public static int nextInt(int n) {
    return nextInt(JVM_RANDOM, n);
  }

  public static int nextInt(Random random) {
    return random.nextInt();
  }

  public static int nextInt(Random random, int n) {
    return random.nextInt(n);
  }

  public static long nextLong() {
    return nextLong(JVM_RANDOM);
  }

  public static long nextLong(Random random) {
    return random.nextLong();
  }
}
