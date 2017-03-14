package com.xxx.collect.core.util.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {

  private static File SysTempDir = null;

  /**
   * 获取系统临时文件夹目录
   *
   * @return
   * @throws IOException
   */
  public static File getTempDir() {
    if (SysTempDir == null) {
      try {
        File tempFile = File.createTempFile("getTempDir", "");
        SysTempDir = tempFile.getParentFile();
      } catch (IOException e) {
        throw new RuntimeException();
      }
    }
    return SysTempDir;
  }

  /**
   * 创建一个指定名称的临时文件
   *
   * @return
   */
  public static File createTempFile(String fileName) {
    File tempFile = new File(System.getProperty("java.io.tmpdir"), fileName);
    if (!tempFile.getParentFile().exists()) {
      tempFile.getParentFile().mkdirs();
    }
    tempFile.deleteOnExit();
    return tempFile;
  }


  /**
   * 如果文件不存在或者删除失败会抛出异常
   */
  public static void fileDelete(File file) {
    file.delete();
    if (file.exists())
      throw new RuntimeException("文件删除后依然存在：" + file.getAbsolutePath());
  }

  /**
   * 是否空文件夹
   *
   * @param dir
   * @return
   */
  public static boolean isEmptyDir(File dir) {
    File[] files = dir.listFiles();
    return files == null || files.length == 0;
  }

  /**
   * 如果文件夹不存在则创建文件夹, 如果文件夹创建不成功，会直接抛出异常
   *
   * @param dir
   */
  public static void mkdirs(File dir) {
    if (!dir.exists() || !dir.isDirectory())
      if (dir.mkdirs() == false)
        throw new RuntimeException("文件夹创建失败：" + dir.getAbsolutePath());
  }

  public static byte[] toByteArray(InputStream input) throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    copy(input, output);
    return output.toByteArray();
  }

  public static int copy(InputStream input, OutputStream output) throws IOException {
    long count = copyLarge(input, output);
    if (count > 2147483647L) {
      return -1;
    }
    return (int) count;
  }

  public static long copyLarge(InputStream input, OutputStream output) throws IOException {
    byte[] buffer = new byte[4096];
    long count = 0L;
    int n = 0;
    while (-1 != (n = input.read(buffer))) {
      output.write(buffer, 0, n);
      count += n;
    }
    return count;
  }

  public static byte[] inputStream2Bytes(InputStream is) {
    byte byteArray[];
    try {
      byteArray = IOUtil.toByteArray(is);
      return byteArray;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  public static InputStream bytes2InputStream(byte bytes[]) {
    if (bytes == null) {
      return null;
    } else {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
      return inputStream;
    }
  }

  public static int getFileSize(File file) {
    try {
      FileInputStream fileInputStream = new FileInputStream(file);
      int available = fileInputStream.available();
      fileInputStream.close();
      return available;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static byte[] file2Bytes(File file) {
    FileInputStream is;
    byte[] bytes = null;
    try {
      is = new FileInputStream(file);
      bytes = inputStream2Bytes(is);
      is.close();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return bytes;
  }

  public static void inputStreamToFile(InputStream is, File file) {
    try {
      FileOutputStream os = new FileOutputStream(file);
      copyAndClose(is, os);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static void copyFile(File src, File target) {
    if (src.exists())
      target.getParentFile().mkdirs();
    try {
      FileInputStream is = new FileInputStream(src);
      FileOutputStream os = new FileOutputStream(target);
      copyAndClose(is, os);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static String fileToString(File target) {
    return fileToString(target, "utf-8");
  }

  public static String fileToString(File target, String encode) {
    try {
      FileInputStream is = new FileInputStream(target);
      String string = new String(inputStream2Bytes(is), encode);
      is.close();
      return string;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void stringToFile(String str, File target) {
    try {
      InputStream is = new ByteArrayInputStream(str.getBytes());
      FileOutputStream os = new FileOutputStream(target);
      copyAndClose(is, os);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static void copyAndClose(InputStream is, OutputStream os) {
    try {
      IOUtil.copy(is, os);
      is.close();
      os.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
