package com.xxx.collect.core.util.zip.zip;

import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;


/**
 * 废弃，压缩中文会有乱码，请使用Zip4jTool
 */
@Deprecated
public class ZipTool {
  // public static void main(String[] args) throws FileNotFoundException {
  // File tempFile = new File("F:\\imageTest\\zip\\test-ZIP-JAVA-dir.zip");
  // if (tempFile.exists())
  // tempFile.delete();
  // FileOutputStream tempFileOS;
  // tempFileOS = new FileOutputStream(tempFile);
  // ZipTool zip = new ZipTool(tempFileOS);
  // zip.addCompressFile("3D乡村铁艺装饰画壁挂模11型.txt", new
  // ByteArrayInputStream("123".getBytes()));
  // // zip.addCompressDir("你好  123 abc");
  // zip.close();
  // }

  public static void main(String[] args) throws FileNotFoundException {
    System.out.println(System.getProperties().getProperty("file.separator"));
    File tempFile = new File("F:\\imageTest\\zip\\test-ZIP-JAVA-dir.zip");
    if (tempFile.exists())
      tempFile.delete();
    FileOutputStream tempFileOS;
    tempFileOS = new FileOutputStream(tempFile);
    ZipTool zip = new ZipTool(tempFileOS);
    String fileName = "sifon_SYFON DI_A GEÇEN GÖVDE APARATI_1.SLDPRT";
    zip.addCompressFile(fileName, new ByteArrayInputStream("123".getBytes()));
    String fileName2 = "にほんご .txt";
    zip.addCompressFile(fileName2, new ByteArrayInputStream("456".getBytes()));
    String fileName3 = "你好  123 abc.txt";
    zip.addCompressFile(fileName3, new ByteArrayInputStream("456".getBytes()));
    String fileDir = "你好  123 abc";
    zip.addCompressDir(fileDir);
    zip.addCompressDir("3D乡村铁艺装饰画壁挂模型");
    zip.addCompressFile("3D乡村铁艺装饰画壁挂模型/122", new ByteArrayInputStream("456".getBytes()));
    zip.close();
  }

  private int level = 0;

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void close() {
    try {
      this.zipOutputStream.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private ZipOutputStream zipOutputStream;

  public ZipTool(OutputStream outputStream) {
    //this.zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream, 1024));
    this.zipOutputStream = new ZipOutputStream(outputStream);
    zipOutputStream.setEncoding("gbk");
    // 不设置压缩级别，默认采用最大压缩级别
    if (level != 0)
      this.zipOutputStream.setLevel(this.level);
  }

  /** 压缩Dir */
  public void addCompressDir(String pathName) {
    try {
      ZipEntry entry = new ZipEntry(pathName + "\\");
      entry.setUnixMode(755);
      this.zipOutputStream.putNextEntry(entry);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /** 压缩 */
  public void addCompressFile(String pathName, InputStream is) {
    try {
      ZipEntry entry = new ZipEntry(pathName);
      entry.setUnixMode(644);
      this.zipOutputStream.putNextEntry(entry);
      IOUtils.copy(is, this.zipOutputStream);
      is.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}