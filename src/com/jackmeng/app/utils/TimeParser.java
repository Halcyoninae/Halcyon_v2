package com.jackmeng.app.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeParser {
  private TimeParser() {}

  public static String getLogCurrentTime() {
    Date date = new Date();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return df.format(date);
  }

  public static String fromSeconds(int seconds) {
    int hour = seconds / 3600;
    int minute = (seconds % 3600) / 60;
    int second = seconds % 60;
    return String.format("%02d:%02d:%02d", hour, minute, second);
  }
}
