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

import java.lang.ref.WeakReference;

import com.jackmeng.halcyoninae.cosmos.theme.bundles.DarkGreen;
import com.jackmeng.halcyoninae.cosmos.theme.bundles.DarkOrange;
import com.jackmeng.halcyoninae.cosmos.theme.bundles.LightGreen;
import com.jackmeng.halcyoninae.cosmos.theme.bundles.LightOrange;

/**
 * A helper class to deal with all the available themes in the bundles.
 * <p>
 * The default theme for the program is the dark green theme.
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cosmos.theme.bundles.DarkGreen
 * @see com.jackmeng.halcyoninae.cosmos.theme.Theme
 * @since 3.3
 */
public final class ThemeBundles {
    private ThemeBundles() {
    }

    /**
     * @return The default theme for the program is the dark green theme.
     */
    public static Theme getDefaultTheme() {
        return new DarkGreen();
    }

    /**
     * Get available themes.
     *
     * @return An array representing the available themes.
     */
    public static WeakReference<Theme[]> getThemes() {
        return new WeakReference<>(new Theme[]{
                new DarkGreen(),
                new DarkOrange(),
                new LightGreen(),
                new LightOrange()
        });
    }

    /**
     * Search for a theme by its canonical name.
     *
     * @param canonicalName The canonical name of the theme.
     * @return The theme with the given canonical name, or the default if not found.
     */
    public static Theme searchFor(String canonicalName) {
        for (Theme theme : getThemes().get()) {
            if (theme.getCanonicalName().equals(canonicalName)) {
                return theme;
            }
        }
        return new DarkGreen();
    }

    /**
     * @return All of the available canonical names from the available themes.
     */
    public static String[] getCanonicNames() {
        String[] canonicNames = new String[getThemes().get().length];
        for (int i = 0; i < getThemes().get().length; i++)
            canonicNames[i] = getThemes().get()[i].getCanonicalName();
        return canonicNames;
    }
}
