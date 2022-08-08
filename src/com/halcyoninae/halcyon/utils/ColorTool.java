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

package com.halcyoninae.halcyon.utils;

import java.awt.*;
import java.util.Random;

/**
 * A Class to manipulate Color utility
 *
 * @author Jack Meng
 * @since 2.0
 */
public final class ColorTool {
  private ColorTool() {
  }

  /**
   * Given a hex, it will return a {@link java.awt.Color} Object
   * representing the color.
   *
   * @param hex The hex to convert
   * @return Color The color object
   */
  public static Color hexToRGBA(String hex) {
    if (!hex.startsWith("#")) {
      hex = "#" + hex;
    }
    return new Color(
        Integer.valueOf(hex.substring(1, 3), 16),
        Integer.valueOf(hex.substring(3, 5), 16),
        Integer.valueOf(hex.substring(5, 7), 16));
  }

  /**
   * Returns a random color
   *
   * @return A color object (int8)
   */
  public static Color rndColor() {
    Random r = new Random();
    return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
  }
}