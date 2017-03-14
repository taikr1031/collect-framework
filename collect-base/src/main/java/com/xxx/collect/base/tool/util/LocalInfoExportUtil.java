package com.xxx.collect.base.tool.util;

import com.xxx.collect.core.util.RegexUtil;
import com.xxx.collect.core.util.file.FileNameUtil;

import java.io.File;

/**
 * 判断是否导出标记
 * Created by Tony on 2017/1/27.
 */
public class LocalInfoExportUtil {
  public static String RESC_EXPORT_FILE_SUFFIX_REGEX = "\\[\\^!\\]$";

  //是否导出文件
  public static boolean isExportFile(File file) {
    String name = file.isDirectory()?file.getName(): FileNameUtil.getTitle(file.getName());
    return RegexUtil.contain(name, RESC_EXPORT_FILE_SUFFIX_REGEX);
  }

}
