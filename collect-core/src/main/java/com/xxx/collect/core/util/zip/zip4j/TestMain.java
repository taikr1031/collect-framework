package com.xxx.collect.core.util.zip.zip4j;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.io.StreamEntry;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.unzip.UnzipUtil;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestMain {

  public static void main(String[] args) throws Exception {

    // Zip4j
    // zip4j_1.3.1.jar
    // http://www.lingala.net/zip4j/

    // 压缩

    // 文件压缩
    test1();

    // 设置密码
    test2();// --标准
    test3();// --AES

    // 文件夹压缩
    test4();

    // 将文件压缩到指定文件夹中
    test5();

    // 往ZIP中添加文件
    test6();

    // 分割压缩文件
    test7();

    // 创建ZIP流
    test8();

    // 从ZIP中删除文件
    test9();

    // 获取ZIP中文件一览
    test10();

    // 解压
    test20();// 解压所有文件
    test21();// 解压所有文件到流
    test22();// 解压单个文件
  }

  private static void test1() throws Exception {

    ZipFile zipFile = new ZipFile("c:\\ZipTest\\test1.zip");

    ArrayList filesToAdd = new ArrayList();
    filesToAdd.add(new File("c:\\ZipTest\\sample.txt"));
    filesToAdd.add(new File("c:\\ZipTest\\文件.doc"));
    filesToAdd.add(new File("c:\\ZipTest\\파일.xls"));
    filesToAdd.add(new File("c:\\ZipTest\\ファイル.ppt"));

    ZipParameters parameters = new ZipParameters();
    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

    zipFile.addFiles(filesToAdd, parameters);
  }

  private static void test2() throws Exception {

    ZipFile zipFile = new ZipFile("c:\\ZipTest\\test2.zip");

    ArrayList filesToAdd = new ArrayList();
    filesToAdd.add(new File("c:\\ZipTest\\sample.txt"));
    filesToAdd.add(new File("c:\\ZipTest\\文件.doc"));
    filesToAdd.add(new File("c:\\ZipTest\\파일.xls"));
    filesToAdd.add(new File("c:\\ZipTest\\ファイル.ppt"));

    ZipParameters parameters = new ZipParameters();
    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

    // Set password
    parameters.setEncryptFiles(true);
    parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
    parameters.setPassword("test123!");

    zipFile.addFiles(filesToAdd, parameters);
  }

  private static void test3() throws Exception {

    ZipFile zipFile = new ZipFile("c:\\ZipTest\\test3.zip");

    ArrayList filesToAdd = new ArrayList();
    filesToAdd.add(new File("c:\\ZipTest\\sample.txt"));
    filesToAdd.add(new File("c:\\ZipTest\\文件.doc"));
    filesToAdd.add(new File("c:\\ZipTest\\파일.xls"));
    filesToAdd.add(new File("c:\\ZipTest\\ファイル.ppt"));

    ZipParameters parameters = new ZipParameters();
    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

    // Set password
    parameters.setEncryptFiles(true);
    parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
    parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
    parameters.setPassword("test123!");

    zipFile.addFiles(filesToAdd, parameters);
  }

  private static void test4() throws Exception {
    ZipFile zipFile = new ZipFile("c:\\ZipTest\\test4.zip");

    String folderToAdd = "c:\\ZipTest";

    ZipParameters parameters = new ZipParameters();
    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

    zipFile.addFolder(folderToAdd, parameters);
  }

  private static void test5() throws Exception {

    ZipFile zipFile = new ZipFile("c:\\ZipTest\\test5.zip");

    ArrayList filesToAdd = new ArrayList();
    filesToAdd.add(new File("c:\\ZipTest\\sample.txt"));
    filesToAdd.add(new File("c:\\ZipTest\\文件.doc"));
    filesToAdd.add(new File("c:\\ZipTest\\파일.xls"));
    filesToAdd.add(new File("c:\\ZipTest\\ファイル.ppt"));

    ZipParameters parameters = new ZipParameters();
    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

    parameters.setRootFolderInZip("test2/");

    zipFile.addFiles(filesToAdd, parameters);
  }

  private static void test6() throws Exception {

    ZipFile zipFile = new ZipFile("c:\\ZipTest\\test1.zip");

    ZipParameters parameters = new ZipParameters();
    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    parameters.setFileNameInZip("文件2.doc");
    parameters.setSourceExternalStream(true);

    InputStream is = new FileInputStream("c:\\ZipTest\\文件2.doc");

    zipFile.addStream(is, parameters);

    is.close();
  }

  private static void test7() throws Exception {

    ZipFile zipFile = new ZipFile("c:\\ZipTest\\test7.zip");

    ArrayList filesToAdd = new ArrayList();
    filesToAdd.add(new File("c:\\ZipTest\\sample.txt"));
    filesToAdd.add(new File("c:\\ZipTest\\文件.doc"));
    filesToAdd.add(new File("c:\\ZipTest\\파일.xls"));
    filesToAdd.add(new File("c:\\ZipTest\\ファイル.ppt"));

    ZipParameters parameters = new ZipParameters();
    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

    // SplitLenth has to be greater than 65536 bytes
    // zipFile.createZipFileFromFolder()
    zipFile.createZipFile(filesToAdd, parameters, true, 65536);
  }

  private static void test8() throws Exception {

    ArrayList filesToAdd = new ArrayList();
    filesToAdd.add(new File("c:\\ZipTest\\sample.txt"));
    filesToAdd.add(new File("c:\\ZipTest\\文件.doc"));
    filesToAdd.add(new File("c:\\ZipTest\\파일.xls"));
    filesToAdd.add(new File("c:\\ZipTest\\ファイル.ppt"));

    ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(new File(
        "c:\\ZipTest\\test8.zip")));

    ZipParameters parameters = new ZipParameters();
    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

    // Set password
    parameters.setEncryptFiles(true);
    parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
    parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
    parameters.setPassword("test123!");

    for (int i = 0; i < filesToAdd.size(); i++) {
      File file = (File) filesToAdd.get(i);
      StreamEntry streamEntry = new StreamEntry("datafile1.csv", new FileInputStream(file));
      outputStream.putNextEntry(streamEntry, parameters);

      if (file.isDirectory()) {
        outputStream.closeEntry();
        continue;
      }

      InputStream inputStream = new FileInputStream(file);
      byte[] readBuff = new byte[4096];
      int readLen = -1;
      while ((readLen = inputStream.read(readBuff)) != -1) {
        outputStream.write(readBuff, 0, readLen);
      }

      outputStream.closeEntry();

      inputStream.close();
    }

    outputStream.finish();

    outputStream.close();
  }

  private static void test9() throws Exception {

    ZipFile zipFile = new ZipFile("c:\\ZipTest\\test1.zip");

    // 删除指定文件
    zipFile.removeFile("sample.txt");

    // 删除第一个文件
    if (zipFile.getFileHeaders() != null && zipFile.getFileHeaders().size() > 0) {
      zipFile.removeFile((FileHeader) zipFile.getFileHeaders().get(0));
    } else {
      System.out.println("This cannot be demonstrated as zip file does not have any files left");
    }

  }

  private static void test10() throws Exception {

    ZipFile zipFile = new ZipFile("c:\\ZipTest\\test5.zip");

    List fileHeaderList = zipFile.getFileHeaders();

    for (int i = 0; i < fileHeaderList.size(); i++) {
      FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
      System.out.println("****File Details for: " + fileHeader.getFileName() + "*****");
      System.out.println("Name: " + fileHeader.getFileName());
      System.out.println("Compressed Size: " + fileHeader.getCompressedSize());
      System.out.println("Uncompressed Size: " + fileHeader.getUncompressedSize());
      System.out.println("CRC: " + fileHeader.getCrc32());
      System.out.println("************************************************************");
    }

  }

  private static void test20() throws Exception {
    // 方法一
    ZipFile zipFile = new ZipFile("c:\\ZipTest\\test1.zip");
    zipFile.extractAll("c:\\ZipTest1");

    // 方法二
    ZipFile zipFile2 = new ZipFile("c:\\ZipTest\\test2.zip");
    if (zipFile2.isEncrypted()) {
      zipFile2.setPassword("test123!");
    }
    List fileHeaderList = zipFile2.getFileHeaders();
    for (int i = 0; i < fileHeaderList.size(); i++) {
      FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
      zipFile2.extractFile(fileHeader, "c:\\ZipTest2\\");
    }
  }

  private static void test21() throws Exception {
    ZipFile zipFile = new ZipFile("c:\\ZipTest\\test2.zip");
    if (zipFile.isEncrypted()) {
      zipFile.setPassword("test123!");
    }

    List fileHeaderList = zipFile.getFileHeaders();

    for (int i = 0; i < fileHeaderList.size(); i++) {
      FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
      if (fileHeader != null) {

        String outFilePath = "c:\\ZipTest3\\" + System.getProperty("file.separator")
            + fileHeader.getFileName();
        File outFile = new File(outFilePath);

        if (fileHeader.isDirectory()) {
          outFile.mkdirs();
          continue;
        }

        File parentDir = outFile.getParentFile();
        if (!parentDir.exists()) {
          parentDir.mkdirs();
        }

        ZipInputStream is = zipFile.getInputStream(fileHeader);
        OutputStream os = new FileOutputStream(outFile);

        int readLen = -1;
        byte[] buff = new byte[4096];

        while ((readLen = is.read(buff)) != -1) {
          os.write(buff, 0, readLen);
        }

        os.close();
        os = null;
        is.close();
        is = null;

        UnzipUtil.applyFileAttributes(fileHeader, outFile);

        System.out.println("Done extracting: " + fileHeader.getFileName());
      } else {
        System.err.println("fileheader is null. Shouldn't be here");
      }
    }
  }

  private static void test22() throws Exception {

    ZipFile zipFile = new ZipFile("c:\\ZipTest\\test2.zip");
    if (zipFile.isEncrypted()) {
      zipFile.setPassword("test123!");
    }

    zipFile.extractFile("文件.doc", "c:\\ZipTest4\\");
  }

}
