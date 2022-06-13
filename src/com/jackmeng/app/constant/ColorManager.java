package com.jackmeng.app.constant;

import com.jackmeng.app.utils.ColorTool;
import java.awt.Color;

/**
 * This interface holds constants for any color values that
 * may be used throughout the program for
 * GUI based colors.
 *
 * @author Jack Meng
 * @since 3.0
 */
public interface ColorManager {
  public static final Color MAIN_FG_THEME = ColorTool.hexToRGBA("#FED46D");
  public static final String MAIN_FG_STR = "#FED46D";
  public static final Color BORDER_THEME = ColorTool.hexToRGBA("#5F657D");
}
