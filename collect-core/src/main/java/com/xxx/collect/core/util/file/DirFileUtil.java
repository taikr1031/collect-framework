package com.xxx.collect.core.util.file;

import com.xxx.collect.core.util.RegexUtil;
import com.xxx.collect.core.util.io.IOUtil;
import com.xxx.collect.core.util.string.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DirFileUtil {

  public static void main(String[] args) {
    System.out.println(changeBaseDir(new File("c:/temp/1.txt"),new File("c:\\"),new File("d:/temp1/")).getAbsolutePath());
  }

  public static String getRelativePath(File file, File root) {
    return StringUtil.deleteHead(file.getAbsolutePath(), root.getAbsolutePath());
  }


  private static final Log log = LogFactory.getLog(DirFileUtil.class);

  /**
   * 获取dir的level级别
   *
   * @param dir
   * @return
   */
  public static int getFileLevelReletiveCount(File base, File dir) {
    return getFileLevelCount(dir) - getFileLevelCount(base);
  }

  /**
   * 获取dir的level级别
   *
   * @param file
   * @return
   */
  public static int getFileLevelCount(File file) {
    return RegexUtil.findCount(file.getAbsolutePath(), "[\\\\|/]");
  }

  /**
   * 是文件夹，且目录里面为空
   *
   * @param dir
   * @return
   */
  public static boolean isEmptyDir(File dir) {
    return dir.exists() && dir.isDirectory() && (dir.listFiles() == null || dir.listFiles().length == 0);
  }

  /**
   * 向上递归删除空文件夹
   *
   * @param file
   */
  public static void loopUpDeleteEmptyDir(File file) {
    File dir = file.isDirectory() ? file : file.getParentFile();
    if (isEmptyDir(dir)) {
      IOUtil.fileDelete(dir);
      loopUpDeleteEmptyDir(dir.getParentFile());
    }
  }

  /**
   * 返回文件夹下面的所有文件，不包括文件夹，不递归
   */
  public static List<File> getOnlyFileListOneLevel(File dir) {
    List<File> list = new ArrayList<File>();
    File[] listFiles = dir.listFiles();
    if (listFiles != null)
      for (File file : listFiles) {
        if (!file.isDirectory())
          list.add(file);
      }
    return list;
  }

  public enum FileExistProc {
    ignore,//跳过
    exception,//异常
    override//覆盖
  }

  /**
   * 拷贝文件夹
   * 拷贝文件,或者文件夹,自动根据src判断是文件还是文件夹
   * 如果是文件夹:src文件夹下的内容，拷贝到target文件夹下面
   *
   * @param src
   * @param target
   */
  public static void copyIfExistIgnore(File src, File target) {
    copy(src, target, FileExistProc.ignore);
  }


  /**
   * 拷贝文件夹
   * 拷贝文件,或者文件夹,自动根据src判断是文件还是文件夹
   * 如果是文件夹:src文件夹下的内容，拷贝到target文件夹下面
   *
   * @param src
   * @param target
   */
  public static void copy(File src, File target, FileExistProc existProc) {
    if (!src.exists())
      throw new RuntimeException("src文件不存在:" + src.getAbsolutePath());
    //文件处理
    if (!src.isDirectory()) {
      if (target.exists() && target.isFile()) {
        if (existProc == FileExistProc.exception)
          throw new RuntimeException("目标文件已存在:" + target.getAbsolutePath());
        if (existProc == FileExistProc.ignore) {
//          log.debug(Thread.currentThread().getName() + " - 文件存在跳过：" + target.getAbsolutePath());
          return;
        }
      }
      copyFile(src, target);
//      log.debug(Thread.currentThread().getName() + " - 成功拷贝文件：" + target.getAbsolutePath());
    }
    //文件夹递归
    else {
      target.mkdirs();
      File[] files = src.listFiles();
      if (files == null)
        return;
      for (File file : files) {
        copy(file, new File(target, file.getName()), existProc);
      }
    }
  }


  /**
   * 拷贝文件
   *
   * @param src
   * @param target
   */
  public static void copyFile(File src, File target) {
    if (!target.getParentFile().exists())
      target.getParentFile().mkdirs();
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(target);
      FileInputStream fileInputStream = new FileInputStream(src);
      IOUtil.copyAndClose(fileInputStream, fileOutputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 递归删除文件夹，包括子目录和文件，不删除文件夹本身
   */
  public static void deleteDirInner(File dir) {
    File[] listFiles = dir.listFiles();
    for (File file : listFiles) {
      delete(file);
    }
  }

  /**
   * 删除文件夹或文件，
   * 如果是文件夹：递归删除文件夹，包括子目录和文件，以及文件夹本身
   *
   * @param file
   */
  public static void delete(File file) {
    if (file.isDirectory()) {
      File[] listFiles = file.listFiles();
      for (File file2 : listFiles) {
        delete(file2);
      }
    }
    IOUtil.fileDelete(file);
  }


  /**
   * 递归循环遍历文件夹,返回所有文件夹列表，包括子目录,并按照文件路径长度升序，就是先父文件夹，后子文件夹
   */
  public static List<File> getDirListOrderByLevelAsc(File dir) {
    List<File> dirList = listDirByDeep(dir);
    Collections.sort(dirList, new Comparator<File>() {
      @Override
      public int compare(File o1, File o2) {
        return StringUtils.countOccurrencesOf(o1.getAbsolutePath(), File.separator)
            - StringUtils.countOccurrencesOf(o2.getAbsolutePath(), File.separator);
      }
    });
    return dirList;
  }

  /**
   * 递归循环遍历文件夹,返回所有文件夹列表，包括子目录,并按照文件路径长度倒序，就是先子文件夹，后父文件夹
   */
  public static List<File> getDirListOrderByLevelDesc(File dir) {
    List<File> dirList = getDirListOrderByLevelAsc(dir);
    Collections.reverse(dirList);
    return dirList;
  }

  /**
   * 获取叶子文件夹
   *
   * @param dir
   * @return
   */
  public static List<File> listLeafDir(File dir) {
    return listDirByDeep(dir).stream().filter(f -> listDirByDeep(f).isEmpty()).collect(Collectors.toList());
  }

  /**
   * 递归循环遍历文件夹,返回所有文件夹列表，包括子目录
   */
  public static List<File> listDirByDeep(File dir) {
    List<File> list = new ArrayList<File>();
    listDirByDeep(dir, list);
    return list;
  }

  /**
   * 递归循环遍历文件,返回所有文件夹列表，包括子目录
   */
  private static void listDirByDeep(File dir, List<File> list) {
    File[] files = dir.listFiles();
    if (files == null)
      return;
    for (int i = 0; i < files.length; i++) {
      if (files[i].isDirectory()) {
        list.add(files[i]);
        listDirByDeep(files[i], list);
      }
    }
  }


  /**
   * 返回文件夹下面的所有文件夹，不包括文件，不递归
   */
  public static List<File> listOneLevelDir(File dir) {
    List<File> list = new ArrayList<File>();
    File[] listFiles = dir.listFiles();
    if (listFiles != null)
      for (File file : listFiles) {
        if (file.isDirectory())
          list.add(file);
      }
    return list;
  }


  /**
   * 递归循环遍历文件,返回所有文件,和文件夹列表，包括子目录
   */
  public static List<File> listFile(File dir) {
    List<File> fileList = listFilByDeep(dir);
    fileList.addAll(getDirListOrderByLevelDesc(dir));
    return fileList;
  }

  /**
   * 按深度遍历文件,返回所有文件列表，包括子目录
   */
  public static List<File> listFileByDeep(File dir) {
    List<File> list = new ArrayList<File>();
    listFileByDeep(dir, list);
    return list;
  }

  /**
   * 递归循环遍历文件,返回所有文件列表，包括子目录
   */
  public static void listFileByDeep(File dir, List<File> list) {
    File[] files = dir.listFiles();
    if (files == null)
      return;
    for (int i = 0; i < files.length; i++) {
      if (files[i].isDirectory()) {
        list.add(files[i]);
        listFileByDeep(files[i], list);
      } else {
        list.add(files[i]);
      }
    }
  }

  /**
   * 递归循环遍历文件,返回所有文件列表，包括子目录
   */
  public static List<File> listFilByDeep(File dir) {
    List<File> list = new ArrayList<File>();
    listFilByDeep(dir, list);
    return list;
  }

  /**
   * 递归循环遍历文件,返回所有文件列表，包括子目录
   */
  public static void listFilByDeep(File dir, List<File> list) {
    if (!dir.exists())
      return;
    File[] files = dir.listFiles();
    if (files == null)
      return;
    for (int i = 0; i < files.length; i++) {
      if (files[i].isDirectory()) {
        listFilByDeep(files[i], list);
      } else {
        list.add(files[i]);
      }
    }
  }

  /**
   * 智能重命名： 1、如果发现重命名已存在，则自动在后面加 - seq 2、可以重命名文件夹
   */
  public static File renameSmart(File oldFile, String newName) {
    File newFile = new File(oldFile.getParentFile(), newName);
    return renameSmart(oldFile, newFile);
  }

  /**
   * 智能重命名： 1、如果发现重命名已存在，则自动在后面加 - seq 2、可以重命名文件夹
   *
   * @param oldFile
   * @param newFile
   */
  public static File renameSmart(File oldFile, File newFile) {
    if (!oldFile.exists()) {
      log.warn("被改名文件不存在:" + oldFile.getAbsolutePath());
    }
    if (oldFile.getAbsolutePath().equals(newFile.getAbsolutePath()))
      return oldFile;
    File notExistsFile = newNotExistsFile(newFile);

    // 如果是文件
    if (oldFile.isFile())
      renameFile(oldFile, notExistsFile);

    // 如果是文件夹
    if (oldFile.isDirectory()) {
      File[] listFiles = oldFile.listFiles();
      for (File file : listFiles) {
        renameToDir(file, notExistsFile);
      }
      IOUtil.fileDelete(oldFile);
    }

    return notExistsFile;

  }

  /**
   * 不断的在文件名后加 -seq，来创造一个不存在的文件名
   *
   * @return
   */
  public static File newNotExistsFile(File file) {
    String name = file.getName();
    File dir = file.getParentFile();
    String fileName = FileNameUtil.getTitle(name);
    String extName = FileNameUtil.getExtToLowerCase(name);
    int seq = 1;
    File newFile = new File(dir, name);
    boolean openSmart = false;
    while (newFile.exists()) {
      openSmart = true;
      if (file.isDirectory()) {
        newFile = new File(dir, fileName + "-" + seq++);
      } else {
        newFile = new File(dir, fileName + "-" + seq++ + "." + extName);
      }
    }
    if (openSmart)
      log.debug("改名是发现重名,启用智能改名：" + name + "   >>>   " + newFile.getName());
    return newFile;
  }

  /**
   * 可以重命名文件夹或文件,
   * 将文件或文件夹，
   * 改为rename为自己所在目录的一个新名字
   *
   * @param srcFile
   * @param newName
   */
  public static void rename(File srcFile, String newName) {
    if (srcFile.isDirectory())
      renameDir(srcFile, newName);
    else
      renameFile(srcFile, newName);
  }

  /**
   * 可以重命名文件夹或文件,
   * 将文件或文件夹，
   * 改为rename为自己所在目录的一个新名字
   *
   * @param srcFile
   * @param newFile
   */
  public static void rename(File srcFile, File newFile) {
    if (srcFile.isDirectory())
      renameDir(srcFile, newFile);
    else
      renameFile(srcFile, newFile);
  }


  /**
   * 即支持文件移动，也支持文件夹移动，
   * 要求 from和to在同一个盘符
   * <p>
   * 1、from如果是文件夹则把from文件夹自己连同他的下级全部移动到to文件夹下面，
   * 而不是把from文件夹里面的文件移到B文件夹下面,
   * 注意是移动到to那个目录之下，不是去替换to那个目录
   * <p>
   * 2、from如果是文件，就直接文件到to文件夹下面
   *
   * @param from  要移动的文件或者目录目录
   * @param toDir 目标文件目录
   * @throws Exception
   */
  public static void renameToDir(File from, File toDir) {
    // 目标
    IOUtil.mkdirs(toDir);
    if (from.isDirectory()) {
      // 如果是文件夹
      File[] childrens = from.listFiles();
      for (File child : childrens) {
        renameToDir(child, new File(toDir, from.getName()));
      }
      File moveDir = new File(toDir, from.getName());
      IOUtil.mkdirs(moveDir);
      // 成功，删除原文件夹
      if (!isEmptyDir(from))
        throw new RuntimeException("移动后原始文件夹非空：" + from.getAbsolutePath());
      IOUtil.fileDelete(from);
    } else {
      if (!toDir.exists())
        toDir.mkdirs();
      // 如果是文件
      File moveFile = new File(toDir, from.getName());
      if (from.getAbsolutePath().equals(moveFile.getAbsolutePath()))
        throw new RuntimeException("rename from 文件和moveFile相同:" + from.getAbsolutePath());
      // 目标文件夹下存在的话，删除
      if (moveFile.exists())
        throw new RuntimeException("目标文件存在:" + moveFile.getAbsolutePath());
      renameFile(from, moveFile);
    }
  }

  /**
   * 如果文件不存在或者失败会抛出异常
   */
  public static void renameFile(File srcFile, String newName) {
    renameFile(srcFile, new File(srcFile.getParentFile(), newName));
  }

  /**
   * 重命名文件，失败则抛出异常
   *
   * @param from
   * @param newFile
   */
  public static void renameFile(File from, File newFile) {
    if (!from.exists())
      throw new RuntimeException("重命名失败：待改名的文件不存在:" + from.getAbsolutePath());
    if (!newFile.getParentFile().exists())
      newFile.getParentFile().mkdirs();
    if (newFile.exists())
      throw new RuntimeException("重命名失败：文件已存在:" + newFile.getAbsolutePath());
    boolean renameTo = from.renameTo(newFile);
    if (!renameTo)
      throw new RuntimeException("重命名失败 :" + from.getAbsolutePath() + "  to: " + newFile.getAbsolutePath());
    if (!newFile.exists())
      throw new RuntimeException("重命名后发现目标文件实际不存在 :" + newFile.getAbsolutePath() + "  to: " + newFile.getAbsolutePath());
  }

  /**
   * 重命名文件夹
   *
   * @param from
   */
  public static File renameDir(File from, String newName) {
    File to = new File(from.getParentFile(), newName);
    renameDir(from, to);
    return to;
  }

  /**
   * 重命名文件夹
   */
  public static void renameDir(File from, File newDir) {
    if (!newDir.exists())
      newDir.mkdirs();
    File[] listFiles = from.listFiles();
    for (File file : listFiles) {
      renameToDir(file, newDir);
    }
    IOUtil.fileDelete(from);
  }

  /**
   * 改变文件或文件夹的相对基础目录
   */
  public static void renameBaseDir(File fileOrDir, File oldBaseDir, File newBaseDir) {
    File newFile = changeBaseDir(fileOrDir, oldBaseDir, newBaseDir);
    if (fileOrDir.isDirectory())
      renameDir(fileOrDir, newFile);
    else
      renameFile(fileOrDir, newFile);
  }

  /**
   * 改变文件或文件夹的相对基础目录
   */
  public static File changeBaseDir(File fileOrDir, File oldBaseDir, File newBaseDir) {
    String newFilePath = fileOrDir.getAbsolutePath();
    newFilePath = newFilePath.substring(oldBaseDir.getAbsolutePath().length(), newFilePath.length());
    File newFile = new File(newBaseDir,newFilePath);
    return newFile;
  }

  /**
   * 移动文件夹内的文件到指定目录，不包括原始文件夹本身
   *
   * @param from
   * @param to
   */
  public static void dirInnerMove(File from, File to) {
    File[] listFiles = from.listFiles();
    if (listFiles != null)
      for (File file : listFiles) {
        renameToDir(file, to);
      }
  }


  public static double fileSizeM(File target) {
    return fileSizeM(target, 0d);
  }

  /**
   * 获取文件或者文件夹的大小
   *
   * @param target
   * @param size
   * @return
   */
  public static double fileSizeM(File target, Double size) {
    if (target.isDirectory()) {
      if (target.listFiles() != null)
        for (File child : target.listFiles()) {
          double size2 = 0d;
          if (child.isDirectory()) {
            size2 = fileSizeM(child, 0d);
          } else {
            size2 = fileSizeM(child, size);
          }
          size += size2;
          // System.out.println(child.getAbsolutePath() + " +:" +
          // size2 +
          // " size:" + size);
        }
    } else
      try {
        FileInputStream is = new FileInputStream(target);
        double fileSize = is.available() * 1.00 / 1024 / 1024;
        is.close();
        return fileSize;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    return size;
  }

}
