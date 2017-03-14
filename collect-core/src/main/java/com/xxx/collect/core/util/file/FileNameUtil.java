package com.xxx.collect.core.util.file;

import com.xxx.collect.core.util.RegexUtil;
import com.xxx.collect.core.util.string.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author 鲁炬
 */
public class FileNameUtil {
  public static void main(String[] args) {
    System.out.println(getExt("abb.xxx/abc"));
  }

  /**
   * 操作系统自带文件
   */
  private static String SYS_NAMES[] = {"Thumbs.db"};
  private static List<String> SYS_NAMES_LIST = Arrays.asList(SYS_NAMES);

  /**
   * 3d模型
   */
  public static List<String> THREE_D_LIST = Arrays.asList(new String[]{"max", "mb", "3ds", "obj", "ma", "c4d", "blend", "dae", "fbx"});

  /**
   * 视频
   */
  public static List<String> VIDEO_NAMES_LIST = Arrays.asList(new String[]{"avi", "mov", "mp4", "flv", "wmv",
      "mpg", "m2ts", "m2t", "rm", "rmvb", "m2p", "m2v", "mkv", "ts", "vob", "mpeg", "m4v", "asx"});


  /**
   * 视频模版
   */
  public static List<String> VIDEO_TEMPLATE_AE_NAMES_LIST = Arrays.asList(new String[]{"aep", "aet"});
  public static List<String> VIDEO_TEMPLATE_VSP_NAMES_LIST = Arrays.asList(new String[]{"vsp"});
  public static List<String> VIDEO_TEMPLATE_EDS_NAMES_LIST = Arrays.asList(new String[]{"ezp"});

  public static List<String> VIDEO_TEMPLATE_NAMES_LIST;

  static {
    VIDEO_TEMPLATE_NAMES_LIST = new ArrayList<>();
    VIDEO_TEMPLATE_NAMES_LIST.addAll(VIDEO_TEMPLATE_AE_NAMES_LIST);
    VIDEO_TEMPLATE_NAMES_LIST.addAll(VIDEO_TEMPLATE_VSP_NAMES_LIST);
    VIDEO_TEMPLATE_NAMES_LIST.addAll(VIDEO_TEMPLATE_EDS_NAMES_LIST);
  }

  private static String[] AUDIO_MID_NAMES = {"midi", "mid",};

  private static String[] AUDIO_NAMES = {"au", "ape", "wav", "ogg", "mp3", "m4a", "wma", "flac", "aif", "aiff"};

  /**
   * 普通图片，云存储可以直接处理的
   */
  public static List<String> PIC_NORMAL_LIST = Arrays.asList(new String[]{"jpg", "jpeg", "gif", "bmp", "png"});

  /**
   * 特殊图片格式，不能在云存储中直接缩略图的
   */
  public static List<String> PIC_SPECIAL_LIST = Arrays.asList(new String[]{"ico", "tif", "tiff", "dds", "tga"});

  /**
   * 图片模版文件
   */
  public static List<String> PIC_TEMPLATE_LIST = Arrays.asList(new String[]{"psd", "ai", "cdr", "eps"});

  /**
   * 所有图片
   */
  public static List<String> PIC_ALL_LIST = new ArrayList<String>();

  /**
   * 云存储不支持的图片
   */
  public static List<String> PIC_CLOUD_NOT_SUPORT_LIST = new ArrayList<String>();

  static {
    PIC_ALL_LIST.addAll(PIC_NORMAL_LIST);
    PIC_ALL_LIST.addAll(PIC_SPECIAL_LIST);
    PIC_ALL_LIST.addAll(PIC_TEMPLATE_LIST);

    PIC_CLOUD_NOT_SUPORT_LIST.addAll(PIC_SPECIAL_LIST);
    PIC_CLOUD_NOT_SUPORT_LIST.addAll(PIC_TEMPLATE_LIST);
  }

  private static String[] ZIP_NAMES = {"zip", "rar", "7z", "tag", "gz"};

  private static String[] DOC_NAMES = {"pdf"};


  private static String[] STATIC_RESOURCE_NAMES = {"js", "css"};

  private static List<String> audioMidNamesList = null;

