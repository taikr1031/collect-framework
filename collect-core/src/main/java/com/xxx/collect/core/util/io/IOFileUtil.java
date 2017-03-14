package com.xxx.collect.core.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class IOFileUtil {

  public static String reader(String file) throws IOException {
    return reader(new File(file));
  }

  public static String reader(File file) throws IOException {
    FileReader reader = new FileReader(file);
    BufferedReader br = new BufferedReader(reader);
    String s;
    String strRest = "";
    System.out.println(reader.getEncoding());
    while ((s = br.readLine()) != null) {
      strRest += s + "\n";

    }
    br.close();
    return strRest;
  }

  public byte[] readLastNByte(int num, String fileName) {
    int buffLen = num;
    byte[] byts = new byte[buffLen];
    try {
      File file = new File(fileName);
      FileInputStream inputStream = new FileInputStream(file);
      int totalLen;
      totalLen = inputStream.available();
      if (totalLen > buffLen) {
        inputStream.skip(totalLen - buffLen);
        inputStream.read(byts);
      }
      inputStream.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return byts;
  }

  public static String loadFileToString(File f, String charset) throws UnsupportedEncodingException {
    return new String(loadFileToByte(f), charset);
  }

  public static String loadFileToString(String f, String charset)
      throws UnsupportedEncodingException {
    return new String(loadFileToByte(f), charset);
  }

  public static String loadFileToString(String f) {
    return new String(loadFileToByte(f));
  }

  public static String loadFileToString(File f) {
    return new String(loadFileToByte(f));
  }

  public static byte[] loadFileToByte(File f) {
    return loadFileToByte(f.getAbsolutePath());
  }

  public static byte[] loadFileToByte(String f) {
    // long beginTime = System.currentTimeMillis();
    InputStream is = null;
    byte[] ret = null;
    try {
      is = new BufferedInputStream(new FileInputStream(f));
      long contentLength = f.length();
      ByteArrayOutputStream outstream = new ByteArrayOutputStream(
          contentLength > 0 ? (int) contentLength : 1024);
      byte[] buffer = new byte[4096];
      int len;
      while ((len = is.read(buffer)) > 0) {
        outstream.write(buffer, 0, len);
      }
      outstream.close();
      // ret = outstream.toString();
      byte[] ba = outstream.toByteArray();
      ret = ba;
      // ret = new String(ba);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    // long endTime = System.currentTimeMillis();
    return ret;
  }

  public static void writeByteToFile(byte[] byts, String file) throws IOException {
    writeByteToFile(byts, new File(file));
  }

  public static void writeByteToFile(byte[] byts, File file) throws IOException {
    FileOutputStream out = null;
    File fileDir = file.getParentFile();
    if (fileDir.exists() == false)
      fileDir.mkdirs();
    out = new FileOutputStream(file);
    out.write(byts);
    out.close();
  }

  public static void writeStringToFile(String str, File file) throws IOException {
    writeStringToFile(str, file.getAbsolutePath());
  }

  public static void writeStringToFile(String str, String file) throws IOException {
    OutputStreamWriter out = null;
    try {
      File fileFile = new File(file);
      File fileDir = fileFile.getParentFile();
      if (fileDir.exists() == false)
        fileDir.mkdirs();
      if (!fileFile.exists()) {
        fileFile.createNewFile();
      }
      out = new FileWriter(file);
      out.write(str);
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }

  public static void writeStringToFileAppend(String str, File file) throws IOException {
    writeStringToFileAppend(str, file.getAbsolutePath());
  }

  public static void writeStringToFileAppend(String str, String file) throws IOException {
    OutputStreamWriter out = null;
    File fileFile = new File(file);
    File fileDir = fileFile.getParentFile();
    if (fileDir.exists() == false)
      fileDir.mkdirs();
    if (!fileFile.exists()) {
      fileFile.createNewFile();
    }
    out = new FileWriter(file, true);
    out.append(str);
    out.close();
  }

  public static void copyFile(String orgFile, String destFile) throws IOException {
    byte[] byts;
    File t = new File(orgFile);
    byts = IOFileUtil.loadFileToByte(t);
    IOFileUtil.writeByteToFile(byts, new File(destFile));
  }

  public static void main(String[] args) {
    String string = IOFileUtil
        .loadFileToString("E:\\AstarWork\\HtmlFile\\t.qq.people\\t.qq.com\\people");
    System.out.println(string);
  }
}