package com.xxx.collect.base.tool.util.analysisExtName;

import com.xxx.collect.core.util.file.DirFileUtil;
import com.xxx.collect.core.util.file.FileNameUtil;

import java.io.File;
import java.util.*;

public class ExtNameAnalysis {

  public ExtNameAnalysis(File dir) {
    this.dir = dir;
    this.list = new ArrayList<>();
  }

  /**
   * 进行文件夹遍历，统计
   */
  public void doCount() {
    List<File> fileList = DirFileUtil.listFilByDeep(this.dir);
    for (File file : fileList) {
      if (FileNameUtil.isSystemFile(file.getName()))
        continue;
      String fileExtName = FileNameUtil.getExtToLowerCase(file.getName());
      Integer count = extNameMap.get(fileExtName);
      if (count == null) {
        extNameMap.put(fileExtName, 1);
      } else {
        count++;
        extNameMap.put(fileExtName, count);
      }
    }

    // 把map中的新复杂到list中，然后排序
    Set<String> keySet = extNameMap.keySet();
    for (String key : keySet) {
      Integer count = extNameMap.get(key);
      list.add(new ExtNameCounter(key, count));
    }
    Collections.sort(list);

  }


  private File dir;
  private List<ExtNameCounter> list;
  private Map<String, Integer> extNameMap = new HashMap<String, Integer>();

  public File getDir() {
    return dir;
  }

  public void setDir(File dir) {
    this.dir = dir;
  }

  public List<ExtNameCounter> getList() {
    return list;
  }

  public void setList(List<ExtNameCounter> list) {
    this.list = list;
  }

}