  private static List<String> audioNamesList = null;

  public static String getVideoTemplateNameDesc(String format) {
    if (format == null)
      return "";
    for (String name : VIDEO_TEMPLATE_AE_NAMES_LIST) {
      if (format.contains(name))
        return "AE模版";
    }
    for (String name : VIDEO_TEMPLATE_VSP_NAMES_LIST) {
      if (format.contains(name))
        return "会声会影模版";
    }
    for (String name : VIDEO_TEMPLATE_EDS_NAMES_LIST) {
      if (format.contains(name))
        return "Edius模版";
    }
    return "";
  }

  public static List<String> getAudioMidNamesList() {
    if (audioMidNamesList == null) {
      audioMidNamesList = Arrays.asList(AUDIO_MID_NAMES);
    }
    return audioMidNamesList;
  }

  public static List<String> getAudioNamesList() {
    if (audioNamesList == null) {
      synchronized (FileNameUtil.class) {
        audioNamesList = new ArrayList<>();
        audioNamesList.addAll(Arrays.asList(AUDIO_MID_NAMES));
        audioNamesList.addAll(Arrays.asList(AUDIO_NAMES));
      }
    }
    return audioNamesList;
  }

  /**
   * 是否视频文件名
   */
  public static boolean isVideo(String fileName) {
    return VIDEO_NAMES_LIST.contains(getExtToLowerCase(fileName));
  }

  /**
   * 是否视频文件扩展名
   */
  public static boolean isVideoExtName(String extName) {
    if (VIDEO_NAMES_LIST.contains(extName))
      return true;
    return false;
  }

  /**
   * 是否视频模版文件名
   */
  public static boolean isVideoTemplate(String fileName) {
    return VIDEO_TEMPLATE_NAMES_LIST.contains(getExtToLowerCase(fileName));
  }

  /**
   * 是否视频文件扩展名
   */
  public static boolean isVideoTemplateExtName(String extName) {
    if (VIDEO_TEMPLATE_NAMES_LIST.contains(extName))
      return true;
    return false;
  }

  /**
   * 是否vedio文件名
   */
  public static boolean isThreeD(String fileName) {
    return THREE_D_LIST.contains(getExtToLowerCase(fileName));
  }

  /**
   * 是否vedio文件扩展名
   */
  public static boolean isThreeDExtName(String extName) {
    if (THREE_D_LIST.contains(extName))
      return true;
    return false;
  }

  public static boolean isSystemFile(String fileName) {
    return SYS_NAMES_LIST.contains(fileName);
  }

  /**
   * 校验文件是否合法
   */
  public static boolean illegal(String fileName) {
    return StringUtil.isFileInvalidChar(fileName) || StringUtil.isBlank(getExtToLowerCase(fileName)) || StringUtil.isBlank(getTitle(fileName));
  }

  /**
   * 获取文件扩展名,并返回小写
   */
  public static String getExtToLowerCase(String fileName) {
    return getExt(fileName).toLowerCase();
  }

  /**
   * 获取文件扩展名,并返回小写
   */
  public static String getExt(String fileName) {
    if (fileName == null)
      return "";
    if (fileName.contains("/"))
      fileName = fileName.substring(fileName.indexOf("/") + 1);
    if (fileName.contains("\\"))
      fileName = fileName.substring(fileName.indexOf("\\") + 1);
    int lastIndexOfDot = fileName.lastIndexOf(".");
    if (lastIndexOfDot > -1) {
      return fileName.substring(lastIndexOfDot + 1);
    } else {
      return "";
    }
  }

  /**
   * 改变文件名，不改变扩展名
   *
   * @param fileName
   * @return
   */
  public static String changeFileName(String fileName, Function<String, String> changeName) {
    String fileExtName = getExt(fileName);
    if (StringUtil.isBlank(fileExtName))
      return changeName.apply(fileName);
    else
      return changeName.apply(getTitle(fileName)) + "." + fileExtName;
  }

  /**
   * 获取文件名不包含扩展名
   */
  public static String getTitle(String fileName) {
    int lastIndexOfDot = fileName.lastIndexOf(".");
    if (lastIndexOfDot > -1) {
      return fileName.substring(0, lastIndexOfDot);
    } else {
      return fileName;
    }
  }

