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

package com.halcyoninae.cosmos.theme.bundles;

import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import com.halcyoninae.cosmos.theme.Theme;
import com.halcyoninae.cosmos.theme.ThemeType;
import com.halcyoninae.halcyon.utils.ColorTool;

import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;

/**
 * This is the secondary light theme of the program.
 * This theme features a contrast between OneLight and
 * the native teal look from the DarkGreen color scheme.
 *
 * @author Jack Meng
 * @since 3.3
 */
public class LightGreen implements Theme {

    @Override
    public Class<? extends BasicLookAndFeel> getLAF() {
        return FlatLightFlatIJTheme.class;
    }

    @Override
    public Color getBackgroundColor() {
        return ColorTool.hexToRGBA("#cef5e6");
    }

    @Override
    public Color getForegroundColor() {
        return ColorTool.hexToRGBA("#2ed35a");
    }

    @Override
    public String getThemeName() {
        return "Light Green";
    }

    @Override
    public String getCanonicalName() {
        return "light_green";
    }

    @Override
    public ThemeType getThemeType() {
        return ThemeType.LIGHT;
    }

}
