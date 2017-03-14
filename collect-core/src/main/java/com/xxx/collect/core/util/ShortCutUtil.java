package com.xxx.collect.core.util;

import net.jimmc.jshortcut.JShellLink;

import java.io.File;

/**
 * 中文目录会有乱码，无法使用
 * windows 下面创建快捷方式
 * Created by Administrator on 2016/7/21 0021.
 */
public class ShortCutUtil {

  // 需要先下载jshortcut.jar直接在百度搜一下
  public static void main(String args[]) {
    File src = new File("D:\\runqianReport.log");
    File target = new File("D:\\中文runqianReport.st.log");
    copyFile(src, target);
    File path = getReal(target);
    System.out.println(path.getAbsolutePath());
  }


  /**
   * 拷贝文件夹
   * 拷贝文件,或者文件夹,自动根据src判断是文件还是文件夹
   * 如果是文件夹:src文件夹下的内容，拷贝到target文件夹下面
   *
   * @param src
   * @param target
   */
  public static void copy(File src, File target, boolean isOverride) {
    if (!src.exists())
      throw new RuntimeException("src文件不存在:" + src.getAbsolutePath());
    if (!src.isDirectory() && target.exists() && target.isFile() && !isOverride)
      return;
    if (!src.isDirectory())
      copyFile(src, target);
    else {
      target.mkdirs();
      File[] files = src.listFiles();
      if (files == null)
        return;
      for (File file : files) {
        copy(file, new File(target, file.getName()), isOverride);
      }
    }
  }

  /**
   * 创建一个快捷方式
   */
  public static void copyFile(File src, File target) {
    JShellLink link = new JShellLink();
    String writeShortCutPath = target.getAbsolutePath();
    writeShortCutPath.replaceAll("/", "\\");
    String folder = writeShortCutPath.substring(0, writeShortCutPath.lastIndexOf("\\"));
    String name = writeShortCutPath.substring(writeShortCutPath.lastIndexOf("\\") + 1, writeShortCutPath.length());
    link.setName(name);// 目的快捷方式文件夹名称
    link.setFolder(folder);// 目的快捷方式文件路径片段
    link.setPath(src.getAbsolutePath());
    link.save();
  }

  /**
   * 获取一个快捷方式真实地址
   */
  public static File getReal(File file) {
    String fileFolderPath = file.getAbsolutePath();
    // 根据快捷方式的路径和文件夹名,获取源文件夹地址
    fileFolderPath.replaceAll("/", "\\");
    String folder = fileFolderPath.substring(0, fileFolderPath.lastIndexOf("\\"));
    String name = fileFolderPath.substring(fileFolderPath.lastIndexOf("\\") + 1, fileFolderPath.length());
    JShellLink link = new JShellLink(folder, name);
    link.load();
    return new File(link.getPath());
  }

}
