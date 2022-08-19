/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly.
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

package com.jackmeng.halcyoninae.halcyon.constant;

import java.awt.*;

import com.jackmeng.halcyoninae.cosmos.theme.Theme;
import com.jackmeng.halcyoninae.cosmos.theme.ThemeBundles;
import com.jackmeng.halcyoninae.halcyon.utils.ColorTool;

/**
 * This interface holds constants for any color values that
 * may be used throughout the program for
 * GUI based colors.
 *
 * @author Jack Meng
 * @since 3.0
 */
public final class ColorManager {
    public static Theme programTheme = ThemeBundles.getDefaultTheme();
    // stable const
    public static Color ONE_DARK_BG = ColorTool.hexToRGBA("#21252B");
    public static Color BORDER_THEME = ColorTool.hexToRGBA("#5F657D");
    public static Color MAIN_FG_THEME = programTheme.getForegroundColor();
    public static String MAIN_FG_STR = ColorTool.rgbTohex(programTheme.getForegroundColor());
    public static Color MAIN_BG_THEME = programTheme.getBackgroundColor();
    public static Color MAIN_FG_FADED_THEME = ColorTool.brightenColor(MAIN_FG_THEME, 30);
    private ColorManager() {
    }

    public static void refreshColors() {
        MAIN_FG_THEME = programTheme.getForegroundColor();
        MAIN_FG_STR = ColorTool.rgbTohex(programTheme.getForegroundColor());
        MAIN_BG_THEME = programTheme.getBackgroundColor();
    }
}
