package com.xxx.collect.core.util.htmlparse;

public class HtmlParserValidate {
	public static boolean validateUrl(String url) {
		if (!url.contains("http://") || url.contains(" ")) {
			return false;
		} else {
			return true;
		}
	}
}
