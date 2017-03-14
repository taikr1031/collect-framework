package com.xxx.collect.core.util.string;

import com.xxx.collect.core.util.date.DateCalcUtil;
import com.xxx.collect.core.util.date.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 把
 * 尺寸；
 * 数据；
 * <p>
 * 格式化成友好的显示方式
 */
public class TextFormat {

  private static final Log log = LogFactory.getLog(TextFormat.class);

  public static void main(String[] args) {
    System.out.println(formatTime2(3610));
    System.out.println(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
    System.out.println(formatCount(1234));
    System.out.println(friendlyDate(DateUtil.strToDate("2016-08-15 19:01", "yyyy-MM-dd HH:mm")));
  }

  private static float k = 1024;
  private static float m = k * 1024;
  private static float g = m * 1024;

  public static String formatSize(long size) {
    if (size < k) {
      return size + "b";
    }
    if (size >= k && size < m) {
      return NumberUtil.format(size / k, 2) + "k";
    }
    if (size >= m && size < g) {
      return NumberUtil.format(size / m, 2) + "m";
    }
    return NumberUtil.format(size / g, 2) + "g";
  }


  /**
   * 默认保留一位小数，如果没有小数就显示整数，不会出现  1.0 这样的形式
   * @param count
   * @return
   */
  public static String formatCount(double count) {
    return formatCount(count, 1);
  }

  /**
   * @param value
   * @param prec  精度
   * @return
   */
  public static String formatCount(double value, int prec) {
    if (value > 99999) {
      return NumberUtil.format(Double.valueOf(value / 10000), prec).toString() + "万";
    }
    if (value > 9999) {
      return NumberUtil.format(value * 1.0 / 10000, prec).toString() + "万";
    }
    if (value == Double.valueOf(Double.valueOf(value).intValue()))
      prec = 0;
    return NumberUtil.format(value, prec).toString();
  }


  public static String formatCountK(int count, int prec) {
    if (count > 999) {
      return NumberUtil.format(count * 1.0 / 1000, prec).toString() + "k";
    }
    return Integer.valueOf(count).toString();
  }

  /**
   * 过期时间给用户的友好日期显示
   *
   * @param date
   * @return
   */
  public static String friendlyDate(Date date) {
    Date now = new Date();
    //今年开始
    long nowYearStart = DateUtils.truncate(now, Calendar.YEAR).getTime();
    //今天开始
    long nowDayStart = DateUtils.truncate(now, Calendar.DAY_OF_MONTH).getTime();
    //比较时间那一天开始
    long dateDayStart = DateUtils.truncate(date, Calendar.DAY_OF_MONTH).getTime();
    //30天
    long now30DayStart = DateUtil.addDay(now, -30).getTime();

    long nowTime = now.getTime();

    long dateTime = date.getTime();
    // 去年或更早
    if (dateTime < nowYearStart) {
      return new SimpleDateFormat("y-M-d").format(date);
    }
    // 今年 - 30天
    if (dateTime < now30DayStart) {
      return new SimpleDateFormat("y-M-d").format(date);
    }
    // 排除前天
    if ((nowDayStart - dateDayStart) == (24 * 2 * 60 * 60 * 1000)) {
      return new SimpleDateFormat("前天").format(date);
    }
    // 排除昨天
    if ((nowDayStart - dateDayStart) == (24 * 60 * 60 * 1000)) {
      return new SimpleDateFormat("昨天").format(date);
    }
    // 小于今天开始：也就是剩下 30天(不包含前天，昨天)-今天开始
    if (dateTime < nowDayStart) {
      return DateCalcUtil.betweenDay(new Date(dateDayStart), new Date(nowDayStart)) + "天前";
    }
    // 今天：>1h
    if (nowTime - dateTime > 60 * 60 * 1000) {
      return DateCalcUtil.betweenHour(date, now) + "小时前";
    }
    // 今天：>1m <=1h
    if (nowTime - dateTime > 60 * 1000) {
      return (long) Math.floor((nowTime - dateTime) * 1d / 60000) + "分钟前";
    }
    // 今天：1分钟以内
    if (nowTime - dateTime >= 0) {
      return "刚刚";
    }
    log.error("友好日期格式显示遇到异常：" + date);
    return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
  }

  /**
   * 时间，单位秒，转化为友好的时间显示的格式
   *
   * @param time
   * @return
   */
  public static String getFormatTimeFormat(int time) {
    String timeFormat = "ss秒";
    if (time >= 60)
      timeFormat = "mm分 " + timeFormat;
    if (time >= 3600)
      timeFormat = "HH:mm:ss" + timeFormat;
    return timeFormat;
  }

  /**
   * 时间，单位秒，转化为友好的时间显示
   *
   * @param time
   * @return
   */
  public static String formatTime(int time) {
    return DateUtil.formatTimeSeccond(time, getFormatTimeFormat(time));
  }


  /**
   * 时间，单位秒，转化为友好的时间显示的格式
   *
   * @param time
   * @return
   */
  public static String getFormatTimeFormat2(int time) {
    String timeFormat = "s秒";
    if (time >= 60)
      timeFormat = "m分钟";
    if (time >= 3600)
      timeFormat = "H小时m分钟";
    return timeFormat;
  }

  /**
   * 时间，单位秒，转化为友好的时间显示
   *
   * @param time
   * @return
   */
  public static String formatTime2(int time) {
    String rest = DateUtil.formatTimeSeccond(time, getFormatTimeFormat2(time));
    rest = StringUtil.deleteLast(rest, "时0分钟");
    return rest;
  }

}
