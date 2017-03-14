package com.xxx.collect.core.model;

import java.io.File;

import com.xxx.collect.core.util.file.FileNameUtil;

/**
 * 系统管理的临时文件，建立在自己管理的文件夹里面
 */
public class SysTempFile {

	public String toUrl() {
		return date + "-" + uuid + "." + FileNameUtil.getExtToLowerCase(this.file.getName());
	}
	
	public SysTempFile(String date, String uuid, File file) {
		super();
		this.date = date;
		this.uuid = uuid;
		this.file = file;
	}

	private String date;
	private String uuid;
	private File file;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
