package com.xxx.collect.core.service;

import com.xxx.collect.core.model.SysTempFile;
import com.xxx.collect.core.util.SecurityUtil;
import com.xxx.collect.core.util.UuidUtil;
import com.xxx.collect.core.util.date.DateUtil;
import com.xxx.collect.core.util.file.FileNameUtil;
import com.xxx.collect.core.util.io.IOUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 用户上传临时文件
 * 临时文件服务，可以自定义系统之外的临时目录,建立临时文件。 日期文件夹(8位)+uuid文件夹(32位)+原始文件名
 */
@Service
public class UploadTempFileService {


  @Value("#{site['dir.userUploadTempFileDir']}")
  private File tempFileDir;

  private File getDir(String date, String uuid) {
    if (SecurityUtil.isInvalidFileStorePath(date) || SecurityUtil.isInvalidFileStorePath(uuid))
      throw new RuntimeException("不安全的下载路径:" + date + "/" + uuid);
    return new File(new File(tempFileDir, date), uuid);
  }

  public SysTempFile createTempFile(String fileName) {
    String date = DateUtil.getNowTimeYyyyMMdd();
    String uuid = UuidUtil.uuid();
    File dir = getDir(date, uuid);
    dir.mkdirs();
    File file = new File(dir, fileName);
    return new SysTempFile(date, uuid, file);
  }

  public SysTempFile createTempFile(String fileName, InputStream is) throws IOException {
    SysTempFile file = createTempFile(fileName);
    file.getFile().createNewFile();
    FileOutputStream os = new FileOutputStream(file.getFile());
    IOUtil.copyAndClose(is, os);
    return file;
  }

  /**
   * url格式参考：TempFile toUrl()
   */
  public SysTempFile getTempFileByUrl(String url) {
    String fileName = FileNameUtil.getTitle(url);
    if (fileName.length() != 41)
      return null;
    return getTempFile(fileName.substring(0, 8), fileName.substring(9));
  }

  public SysTempFile getTempFile(String date, String uuid) {
    File dir = getDir(date, uuid);
    if (!dir.exists())
      return null;
    File[] files = dir.listFiles();
    if (files == null || files.length == 0)
      return null;
    return new SysTempFile(date, uuid, files[0]);
  }

}
