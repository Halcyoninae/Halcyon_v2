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

package com.jackmeng.halcyon.constant;

import java.awt.Color;

import com.jackmeng.halcyon.utils.ColorTool;

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
