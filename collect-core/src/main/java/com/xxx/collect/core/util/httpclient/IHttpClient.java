package com.xxx.collect.core.util.httpclient;

import com.xxx.collect.core.util.httpclient.model.HttpClientConfig;
import com.xxx.collect.core.util.httpclient.model.HttpResult;

import java.io.File;
import java.util.Map;

public interface IHttpClient {

	public void download(File localFile, String url) throws Exception;

	public HttpResult visit(String url) throws Exception;
	public HttpResult visit(String url, HttpClientConfig httpClientConfig) throws Exception;

	public HttpResult post(String url, Map<String, String> parm) throws Exception;
}
