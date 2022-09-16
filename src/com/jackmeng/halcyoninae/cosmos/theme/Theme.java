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

package com.jackmeng.halcyoninae.cosmos.theme;

import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;

/**
 * A theme is a collection of colors and fonts used to style the UI.
 * <p>
 * This interface is extended by classes providing a "Theme Bundle"
 * which can be called and searched up to find the theme to use.
 *
 * @author Jack Meng
 * @since 3.3
 */
public interface Theme {

    /**
     * Get the class of the main LAF
     *
     * @return the class enumeration of the main LAF
     */
    Class<? extends BasicLookAndFeel> getLAF();

    /**
     * The secondary foreground color
     *
     * @return the secondary foreground color
     */
    Color getForegroundColor2();

    /**
     * The primary foreground colors
     *
     * @return the primary foreground color
     */
    Color getForegroundColor();

    /**
     * The main background theme
     *
     * @return the main background color
     */
    Color getMainBackground();

    /**
     * The theme or the selection colors
     *
     * @return the selection color
     */
    String getThemeName();

    /**
     * Return a simplified version of the theme name.
     * Usually that of the property value to set the theme.
     *
     * @return the theme name
     */
    String getCanonicalName();

    /**
     * Defines what kind of theme this theme is representing based
     * on the ThemeType enum.
     *
     * @return the theme type (enum style)
     */
    ThemeType getThemeType();
}