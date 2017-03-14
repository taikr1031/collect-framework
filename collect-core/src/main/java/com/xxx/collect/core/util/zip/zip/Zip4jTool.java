package com.xxx.collect.core.util.zip.zip;

import com.xxx.collect.core.util.UuidUtil;
import com.xxx.collect.core.util.file.DirFileUtil;
import com.xxx.collect.core.util.io.IOUtil;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.List;

public class Zip4jTool {

  private static final Log log = LogFactory.getLog(Zip4jTool.class);

  public static void main(String[] args) throws IOException {
//    testZipDir();
    testZipDirWithFiles();
  }

  /**
   * 直接测试压缩一个文件夹，一次性完成，效率高
   */
  private static void testZipDirWithFiles() throws IOException {
    log.debug("开始压缩");
    File dir = new File("D:\\aigei\\svn-aigei\\docs\\2gei\\zip测试\\testToZipDir");
    File tempFile2 = new File("D:\\aigei\\svn-aigei\\docs\\2gei\\zip测试\\zip4j-testToZipDirWithFiles.zip");
    if (tempFile2.exists())
      IOUtil.fileDelete(tempFile2);
    Zip4jTool zip2 = new Zip4jTool(tempFile2);
    zip2.addDirWithFiles(dir, false);
    log.debug("完成压缩");
  }


  /**
   * 单个文件压缩，测试直接压缩一个文件夹
   */
  private static void testZipDir() throws IOException {
    log.debug("开始压缩");
    File dir = new File("D:\\aigei\\svn-aigei\\docs\\2gei\\zip测试\\testToZipDir");
    List<File> fileList = DirFileUtil.listFilByDeep(dir);
    File tempFile2 = new File("D:\\aigei\\svn-aigei\\docs\\2gei\\zip测试\\zip4j-testToZipDir.zip");
    if (tempFile2.exists())
      IOUtil.fileDelete(tempFile2);
    Zip4jTool zip2 = new Zip4jTool(tempFile2);
    for (File file : fileList) {
      String path = file.getAbsolutePath().replace(dir.getAbsolutePath() + "\\", "");
      FileInputStream is = new FileInputStream(file);
      zip2.addFile(path, is);
      is.close();
    }
    log.debug("完成压缩");
  }

  /**
   * 单个文件压缩，主要测试乱码和空文件夹问题
   */
  private static void testLuanMa() {
    //1、测试自定义文件压缩
    System.out.println(System.getProperties().getProperty("file.separator"));
    File tempFile = new File("D:\\aigei\\svn-aigei\\docs\\2gei\\zip测试\\zip4j-2.zip");
    if (tempFile.exists())
      IOUtil.fileDelete(tempFile);
    Zip4jTool zip = new Zip4jTool(tempFile);
    String fileName = "sifon &#48;_SYFON DI_A GEÇEN GÖVDE APARATI_1.SLDPRT";
    zip.addFile(fileName, new ByteArrayInputStream("123".getBytes()));
    String fileName2 = "にほんご .txt";
    zip.addFile(fileName2, new ByteArrayInputStream("456".getBytes()));
    String fileName3 = "你好 : 123 abc.txt";//设定一个非法的字符
    zip.addFile(fileName3, new ByteArrayInputStream("456".getBytes()));
    String fileDir = "你好  123 abc";
    zip.addEmptyDir(fileDir);
    zip.addEmptyDir("3D乡村铁艺装饰画壁挂模型");
    zip.addEmptyDir("模型1/模型2/模型3/模型4/模型5");
    zip.addFile("3D乡村铁艺装饰画壁挂模型/122/333/444/55", new ByteArrayInputStream("456".getBytes()));
  }

  private ZipFile zipFile;

  public Zip4jTool(File file) {
    try {
      this.zipFile = new ZipFile(file);
      this.zipFile.setFileNameCharset("GBK");
    } catch (ZipException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 效率高，添加压缩文件夹，压缩文件夹和里面所有的文件形成一个压缩包
   *
   * @param rootDir
   * @param isContainRootDir 是否包含rootDir本身，如果是true，则rootDir为zip的目录，如果是false，则zip根目录里面为rootDir的下级文件
   */
  public void addDirWithFiles(File rootDir, boolean isContainRootDir) {
    try {
      ZipParameters parameters = new ZipParameters();
      parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);// 压缩方式
      parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);    // 压缩级别
      if (isContainRootDir)
        zipFile.addFolder(rootDir, parameters);
      else {
        File[] files = rootDir.listFiles();
        if (files != null)
          for (File file : files) {
            if (file.isDirectory())
              zipFile.addFolder(file, parameters);
            else
              zipFile.addFile(file, parameters);
          }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 压缩一个空文件夹，通过在临时目录下面建立空文件夹实现，用于和压缩文件配合完成多文件压缩
   */
  public void addEmptyDir(String pathName) {
    try {
      // 先建一个uuid的文件夹
      File tempFile = File.createTempFile("Zip4jTool", "");
      File uuidDir = new File(tempFile.getParentFile(), UuidUtil.uuid());
      // 再在uuid文件夹下面建立所有的文件路径
      File dirs = new File(uuidDir, pathName);
      dirs.mkdirs();
      // 再取uuid下面的唯一那个文件夹
      File[] listFiles = uuidDir.listFiles();
      if (listFiles == null || listFiles.length != 1)
        throw new RuntimeException("uuidDir下未发现文件夹!" + uuidDir.getAbsolutePath());
      File dir = listFiles[0];
      ZipParameters parameters2 = new ZipParameters();
      parameters2.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);// 压缩方式
      parameters2.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);    // 压缩级别
      zipFile.addFolder(dir, parameters2);
      DirFileUtil.delete(uuidDir);
      tempFile.delete();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * * 效率低，谨慎用，添加单个文件进压缩包
   */
  public void addFile(String pathName, InputStream is) {
    try {
      ZipParameters parameters = new ZipParameters();
      parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
      parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);    // 压缩级别
      parameters.setSourceExternalStream(true);
      parameters.setFileNameInZip(pathName);
      zipFile.addStream(is, parameters);
      is.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}