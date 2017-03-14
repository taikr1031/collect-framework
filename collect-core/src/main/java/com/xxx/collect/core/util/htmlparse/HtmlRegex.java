package com.xxx.collect.core.util.htmlparse;

import com.xxx.collect.core.util.RegexUtil;

public class HtmlRegex {
	/**
	 * <a [^<>]*href=\"(.+?)\"[^<>]*>[^<>]*��һҳ
	 */
	public static String getNextPageHref(String html) {
		return RegexUtil.searchBetween(html, "<a [^<>]*href=\"(.+?)\"[^<>]*>[^<>]*��һҳ");
	}
}
