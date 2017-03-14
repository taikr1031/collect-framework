package com.xxx.collect.core.util.httpclient;

public interface ICookieStore {
	/**
	 * 站锟斤拷cookie锟叫憋拷
	 * 
	 * @param site
	 * @return
	 */
	String[] getCookieArr(String site);
	
	/** 
	 * 锟皆讹拷锟叫伙拷cookie
	 * @param site
	 * @return
	 */
	String nextCookie(String site);
}
