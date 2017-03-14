package com.xxx.collect.core.tool;

import com.xxx.collect.core.util.thread.ThreadUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 异步不停的删除文件，用于临时文件无法被删除的情况
 * Created by Tony on 2016/5/3.
 */
public class DeleteFileThread {

  private static final Log log = LogFactory.getLog(DeleteFileThread.class);

  private static List<File> fileList;

  private static Thread thread;
  private static int index;

  public static void deleteFile(File file) {
    if (file == null)
      return;
    if (fileList == null)
      fileList = Collections.synchronizedList(new ArrayList<>());
    fileList.add(file);
    if (thread == null) {
      thread = new Thread(() -> {
        while (true) {
          try {
            if (fileList.isEmpty()) {
              ThreadUtil.sleepSeccond(5);
              continue;
            }
            index++;
            if (index >= fileList.size())
              index = 0;
            File fileTmp = fileList.get(index);
            //校验，将非法数据删除
            synchronized (DeleteFileThread.class) {
              if ((fileTmp == null || !fileTmp.exists()) && index < fileList.size()) {
                fileList.remove(index);
                continue;
              }
            }
            if (fileTmp.isDirectory()) {
              if (fileTmp.listFiles() == null || fileTmp.listFiles().length == 0) {
                fileTmp.delete();
                if (fileTmp.exists())
                  fileList.add(fileTmp);
              } else {
                log.debug("待删除文件夹不为空:" + fileTmp.getAbsolutePath());
                fileList.remove(index);
              }
            } else {
              fileTmp.delete();
              File parentFile = fileTmp.getParentFile().getParentFile();
              if (parentFile.listFiles() == null || parentFile.listFiles().length == 0) {
                parentFile.delete();
                if (parentFile.exists())
                  fileList.add(parentFile);
              }
              synchronized (DeleteFileThread.class) {
                if (!fileTmp.exists() && index < fileList.size() && !fileList.isEmpty())
                  fileList.remove(index);
              }
            }
            ThreadUtil.sleep(10);
          } catch (Exception e) {
            log.error(e, e);
          }
        }
      });
      thread.start();
    }

  }

}