  public static boolean iZipExtName(String fileExtName) {
    for (String eName : ZIP_NAMES) {
      if (fileExtName.equals(eName))
        return true;
    }
    return false;
  }

  /**
   * 是否压缩文件
   */
  public static boolean isZip(String fileName) {
    for (String subName : ZIP_NAMES) {
      if (getExtToLowerCase(fileName).equals(subName))
        return true;
    }
    return false;
  }

  /**
   * 是否压缩文件,但是非rar文件
   */
  public static boolean isZipNotRar(String fileName) {
    if (getExtToLowerCase(fileName).equals("rar"))
      return false;
    for (String subName : ZIP_NAMES) {
      if (getExtToLowerCase(fileName).equals(subName))
        return true;
    }
    return false;
  }

  /**
   * 是否音频文件
   */
  public static boolean isAudio(String fileName) {
    return isAudioExtName(getExtToLowerCase(fileName));
  }

  /**
   * 是否音频文件
   */
  public static boolean isAudioExtName(String fileExtName) {
    if (getAudioNamesList().contains(fileExtName))
      return true;
    return false;
  }

  /**
   * 是否文档文件
   */
  public static boolean isDoc(String fileName) {
    return isDocExtName(getExtToLowerCase(fileName));
  }

  /**
   * 是否文档文件
   */
  public static boolean isDocExtName(String fileExtName) {
    for (String eName : DOC_NAMES) {
      if (fileExtName.equals(eName))
        return true;
    }
    return false;
  }

  /**
   * 是否图片文件
   */
  public static boolean isPic(String fileName) {
    return isPicExtName(getExtToLowerCase(fileName));
  }

  /**
   * 是云存储支持直接处理的图片
   */
  public static boolean isCloudSupportPicExtName(String fileExtName) {
    if (fileExtName == null)
      return false;
    String toLowerCase = fileExtName.toLowerCase();
    if (isPicExtName(toLowerCase) && !PIC_CLOUD_NOT_SUPORT_LIST.contains(toLowerCase))
      return true;
    return false;
  }

  /**
   * 是否图片文件
   */
  public static boolean isPicExtName(String fileExtName) {
    if (PIC_ALL_LIST.contains(fileExtName.toLowerCase()))
      return true;
    return false;
  }

  /**
   * 是否图片模版文件
   */
  public static boolean isPicTemplateExtName(String fileExtName) {
    if (PIC_TEMPLATE_LIST.contains(fileExtName.toLowerCase()))
      return true;
    return false;
  }

  /**
   * 是否gif文件
   */
  public static boolean isGif(String fileName) {
    if (getExtToLowerCase(fileName).equals("gif"))
      return true;
    return false;
  }

  /**
   * 是否midi文件
   */
  public static boolean isMid(String fileName) {
    if (getAudioMidNamesList().contains(getExtToLowerCase(fileName)))
      return true;
    return false;
  }

  /**
   * 是否midi文件
   */
  public static boolean isMidExt(String ext) {
    if (getAudioMidNamesList().contains(ext.toLowerCase()))
      return true;
    return false;
  }

  /**
   * 是否是静态资源
   *
   * @param fileName
   * @return
   */
  public static boolean isStaticResource(String fileName) {
    if (isPic(fileName))
      return true;
    for (String sName : STATIC_RESOURCE_NAMES) {
      if (getExtToLowerCase(fileName).equals(sName))
        return true;
    }
    return false;
  }


  /**
   * 获取相对级别,子文件夹和文件的相对级别都是从1开始
   *
   * @param dir
   * @param rootDir
   * @return
   */
  public static int getRelativeLevel(File dir, File rootDir) {
    String absolutePath = dir.getAbsolutePath().replace(rootDir.getAbsolutePath(), "");
    return RegexUtil.searchList(absolutePath, "\\\\").size();
  }

  /**
   * 获取文件夹级别
   *
   * @param dir
   * @return
   */
  public static int getPathLevel(File dir) {
    return RegexUtil.searchList(dir.getAbsolutePath(), "\\\\").size();
  }
}
