package com.xxx.collect.core.util.htmlparse;

public class HtmlCompress {
	/**
	 * 不改变文档结构，去掉重复的空格和换行
	 */
	public static String compress1(String html) {
		html = html.replaceAll("\\s+?>", ">");
		html = html.replaceAll("\\s+?/>", "/>");
		html = html.replaceAll(" +", " ");
		html = html.replaceAll("\\n+", "\n");
		html = html.replaceAll("\\r+", "\r");
		html = html.replaceAll("\\t+", "\t");
		return html;
	}
}
