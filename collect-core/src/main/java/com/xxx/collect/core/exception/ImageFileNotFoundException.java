package com.xxx.collect.core.exception;

@SuppressWarnings("serial")
public class ImageFileNotFoundException extends RuntimeException {
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ImageFileNotFoundException(String fileName) {
		this.fileName = fileName;
	}
}
 