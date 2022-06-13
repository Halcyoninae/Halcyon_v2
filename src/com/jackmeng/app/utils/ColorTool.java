package com.jackmeng.app.utils;

import java.awt.Color;

/**
 * A Class to manipulate Color utility
 * 
 * @author Jack Meng
 * @since 2.0
 */
public class ColorTool {
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
}