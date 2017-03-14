package com.xxx.collect.core.util.file.query;

import com.xxx.collect.core.util.RegexUtil;
import com.xxx.collect.core.util.file.DirFileUtil;
import com.xxx.collect.core.util.file.FileNameUtil;
import com.xxx.collect.core.util.string.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * File查询工具
 * Created by Administrator on 2016/8/4 0004.
 */
public class QueryFiles {

  private static final Log log = LogFactory.getLog(QueryFiles.class);

  public static void main(String[] args) {
    List<File> files = QueryFiles.root("h:").eq((f)->f.getName().contains("备份"))
        .eqDir().orderByLevelAsc().query();
    for (File file : files) {
      log.debug(file.getAbsolutePath());
    }
  }


  private QueryFiles() {

  }

  public static QueryFiles root(String file) {
    return root(new File(file));
  }

  public static QueryFiles root(File root) {
    QueryFiles queryFiles = new QueryFiles();
    queryFiles.root = root;
    return queryFiles;
  }

  private File root;
  private boolean isIncludeRoot = false;
  private List<FileCondition> eqConds = new ArrayList<>();//等于条件
  private List<FileCondition> neConds = new ArrayList<>();//不等于条件

  private Integer eqLevel;
  private Integer ltLevel;

  private enum OrderBy {deep, levelAsc, levelDesc, fileNameLengthDesc}

  private OrderBy orderBy = OrderBy.deep;//排序方式


  /**
   * eqLevel特殊处理提高效率
   *
   * @param level
   * @return
   */
  public QueryFiles eqLevel(int level) {
    this.eqLevel = level;
    return eq(f -> DirFileUtil.getFileLevelReletiveCount(root, f) == level);
  }

  /**
   * ltLevel特殊处理提高效率
   *
   * @param level
   * @return
   */
  public QueryFiles ltLevel(int level) {
    this.ltLevel = level;
    return eq(f -> DirFileUtil.getFileLevelReletiveCount(root, f) < level);
  }

  public QueryFiles gtLevel(int level) {

    return eq(f -> DirFileUtil.getFileLevelReletiveCount(root, f) > level);
  }

  public QueryFiles orderByDeep() {
    orderBy = OrderBy.deep;
    return this;
  }

  public QueryFiles orderByLevelAsc() {
    orderBy = OrderBy.levelAsc;
    return this;
  }

  public QueryFiles orderFileNameLengthDesc() {
    orderBy = OrderBy.fileNameLengthDesc;
    return this;
  }

  public QueryFiles orderByLevelDesc() {
    orderBy = OrderBy.levelDesc;
    return this;
  }

  private boolean isInclude(File file) {
    for (FileCondition cond : eqConds) {
      if (!cond.isInclude(file))
        return false;
    }
    for (FileCondition neCond : neConds) {
      if (neCond.isInclude(file))
        return false;
    }
    return true;
  }

  private QueryFiles eq(FileCondition cond) {
    this.eqConds.add(cond);
    return this;
  }

  private QueryFiles ne(FileCondition cond) {
    this.neConds.add(cond);
    return this;
  }

  /**
   * 文件夹
   *
   * @return
   */
  public QueryFiles eqDir() {
    return eq(File::isDirectory);
  }

  /**
   * 文件
   *
   * @return
   */
  public QueryFiles eqFil() {
    return ne(File::isDirectory);
  }

  public QueryFiles eqExtName(String extName) {
    return eq(f -> extName.equals(FileNameUtil.getExtToLowerCase(f.getName())));
  }

  public QueryFiles eqExtNames(String[] extNames) {
    QueryFiles queryFiles = null;
    for (String extName : extNames) {
      queryFiles = eq(f -> extName.equals(FileNameUtil.getExtToLowerCase(f.getName())));
    }
    return queryFiles;
  }

  public QueryFiles eqNameContainStr(String contain) {
    return eq(f -> f.getName().contains(contain));
  }

  public QueryFiles neNameContainStr(String contain) {
    return ne(f -> f.getName().contains(contain));
  }

  public QueryFiles eqNameContainRegex(String contain) {
    return eq(f -> RegexUtil.contain(f.getName(), contain));
  }

  public QueryFiles neNameContainRegex(String contain) {
    return ne(f -> RegexUtil.contain(f.getName(), contain));
  }

  public QueryFiles eqName(String name) {
    return eq(f -> f.getName().equals(name));
  }

  public QueryFiles eqPathContainRegex(String contain) {
    return eq(f -> RegexUtil.contain(StringUtil.deleteHead(f.getAbsolutePath(), root.getAbsolutePath()), contain));
  }

  public QueryFiles nePathContainRegex(String contain) {
    return ne(f -> RegexUtil.contain(StringUtil.deleteHead(f.getAbsolutePath(), root.getAbsolutePath()), contain));
  }


  /**
   * 遍历
   *
   * @return
   */
  public List<File> query() {
    List<File> list = new ArrayList<File>();
    loop(root, list);
    if (orderBy == OrderBy.levelAsc)
      Collections.sort(list, (f1, f2) -> DirFileUtil.getFileLevelCount(f1) - DirFileUtil.getFileLevelCount(f2));
    if (orderBy == OrderBy.levelDesc)
      Collections.sort(list, (f1, f2) -> DirFileUtil.getFileLevelCount(f2) - DirFileUtil.getFileLevelCount(f1));
    if (orderBy == OrderBy.fileNameLengthDesc)
      Collections.sort(list, (f1, f2) -> f2.getName().length() - f1.getName().length());
    return list;
  }

  /**
   * 是否大于条件约束的最小文件夹级别
   *
   * @param dir
   * @return
   */
  private boolean isGtThenMinLevel(File dir) {
    if (eqLevel == null && ltLevel == null)
      return false;
    int level = DirFileUtil.getFileLevelReletiveCount(root, dir);
    return ((eqLevel == null || eqLevel < level)
        && (ltLevel == null || ltLevel < level));
  }

  /**
   * 采用深度遍历
   */
  private void loop(File dir, List<File> list) {
    if (!dir.equals(root) || isIncludeRoot) {
      if (isInclude(dir))
        list.add(dir);
    }
    //如果level条件(等于和小于)不符号后面不用判断了
    if (isGtThenMinLevel(dir))
      return;
    File[] files = dir.listFiles();
    if (files == null)
      return;
    for (int i = 0; i < files.length; i++) {
      File subFile = files[i];
      if (subFile.isDirectory())
        loop(subFile, list);
      else if (isInclude(subFile))
        list.add(subFile);
    }
  }

}
