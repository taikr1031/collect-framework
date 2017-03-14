package com.xxx.collect.core.util.date;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateCalcUtil {
  public static void main(String[] args) {
    tBetweenDate();
  }

  public static void tBetweenDate() {
    Date startDate = DateUtil.strToDate("20100101", "yyyyMMdd");
    Date endDate = DateUtil.strToDate("20100101", "yyyyMMdd");
    List<Date> list = betweenDate(startDate, endDate);
    for (Date d : list) {
      System.out.println(DateUtil.dateToStr(d, "yyyyMmdd"));
    }
  }

  /**
   * 日期列表
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public static List<Date> betweenDate(Date startDate, Date endDate) {
    Date tDate = startDate;
    List<Date> list = new ArrayList<Date>();
    int i = 1;
    if (startDate.getTime() > endDate.getTime())
      return list;
    while (tDate.getTime() <= endDate.getTime()) {
      list.add(tDate);
      tDate = DateUtil.addDay(startDate, i++);
    }
    return list;
  }

  public static long betweenSecond(Date startDate, Date endDate) {
    long d1 = startDate.getTime();
    long d2 = endDate.getTime();
    return (d2 - d1) / 1000;
  }

  public static long betweenHour(Date startDate, Date endDate) {
    return betweenSecond(startDate,endDate)/3600;
  }

  public static long betweenDay(Date startDate, Date endDate) {
    return betweenHour(startDate,endDate)/24;
  }
}
