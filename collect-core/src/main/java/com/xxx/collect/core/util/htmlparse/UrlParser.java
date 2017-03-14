package com.xxx.collect.core.util.htmlparse;

import com.xxx.collect.core.util.RegexUtil;
import com.xxx.collect.core.util.string.StringUtil;

public class UrlParser {

	public static String getAbsoluteUrl(String parentUrl, String url) {
		url = url.trim();
		String restUrl = url;
		if (StringUtil.isBlank(url))
			return parentUrl;
		if (!url.contains("http:")) {
			if ("/".equals(url.substring(0, 1))) {
				restUrl = getHostUrl(parentUrl) + url;
			} else if ("?".equals(url.substring(0, 1))) {
				restUrl = parentUrl + url;
			} else {
				restUrl = parentUrl + "/" + url;
			}
		}
		return restUrl;
	}

	/**
	 * @param url
	 *            : http://t.qq.com/people/
	 * @return http://t.qq.com
	 */
	public static String getHostUrl(String url) {
		String site = RegexUtil.searchBetween(url, "(http://[^/]*)");
		return site;
	}

	/**
	 * @param url
	 *            : http://t.qq.com/people/
	 * @return t.qq.com
	 */
	public static String getHostName(String url) {
		String site = RegexUtil.searchBetween(url, "http://([^/]+).*");
		return site;
	}

	public static void main(String[] args) {
		String url = "http://t.qq.com/sdfsdf?";
		System.out.println(getHostUrl(url));
	}

}
