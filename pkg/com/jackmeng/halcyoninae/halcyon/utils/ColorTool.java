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

package com.jackmeng.halcyoninae.halcyon.utils;

import java.awt.*;
import java.util.Random;

/**
 * A Class to manipulate Color utility
 *
 * @author Jack Meng
 * @since 2.0
 */
public final class ColorTool {
    private ColorTool() {
    }

    /**
     * Given a hex, it will return a {@link java.awt.Color} Object
     * representing the color.
     *
     * @param hex The hex to convert
     * @return Color The color object
     */
    public static Color hexToRGBA(String hex) {
        if (!hex.startsWith("#")) {
            hex = "#" + hex;
        }
        return new Color(
            Integer.valueOf(hex.substring(1, 3), 16),
            Integer.valueOf(hex.substring(3, 5), 16),
            Integer.valueOf(hex.substring(5, 7), 16));
    }

    /**
     * Convert a color object to a string hex representation.
     *
     * @param color The color to convert
     * @return String The hex representation of the color
     */
    public static String rgbTohex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }


    /**
     * @param c
     * @param percent
     * @return Color
     */
    public static Color brightenColor(Color c, int percent) {
        int r = c.getRed() + (255 - c.getRed()) * percent / 100;
        int g = c.getGreen() + (255 - c.getGreen()) * percent / 100;
        int b = c.getBlue() + (255 - c.getBlue()) * percent / 100;
        int a = c.getAlpha() - c.getAlpha() * 30 / 100;
        return new Color(r, g, b, a);
    }

    /**
     * Returns an integer array representing the standard RED GREEN BLUE
     * colors. Where arr[0]:RED arr[1]:GREEN arr[2]:BLUE. Alpha is not
     * represented
     *
     * @param c A Color object (java.awt.Color)
     * @return An integer array of length 3
     */
    public static int[] colorBreakDown(Color c) {
        return new int[]{c.getRed(), c.getGreen(), c.getBlue()};
    }

    /**
     * Get a color with 0 RED GREEN BLUE ALPHA
     *
     * @return A Color object (java.awt.Color)
     */
    public static Color getNullColor() {
        return new Color(0, 0, 0, 0);
    }

    /**
     * Returns a random color
     *
     * @return A color object (int8)
     */
    public static Color rndColor() {
        Random r = new Random();
        return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
    }
}