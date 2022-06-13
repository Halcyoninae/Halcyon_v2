package com.jackmeng.app.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A utility class that provides methods for parsing time.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class TimeParser {

  private TimeParser() {}

  /**
   * Returns a string representation of the time in the format of {@code yyyy-MM-dd HH:mm:ss}
   * This time is taken from {@link java.lang.System#currentTimeMillis()}.
   * @return A string representation of the time in the format of {@code yyyy-MM-dd HH:mm:ss}
   */
  public static String getLogCurrentTime() {
    Date date = new Date();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return df.format(date);
  }

  /**
   * Converts the seconds into {@code HH:MM:SS} format.
   * @param seconds The seconds to be converted
   * @return A string representation of the time in the format of {@code HH:MM:SS}
   */
  public static String fromSeconds(int seconds) {
    int hour = seconds / 3600;
    int minute = (seconds % 3600) / 60;
    int second = seconds % 60;
    return String.format("%02d:%02d:%02d", hour, minute, second);
  }
}
