package com.xxx.collect.core.util.httpclient.proxy;

import com.xxx.collect.core.util.httpclient.model.HttpResult;

/**
 *校验使用代理访问的结果，有可能提示，不能访问此地址等，屏蔽这样的代理IP
 */
public class ProxyResponseValidate {

	public static boolean validateResponse(HttpResult response) {
		if (response.getStatus() != 200) {
			return false;
		}
		if (response.getHtml().contains("<TITLE>ERROR: The requested URL could not be retrieved</TITLE>")) {
			return false;
		}
//		if (response.getHtml().contains("<html><head><title></title><style")) {
//			return false;
//		}
		if (response.getHtml().contains("<title>找不到网页</title>")) {
			return false;
		}
		return true;
	}

}
