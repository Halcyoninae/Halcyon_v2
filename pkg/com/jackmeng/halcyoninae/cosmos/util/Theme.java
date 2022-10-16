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