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

package com.jackmeng.halcyoninae.cosmos.theme.bundles;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import com.jackmeng.halcyoninae.cosmos.theme.Theme;
import com.jackmeng.halcyoninae.cosmos.theme.ThemeType;
import com.jackmeng.halcyoninae.halcyon.utils.ColorTool;

import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;

/**
 * This is the default dark theme of the program.
 * <p>
 * It contrasts the OneDark background color, with the
 * tealish foreground colors.
 *
 * @author Jack Meng
 * @since 3.3
 */
public class DarkGreen implements Theme {


    /**
     * @return
     */
    @Override
    public Class<? extends BasicLookAndFeel> getLAF() {
        return FlatAtomOneDarkIJTheme.class;
    }


    /**
     * @return Color
     */
    @Override
    public Color getBackgroundColor() {
        return ColorTool.hexToRGBA("#cef5e6");
    }


    /**
     * @return Color
     */
    @Override
    public Color getForegroundColor() {
        return ColorTool.hexToRGBA("#89EDA4");
    }


    /**
     * @return String
     */
    @Override
    public String getThemeName() {
        return "Dark Green";
    }


    /**
     * @return String
     */
    @Override
    public String getCanonicalName() {
        return "dark_green";
    }


    /**
     * @return ThemeType
     */
    @Override
    public ThemeType getThemeType() {
        return ThemeType.DARK;
    }

}
