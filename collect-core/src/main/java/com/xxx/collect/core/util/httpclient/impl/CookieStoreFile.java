package com.xxx.collect.core.util.httpclient.impl;

import com.xxx.collect.core.util.httpclient.ICookieStore;
import com.xxx.collect.core.util.string.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取文件夹下与site同名的.txt文件里每一行作为cookie列表
 * 
 * @author Astar
 * 
 */
public class CookieStoreFile implements ICookieStore {

	private Log log = LogFactory.getLog(CookieStoreFile.class);

	public CookieStoreFile() {
		// 设置默认dir
		this.dir = new File("E:\\gei-work\\Spider\\Cookie");
	}

	private File dir;

	public void setDir(File dir) {
		this.dir = dir;
	}

	@Override
	public String[] getCookieArr(String site) {
		File cookiesFile = new File(this.dir, site + ".txt");
		if (!cookiesFile.exists()) {
			log.warn("cookie 存储文件:"+cookiesFile.getAbsolutePath()+" 不存在！");
			return null;
		}
		String str;
		try {
			str = IOUtils.toString(new FileInputStream(cookiesFile));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		String[] strs = str.split("\n");
		return strs;
	}

	@Override
	public String nextCookie(String site) {
		CookieChange cookieChange = cookieChangeMap.get(site);
		if (cookieChange == null) {
			cookieChange = new CookieChange(site);
			cookieChangeMap.put(site, cookieChange);
		}
		return cookieChange.getCooike();
	}

	// 静态属性，全站唯一，保证全程序轮询
	private static Map<String, CookieChange> cookieChangeMap = new HashMap<String, CookieChange>();

	/* 利用队列和指针，轮流使用cookie队列 */
	class CookieChange {
		public CookieChange(String site) {
			cookiesArr = getCookieArr(site);
		}

		private String[] cookiesArr;
		private int cookiesIndex = 0;
		private Map<String, String> threadCookie = new HashMap<String, String>();

		public synchronized String getCooike() {
			// 每个线程使用固定一个cookie
			String threadName = Thread.currentThread().getName();
			String cookie = threadCookie.get(threadName);
			if (!StringUtil.isBlank(cookie)) {// 如果此线程已经注册了cookie则返回固定的
				return cookie;
			} else if (cookiesArr != null && cookiesArr.length > 0) {
				if (cookiesIndex >= cookiesArr.length) {
					cookiesIndex = 0;
				}
				String cookies = cookiesArr[cookiesIndex];
				log.debug("cookie get: " + threadName + " cookiesIndex[" + cookiesIndex
						+ "] cookie[" + cookies + "]");
				cookiesIndex++;

				// 注册线程的cookie，固定下来
				this.threadCookie.put(threadName, cookies);
				return cookies;
			} else {
				return null;
			}
		}
	}

}
