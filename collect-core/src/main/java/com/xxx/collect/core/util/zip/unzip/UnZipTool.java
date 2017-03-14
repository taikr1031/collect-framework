package com.xxx.collect.core.util.zip.unzip;

import com.xxx.collect.core.util.file.DirFileUtil;
import com.xxx.collect.core.util.file.FileHeaderReader;
import com.xxx.collect.core.util.file.FileNameUtil;
import com.xxx.collect.core.util.io.IOUtil;
import de.innosystec.unrar.Archive;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

public class UnZipTool {
  public static void main(String[] args) throws Exception {
    File file = new File("D:\\aigei\\fileTest\\zip\\rar中文+乱码+空格+密码123456.rar");
    int unzip = unzip(file, new File(file.getParentFile(), FileNameUtil.getTitle(file.getName())));
    System.out.println("解压文件数量：" + unzip);
  }

  /**
   * 判断压缩包文件是否加密了
   *
   * @param zipFile
   * @return
   */
  public boolean isEncrypted(File zipFile) {
    String fileHeader = FileHeaderReader.getFileType(zipFile);
    try {
      if ("zip".equals(fileHeader)) {
        return new net.lingala.zip4j.core.ZipFile(zipFile).isEncrypted();
      }
      if ("rar".equals(fileHeader)) {
        return new Archive(zipFile).isEncrypted();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    throw new RuntimeException("文件头校验失败：不是zip或者rar, fileHeader=" + fileHeader);
  }

  /**
   * @param zipFile
   * @param unzipDir
   * @throws Exception
   */
  public static int unzip(File zipFile, File unzipDir) throws Exception {
    return unzip(zipFile, unzipDir, false);
  }

  /**
   * @param zipFile
   * @param unzipDir
   * @throws Exception
   */
  public static int unzip(File zipFile, File unzipDir, boolean isLoop) throws Exception {
    int unzipFileCount = 0;
    if (!zipFile.exists())
      throw new RuntimeException("文件不存在！" + zipFile.getAbsolutePath());
    String fileHeader = FileHeaderReader.getFileType(zipFile);
    if (!"zip".equals(fileHeader) && !"rar".equals(fileHeader)) {
      throw new RuntimeException("文件头校验失败：不是zip或者rar, fileHeader=" + fileHeader);
    }

    try {
      if ("zip".equals(fileHeader))
        unzipFileCount = unzipZip(zipFile, unzipDir, isLoop);
      if ("rar".equals(fileHeader)) {
        unzipFileCount = unzipRar(zipFile, unzipDir, isLoop);
      }
    } catch (Exception e) {
      // 如果解压异常就直接删除临时文件夹
      DirFileUtil.delete(unzipDir);
      throw new Exception(e);
    }
    return unzipFileCount;

  }

  /**
   * 读取ZIP文件，只适合于ZIP文件对于RAR文件无效，因为ZIP文件的压缩算法是公开的，而RAR不是
   *
   * @throws Exception
   * @author 彭建明
   * @version 1.0
   * ：ZIP文件的路径，unzippath：要解压到的文件路径
   */
  @SuppressWarnings("unchecked")
  private static int unzipZip(File zipFile, File unzipDir, boolean isloop) throws Exception {
    int fileCount = 0;
    // 根据字符流，创建ZIP文件输入流
    ZipFile zipFileObj = null;
    try {
      zipFileObj = new ZipFile(zipFile, "GBK");
      Enumeration<ZipEntry> entrys = zipFileObj.getEntries();
      // zip文件条目，表示zip文件
      ZipEntry entry;
      // 循环读取文件条目，只要不为空，就进行处理
      while (entrys.hasMoreElements()) {
        entry = entrys.nextElement();
        // System.out.println("====" + entry.getName());
        // 如果条目是文件目录，则继续执行
        if (entry.isDirectory()) {
          File dirFile = getRealFileName(unzipDir, entry.getName());
          if (!dirFile.exists())
            dirFile.mkdirs();
        } else {
          // int begin = zipfilepath.lastIndexOf("\\") + 1;
          // int end = zipfilepath.lastIndexOf(".") + 1;
          // String zipRealName = zipfilepath.substring(begin, end);
          String entryName = entry.getName();
          File entryFile = getRealFileName(unzipDir, entryName);
          FileOutputStream fos = new FileOutputStream(entryFile);
          // BufferedOutputStream bos = new
          // BufferedOutputStream(fileOutputStream);
          InputStream zis = zipFileObj.getInputStream(entry);
          IOUtil.copyAndClose(zis, fos);
          // while ((count = zis.read(date)) != -1) {
          // bos.write(date, 0, count);
          // }
          // bos.flush();
          // bos.close();
          // fileOutputStream.close();
          // zis.close();
          fileCount++;

          // 如果压缩包内还有压缩包则递归调用
          if (isloop && FileNameUtil.isZip(entryName)) {
            fileCount += unzip(entryFile, new File(unzipDir, FileNameUtil.getTitle(entryName)), isloop);
            IOUtil.fileDelete(entryFile);
          }
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (zipFileObj != null)
        zipFileObj.close();
    }
    return fileCount;
  }

  /**
   * 解压rar格式的压缩文件到指定目录下
   * 压缩文件
   * 解压目录
   *
   * @throws IOException
   * @throws RarException
   * @throws Exception
   */
  private static int unzipRar(File sourceRar, File destDir, boolean isloop) throws Exception {
    int fileCount = 0;
    Archive a = null;
    FileOutputStream fos = null;
    try {
      a = new Archive(sourceRar);
      FileHeader fh = a.nextFileHeader();
      while (fh != null) {
        // String compressFileName = fh.getFileNameString();
        // String compressFileName = fh.getFileNameW();
        String compressFileName = fh.isUnicode() ? fh.getFileNameW() : fh.getFileNameString();
        // System.out.println(compressFileName);
        File extFile = new File(destDir, compressFileName);
        // System.out.println(extFile.getAbsolutePath());
        if (fh.isDirectory()) {
          IOUtil.mkdirs(extFile);
        } else {
          // 1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
          // 2创建文件夹
          File unzipDir = extFile.getParentFile();
          IOUtil.mkdirs(unzipDir);
          // 3解压缩文件
          fos = new FileOutputStream(extFile);
          a.extractFile(fh, fos);
          fos.close();
          fos = null;
          fileCount++;
          // 如果压缩包内还有压缩包则递归调用
          if (isloop && FileNameUtil.isZip(extFile.getName())) {
            fileCount += unzipZip(extFile, new File(unzipDir, FileNameUtil.getTitle(extFile.getName())), isloop);
            IOUtil.fileDelete(extFile);
          }
        }
        fh = a.nextFileHeader();
      }
      a.close();
      a = null;
    } catch (IOException | RarException e) {
      throw e;
    } finally {
      if (fos != null) {
        try {
          fos.close();
          fos = null;
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      if (a != null) {
        try {
          a.close();
          a = null;
        } catch (IOException e) {
          throw new RuntimeException();
        }
      }
    }
    return fileCount;
  }

  private static File getRealFileName(File unzipFileDirRoot, String absFileName) {
    String[] dirs = absFileName.split("/", absFileName.length());
    // 创建文件对象
    File unzipFileDir = unzipFileDirRoot;
    if (dirs.length > 1) {
      for (int i = 0; i < dirs.length - 1; i++) {
        // 根据file抽象路径和dir路径字符串创建一个新的file对象，路径为文件的上一个目录
        unzipFileDir = new File(unzipFileDir, dirs[i]);
      }
    }
    if (!unzipFileDir.exists()) {
      unzipFileDir.mkdirs();
    }
    unzipFileDir = new File(unzipFileDir, dirs[dirs.length - 1]);
    return unzipFileDir;
  }
}