package com.xxx.collect.base.tool.util;

import com.xxx.collect.core.util.MD5Util;
import com.xxx.collect.core.util.RegexUtil;
import com.xxx.collect.core.util.file.DirFileUtil;
import com.xxx.collect.core.util.string.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 把一个dir中的文件进行md5序列化，来起到判断dir内容是否相似的作用
 * Created by luju on 2015-08-02.
 *
 */
public class DirMd5 {

  public static void main(String[] args) {
    DirMd5 dirMd5 = new DirMd5(new File("R:\\视频素材-ae模版\\VideoHive-ae模版\\中文名2\\001白色运动"),"\\.aep$");
    System.out.println(dirMd5.getMd5());
  }

  private File dir;
  private String regexName;//文件名过滤器

  public DirMd5(File dir){
    this(dir,null);
  }

  public DirMd5(File dir,String regexName){
    this.dir  = dir;
    this.regexName = regexName;
  }

  public String getMd5(){
    StringBuilder md5 = new StringBuilder();
    List<File> fileList = DirFileUtil.listFilByDeep(dir);
    List<File> varFiles = new ArrayList<File>();
    //提取所有合法文件
    fileList.forEach(file -> {
      String name = file.getName();
      if (regexName == null || RegexUtil.contain(name, regexName)) {
        varFiles.add(file);
      }
    });
    //排序
    varFiles.sort(new Comparator<File>() {
      @Override
      public int compare(File o1, File o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });

    //拼md5
    varFiles.forEach(file->{
      md5.append(MD5Util.md5(file)+"-");
    });
    String md5Str = StringUtil.deleteLast(md5.toString(), "-");

    return md5Str;
  }

}
