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

package com.jackmeng.halcyoninae.halcyon.runtime.constant;

import com.jackmeng.halcyoninae.cloudspin.CloudSpin;
import com.jackmeng.halcyoninae.cosmos.util.Theme;
import com.jackmeng.halcyoninae.cosmos.util.ThemeBundles;
import com.jackmeng.halcyoninae.halcyon.utils.ColorTool;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;
import com.jackmeng.locale.Localized;

import java.awt.*;
import java.awt.image.*;

import javax.swing.ImageIcon;

/**
 * This interface holds constants for any color values that
 * may be used throughout the program for
 * GUI based colors.
 *
 * @author Jack Meng
 * @since 3.0
 */
public final class ColorManager {
    @Localized public static Theme programTheme        = ThemeBundles.getDefaultTheme();
    @Localized public static Color MAIN_BG_THEME       = programTheme.getMainBackground();
    @Localized public static Color BORDER_THEME        = ColorTool.hexToRGBA("#5F657D");
    @Localized public static Color MAIN_FG_THEME       = programTheme.getForegroundColor();
    @Localized public static Color MAIN_FG_FADED_THEME = ColorTool.brightenColor(MAIN_FG_THEME, 30);
    @Localized public static String MAIN_FG2_STR       = ColorTool.rgbTohex(programTheme.getForegroundColor2());
    @Localized public static String MAIN_FG_STR        = ColorTool.rgbTohex(programTheme.getForegroundColor());
    @Localized public static Color MAIN_FG2_THEME      = programTheme.getForegroundColor2();
    @Localized public static Color MAIN_TEXT_THEME     = ColorTool.hexToRGBA("#9499a2");

    private ColorManager() {
    }

    /**
     * This static global method attempts to refresh
     * the above public fields for colors.
     */
    @Localized
    public static void refreshColors() {
        MAIN_FG_THEME                       = programTheme.getForegroundColor();
        MAIN_FG_STR                         = ColorTool.rgbTohex(programTheme.getForegroundColor());
        MAIN_FG2_THEME                      = programTheme.getForegroundColor2();
    }

    /**
     * This method uses the current static theme
     * to hue the image
     * @param g An ImageIcon
     * @return An ImageIcon with a tinted hue from the current theme
     *
     * @see #themedIcon(ImageIcon, Color)
     */
    @Localized
    public static ImageIcon hueTheme(ImageIcon g) {
        return themedIcon(g, MAIN_FG_THEME);
    }

    /**
     * This method hues an image icon
     *
     * @param t An ImageIcon
     * @param theme A java.awt.Color object to use
     * @return A new ImageIcon object
     */
   public static ImageIcon themedIcon(ImageIcon t, Color theme) {
        BufferedImage img = DeImage.imageIconToBI(t);
        CloudSpin.hueImage(img, ColorTool.colorBreakDown(theme));
        return new ImageIcon(img);
    }
}
