package com.xxx.collect.core.model;

import java.io.File;

public class TempFile {

  public TempFile(File file, String fileName) {
    super();
    this.file = file;
    this.fileName = fileName;
  }

  /**
   * 真实的临时文件
   */
  private File file;

  private String fileName;

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

}
