/*
 * Halcyon ~ exoad
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https://raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
 */

package com.jackmeng.halcyoninae.cosmos.util;

import com.jackmeng.halcyoninae.cosmos.util.bundles.*;

import java.lang.ref.WeakReference;

/**
 * A helper class to deal with all the available themes in the bundles.
 * <p>
 * The default theme for the program is the dark green theme.
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cosmos.theme.bundles.DarkGreen
 * @see com.jackmeng.halcyoninae.cosmos.util.Theme
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
