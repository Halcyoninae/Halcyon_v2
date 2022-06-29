/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jackmeng.halcyon.utils;

import java.nio.charset.StandardCharsets;

/**
 * A utility class for text manipulation
 *
 * @author Jack Meng
 * @since 3.0
 */
public final class TextParser {
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
    return str != null ? str.length() > validLength ? str.substring(0, validLength) + "..." : str : "";
  }

  public static String clipText(String str) {
    return str.substring(0, str.length() - 1);
  }

  public static boolean isInteger(String str) {
    try {
      Integer.parseInt(str);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public static String parseAsPure(String str) {
    return new String(str.getBytes(StandardCharsets.UTF_16));
  }
}