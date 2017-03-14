package com.xxx.collect.core.tool;

import org.apache.log4j.Priority;
import org.apache.log4j.RollingFileAppender;

/**
 * 实现只保存规定级别的日志进文件
 */
public class Log4jLevelRollingFileAppender extends RollingFileAppender {

	@Override
	public boolean isAsSevereAsThreshold(Priority priority) {
		// 只判断是否相等，而不判断优先级
		return this.getThreshold().equals(priority);
	}
}
