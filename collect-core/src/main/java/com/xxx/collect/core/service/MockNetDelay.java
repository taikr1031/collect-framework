package com.xxx.collect.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MockNetDelay {

	@Value("#{site['debug.mockNetDelay']}")
	private boolean isMockNetDelay;

	public void mockNetDelay1() {
		this.mockNetDelay(1);
	}

	public void mockNetDelay2() {
		this.mockNetDelay(2);
	}

	public void mockNetDelay3() {
		this.mockNetDelay(3);
	}

	public void mockNetDelay(int m) {
		if (this.isMockNetDelay) {
			try {
				Thread.sleep(m * 1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
