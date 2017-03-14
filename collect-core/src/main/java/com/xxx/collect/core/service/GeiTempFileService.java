package com.xxx.collect.core.service;

import com.xxx.collect.core.util.UuidUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * 系统处理文件是时需要的临时文件
 */
@Service
public class GeiTempFileService {

  @Value("#{site['dir.geiTmpFilesDir']}")
  private File geiTmpFilesDir;

  @PostConstruct
  public void init() {
    if (!geiTmpFilesDir.exists())
      geiTmpFilesDir.mkdirs();
  }

  public File createTempFile(String prefix, String suffix) {
    return new File(geiTmpFilesDir, prefix + UuidUtil.uuid() + suffix);
  }
}
