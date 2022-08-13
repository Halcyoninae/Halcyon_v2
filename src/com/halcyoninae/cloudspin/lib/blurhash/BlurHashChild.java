/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * CloudSpin a graphics library for image manipulation is licensed under the following
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

package com.halcyoninae.cloudspin.lib.blurhash;

import java.util.Arrays;

/**
 * A low level implementation of the BlurHash
 * algorithm from here: https://blurha.sh/
 * <p>
 * Original ported from an early version of this
 * program.
 *
 * @author Jack Meng
 * @since 1.5
 */
public final class BlurHashChild {
    /**
     * Represents the colors;
     */
    static double[] __ll = new double[256];

    static {
        for (int i = 0; i < __ll.length; i++) {
            double _m = i / 255.0d;
            __ll[i] = _m <= 0.04045 ? (_m / 12.92) : (Math.pow((_m + 0.055) / 1.055, 2.4));
        }
    }

    private BlurHashChild() {
    }

    /**
     * Finds a max value in an array (2D)
     *
     * @param val The array
     * @return A max value
     */
    public static double max(double[][] val) {
        double max = 0;
        for (int i = 0; i < val.length; i++) {
            for (int j = 0; j < val[i].length; j++) {
                if (val[i][j] > max) {
                    max = val[i][j];
                }
            }
        }
        return max;
    }

    /**
     * Converts the given number to be within the linear range
     *
     * @param val The number to convert
     * @return The converted number
     */
    public static double to_linear(int val) {
        return val < 0 ? __ll[0] : (val >= 256 ? __ll[255] : __ll[val]);
    }

    /**
     * Converts the given number to be within the sRGB range
     *
     * @param val The number to convert
     * @return The converted number
     */
    public static int _as_linear(double val) {
        int _l = Arrays.binarySearch(__ll, val);
        if (_l < 0) {
            _l = ~_l;
        }
        return _l < 0 ? 0 : (_l >= 256 ? 255 : _l);
    }

    /**
     * Encodes the given values into a BlurHash
     *
     * @param pixels     The pixels to encode
     * @param width      The width of the image
     * @param height     The height of the image
     * @param componentX The x-component of the center of the image
     * @param componentY The y-component of the center of the image
     * @return The encoded BlurHash as a String
     */
    public static String enc(int[] pixels, int width, int height, int componentX, int componentY) {
        double[][] factors = new double[componentX * componentY][3];
        for (int j = 0; j < componentY; j++) {
            for (int i = 0; i < componentX; i++) {
                double normalisation = (i == 0 && j == 0) ? 1 : 2;
                double r = 0, g = 0, b = 0;
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        double basis = normalisation
                                * Math.cos((Math.PI * i * x) / width)
                                * Math.cos((Math.PI * j * y) / height);
                        int pixel = pixels[y * width + x];
                        r += basis * to_linear((pixel >> 16) & 0xff);
                        g += basis * to_linear((pixel >> 8) & 0xff);
                        b += basis * to_linear(pixel & 0xff);
                    }
                }
                double scale = 1.0 / (width * height);
                int index = j * componentX + i;
                factors[index][0] = r * scale;
                factors[index][1] = g * scale;
                factors[index][2] = b * scale;
            }
        }

        int factorsLength = factors.length;
        char[] hash = new char[4 + 2 * factorsLength];

        long sizeFlag = componentX + componentY * 9l - 10;
        base_83.encode(sizeFlag, 1, hash, 0);

        double maximumValue;
        if (factorsLength > 1) {
            double actualMaximumValue = max(factors);
            double quantisedMaximumValue = Math
                    .floor(Math.max(0, Math.min(82, Math.floor(actualMaximumValue * 166 - 0.5))));
            maximumValue = (quantisedMaximumValue + 1) / 166;
            base_83.encode(Math.round(quantisedMaximumValue), 1, hash, 1);
        } else {
            maximumValue = 1;
            base_83.encode(0, 1, hash, 1);
        }

        double[] dc = factors[0];
        base_83.encode(base_83.encodeDC(dc), 4, hash, 2);

        for (int i = 1; i < factorsLength; i++) {
            base_83.encode(base_83.encodeAC(factors[i], maximumValue), 2, hash, 4 + 2 * i);
        }
        return new String(hash);
    }

    /**
     * Decodes the given BlurHash into an array of pixels
     *
     * @param blurHash The BlurHash to decode (String)
     * @param width    The width of the image
     * @param height   The height of the image
     * @param punch    The punch value of the image; often regarded as the "sharpness" of the image
     * @return The decoded pixels
     */
    public static int[] dec(String blurHash, int width, int height, double punch) {
        int blurHashLength = blurHash.length();
        if (blurHashLength < 6) {
            throw new IllegalArgumentException("BlurHash must be at least 6 characters long");
        }
        int sizeInfo = base_83.decode(blurHash.substring(0, 1));
        int sizeY = sizeInfo / 9 + 1;
        int sizeX = sizeInfo % 9 + 1;

        int quantMaxValue = base_83.decode(blurHash.substring(1, 2));
        double rmV = (quantMaxValue + 1) / 166.0 * punch;

        double[][] colors = new double[sizeX * sizeY][3];
        base_83.decodeDC(blurHash.substring(2, 6), colors[0]);
        for (int i = 1; i < sizeX * sizeY; i++) {
            base_83.decodeAC(blurHash.substring(4 + i * 2, 6 + i * 2), rmV, colors[i]);
        }
        int[] pixels = new int[width * height];
        int pos = 0;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                double r = 0, g = 0, b = 0;
                for (int y = 0; y < sizeY; y++) {
                    for (int x = 0; x < sizeX; x++) {
                        double basic = Math.cos(Math.PI * x * i / width) *
                                Math.cos(Math.PI * y * j / height);
                        double[] color = colors[x + y * sizeX];
                        r += (color[0] * basic);
                        g += (color[1] * basic);
                        b += (color[2] * basic);
                    }
                }
                pixels[pos++] = 255 << 24 | (_as_linear(r) & 255) << 16 |
                        (_as_linear(g) & 255) << 8 | (_as_linear(b) & 255);
            }
        }
        return pixels;
    }
}
