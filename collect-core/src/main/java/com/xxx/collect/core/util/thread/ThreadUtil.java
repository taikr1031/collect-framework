package com.xxx.collect.core.util.thread;

public class ThreadUtil {
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void sleepSeccond(int seccond) {
		try {
			Thread.sleep(seccond*1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}


	public static void waitThis() {
		try {
			Thread.currentThread().wait();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
