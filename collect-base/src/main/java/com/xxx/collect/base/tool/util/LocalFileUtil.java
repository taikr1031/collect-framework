package com.xxx.collect.base.tool.util;

import com.xxx.collect.core.util.RegexUtil;
import com.xxx.collect.core.util.file.DirFileUtil;
import com.xxx.collect.core.util.io.IOUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LocalFileUtil {

  public static void main(String[] args) {
    System.out.println(getRelativeLevel(new File("D:\\会声会影12-爱情志-Ⅰ(才子佳人)\\素材文件夹"), new File("D:\\会声会影12-爱情志-Ⅰ(才子佳人)")));
  }

  public static String DIR_NAME = "DirName";

  private static int counter = 0;

  /**
   * 获取相对级别,子文件夹和文件的相对级别都是从1开始
   *
   * @param dir
   * @param rootDir
   * @return
   */
  public static int getRelativeLevel(File dir, File rootDir) {
    String absolutePath = dir.getAbsolutePath().replace(rootDir.getAbsolutePath(), "");
    return RegexUtil.searchList(absolutePath, "\\\\").size();
  }

  /**
   * 按指定文件名称删除文件
   *
   * @param dir
   */
  public static void deleteFileByName(File dir, boolean isRegex, boolean isTest, String... names) {
    if (!dir.isDirectory())
      return;
    File[] files = dir.listFiles();
    if (files != null && files.length > 0) {
      for (File file : files) {
        if (file.isDirectory())
          deleteFileByName(file, isRegex, isTest, names);
      }
    }
    File[] files2 = dir.listFiles();
    if (files2 == null || files2.length == 0) {
      return;
    } else {
      for (File file : files2) {
        for (String defName : names) {
          boolean isDelete;
          if (isRegex) {
            isDelete = RegexUtil.match(file.getName(), defName);
          } else {
            isDelete = file.getName().equals(defName);
          }
          if (isDelete) {
            String fileInfo = " : " + file.getName() + " size:" + file.length() + " path:" + file.getAbsolutePath();
            counter++;
            if (!isTest && counter % 1 == 0)
              System.out.println(counter + "删除：" + fileInfo);
            if (isTest)
              System.out.println("测试 删除-" + counter + fileInfo);
            else {
              if (file.delete() == false)
                System.out.println("文件删除失败：" + file.getAbsolutePath());
            }
          }
        }
      }
    }
  }

  /**
   * 删除空文件夹
   *
   * @param dir
   */
  public static List<File> deleteNullDir(File dir) {
    List<File> deleteDir = new ArrayList<>();
    if (!dir.isDirectory())
      return deleteDir;
    File[] files = dir.listFiles();
    if (files != null && files.length > 0) {
      for (File file : files) {
        deleteNullDir(file);
      }
    }
    File[] files2 = dir.listFiles();
    if (files2 == null || files2.length == 0) {
      IOUtil.fileDelete(dir);
      deleteDir.add(dir);
       System.out.println("删除空文件夹：" + dir.getAbsolutePath());
    }
    return deleteDir;
  }

  /**
   * 将只有一个文件夹下面只有唯一的一个文件夹删除；
   *
   * @param dir
   */
  public static void removeOneDir(File dir) {
    if (!dir.isDirectory())
      return;
    File[] files = dir.listFiles();
    if (files != null && files.length > 0) {
      for (File file : files) {
        removeOneDir(file);
      }
    }
    File[] files2 = dir.listFiles();
    if (files2 != null && files2.length == 1) {
      File onlyDir = files2[0];
      if (onlyDir.isDirectory() && !onlyDir.getName().equals(DIR_NAME)) {
        System.out.println("move dir:" + onlyDir.getAbsolutePath() + " to:" + dir.getAbsolutePath());
        for (File file : onlyDir.listFiles()) {
          DirFileUtil.renameToDir(file, dir);
        }
        onlyDir.delete();
      }
    }
  }


}
