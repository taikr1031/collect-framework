package com.xxx.collect.core.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CacheSimplyService  {

	private static Map<String, Object> STATIC_MAP = new HashMap<String, Object>();

	public void delete(String key) {
		STATIC_MAP.remove(key);
	}

	public void set(String key, Object value) {
		STATIC_MAP.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) STATIC_MAP.get(key);
	}

	public long decr(String key, long value) {
		Object object = STATIC_MAP.get(key);
		Long oldVal = Long.valueOf(object.toString());
		Long newVal = oldVal - value;
		STATIC_MAP.put(key, newVal);
		return newVal;
	}

	public long incr(String key, long value) {
		Object object = STATIC_MAP.get(key);
		Long oldVal = Long.valueOf(object.toString());
		Long newVal = oldVal + value;
		STATIC_MAP.put(key, newVal);
		return newVal;
	}

}
