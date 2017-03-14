package com.xxx.collect.core.exception;

public class FileCannotParseException extends Exception {
  public FileCannotParseException(String message) {
    this("未知", message);
  }

  public FileCannotParseException(String fileExtName, String message) {
    super("文件无法正常读取解析,扩展名:" + fileExtName + " message:" + message);
  }

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

}
