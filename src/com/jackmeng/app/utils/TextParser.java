package com.jackmeng.app.utils;

/**
 * A utility class for text manipulation
 * 
 * @author Jack Meng
 * @since 3.0
 */
public class TextParser {
  private TextParser() {}

  /**
   * Returns a string that has been stripped based on the desired length.
   * 
   * For example:
   * 
   * strip("helloworld", 2) --> "he..."
   * 
   * @param str The string to strip
   * @param validLength The valid length (from 1)
   * @return A string that has been stripped based on the desired length
   */
  public static String strip(String str, int validLength) {
    return str.length() > validLength ? str.substring(0, validLength) + "..." : str;
  }
}