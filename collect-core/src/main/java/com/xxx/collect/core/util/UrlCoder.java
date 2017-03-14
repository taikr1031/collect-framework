package com.xxx.collect.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlCoder {

	public static String encode(String str) {
		String encode = null;
		try {
			encode = URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return encode;
	}

	public static String decode(String str) {
		String encode = null;
		try {
			encode = URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return encode;
	}

}
