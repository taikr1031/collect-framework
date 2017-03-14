package com.xxx.collect.core.tool;

import java.util.Date;

import com.xxx.collect.core.util.date.DateUtil;

public class CacheTool {

	public static Date lastModifyDate = DateUtil.strToDate("2013-10-01", "yyyy-MM-dd");;

	/**
	 * 获取一个固定的图片修改时间，用于创建客户端缓存
	 */
	public static long getFixedLastModifyDate() {
		return lastModifyDate.getTime();
	}

	public static Date lastModifyDateError = DateUtil.strToDate("2011-10-01", "yyyy-MM-dd");;

	/**
	 * 获取出错图片的缓存
	 */
	public static long getLastModifyDateError() {
		return lastModifyDateError.getTime();
	}
}
