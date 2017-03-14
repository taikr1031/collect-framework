package com.xxx.collect.base.tool.util;

import com.xxx.collect.core.util.thread.ThreadUtil;
import com.xxx.collect.core.util.io.IOUtil;

import java.io.File;

/**
 * 用于测试硬盘连接是否稳定,不停的创建和删除文件
 */
public class DiskTestTool {

  private static String pans[] = { "z", "v", "y" };

  private static int count = 0;

  public static void main(String[] args) {
    while (true) {
      for (int i = 0; i < 100; i++) {
        for (String pan : pans) {
          File file = new File(pan + ":\\testdist\\" + i + ".txt");
          String content = i + "-123";
          IOUtil.stringToFile(content, file);
          String content2 = IOUtil.fileToString(file);
          if (!content.equals(content2))
            throw new RuntimeException("文件创建或读取失败");
          ThreadUtil.sleep(50);
        }
      }

      for (int i = 0; i < 100; i++) {
        for (String pan : pans) {
          File file = new File(pan + ":\\testdist\\" + i + ".txt");
          IOUtil.fileDelete(file);
          ThreadUtil.sleep(50);
        }
      }

      count++;
      System.out.println(count + " - 创建读取删除文件，每个盘各1000个");
    }
  }

}
