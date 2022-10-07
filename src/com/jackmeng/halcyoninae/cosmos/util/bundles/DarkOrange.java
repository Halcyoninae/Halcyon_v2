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

package com.jackmeng.halcyoninae.cosmos.util.bundles;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import com.jackmeng.halcyoninae.cosmos.util.Theme;
import com.jackmeng.halcyoninae.cosmos.util.ThemeType;
import com.jackmeng.halcyoninae.halcyon.utils.ColorTool;

import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;

/**
 * This is the secondary dark theme of the program.
 * It features a contrast between OneDark and an Orange
 * color.
 *
 * @author Jack Meng
 * @since 3.3
 */
public class DarkOrange implements Theme {

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
    public Color getForegroundColor2() {
        return ColorTool.hexToRGBA("#f2c29d");
    }

    /**
     * @return Color
     */
    @Override
    public Color getForegroundColor() {
        return ColorTool.hexToRGBA("#fa9548");
    }

    /**
     * @return String
     */
    @Override
    public String getThemeName() {
        return "Dark Orange";
    }

    /**
     * @return String
     */
    @Override
    public String getCanonicalName() {
        return "dark_orange";
    }


    /**
     * @return Color
     */
    @Override
    public Color getMainBackground() {
        return ColorTool.hexToRGBA("#282c34");
    }

    /**
     * @return ThemeType
     */
    @Override
    public ThemeType getThemeType() {
        return ThemeType.DARK;
    }

}
