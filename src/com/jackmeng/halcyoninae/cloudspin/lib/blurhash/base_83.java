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

package com.jackmeng.halcyoninae.cloudspin.lib.blurhash;

import com.jackmeng.halcyoninae.cloudspin.helpers.math;

/**
 * Base 83 Helper Class
 *
 * @author Jack Meng
 * @since 1.0
 */
public final class base_83 {
    /**
     * A Char Table to look up.
     */
    public static final char[] TABLE = {
        '0',
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        'A',
        'B',
        'C',
        'D',
        'E',
        'F',
        'G',
        'H',
        'I',
        'J',
        'K',
        'L',
        'M',
        'N',
        'O',
        'P',
        'Q',
        'R',
        'S',
        'T',
        'U',
        'V',
        'W',
        'X',
        'Y',
        'Z',
        'a',
        'b',
        'c',
        'd',
        'e',
        'f',
        'g',
        'h',
        'i',
        'j',
        'k',
        'l',
        'm',
        'n',
        'o',
        'p',
        'q',
        'r',
        's',
        't',
        'u',
        'v',
        'w',
        'x',
        'y',
        'z',
        '#',
        '$',
        '%',
        '*',
        '+',
        ',',
        '-',
        '.',
        ':',
        ';',
        '=',
        '?',
        '@',
        '[',
        ']',
        '^',
        '_',
        '{',
        '|',
        '}',
        '~',
    };

    private base_83() {
    }

    /**
     * Encodes with Base 83.
     *
     * @param val    The value to encode
     * @param length The length of the value
     * @param buff   The buffer to write to (contains values)
     * @param offset The offset to start writing at
     * @return The encoded value as a string
     */
    public static String encode(long val, int length, char[] buff, int offset) {
        int _i = 1;
        for (int i = 1; i <= length; i++) {
            int curr = (int) val / _i % 83;
            buff[offset + length - i] = TABLE[curr];
            _i *= 83;
        }
        return new String(buff);
    }


    /**
     * @param val
     * @return long
     */
    public static long encodeDC(double[] val) {
        return ((((long) BlurHashChild._as_linear(val[0])) << 16) + (((long) BlurHashChild._as_linear(val[1])) << 8)
            + BlurHashChild._as_linear(val[2]));
    }


    /**
     * @param val
     * @param m
     * @return long
     */
    public static long encodeAC(double[] val, double m) {
        return Math
            .round((Math.floor(Math.max(0, Math.min(18, Math.floor(math.signpow(val[0] / m, 0.5) * 9 + 9.5))))) * 19 * 19
                + (Math.floor(Math.max(0, Math.min(18, Math.floor(math.signpow(val[1] / m, 0.5) * 9 + 9.5))))) * 19
                + (Math.floor(Math.max(0, Math.min(18, Math.floor(math.signpow(val[2] / m, 0.5) * 9 + 9.5))))));
    }

    /**
     * Decodes from Base 83
     *
     * @param str An Encoded String
     * @return The decoded string from base 83
     */
    public static int decode(String str) {
        int temp = 0;
        for (char c : str.toCharArray()) {
            int i = find(c);
            temp = temp * 83 + i;
        }
        return temp;
    }


    /**
     * @param str
     * @param rMv
     * @param color
     */
    public static void decodeAC(String str, double rMv, double[] color) {
        int aV = decode(str);
        int qR = aV / (19 * 19);
        int qG = (aV / 19) % 19;
        int qB = aV % 19;
        color[0] = math.signpow((qR - 9.0) / 9.0, 2.0) * rMv;
        color[1] = math.signpow((qG - 9.0) / 9.0, 2.0) * rMv;
        color[2] = math.signpow((qB - 9.0) / 9.0, 2.0) * rMv;
    }


    /**
     * @param str
     * @param colors
     */
    public static void decodeDC(String str, double[] colors) {
        int dV = decode(str);
        colors[0] = BlurHashChild.to_linear(dV >> 16);
        colors[1] = BlurHashChild.to_linear(dV >> 8 & 0xFF);
        colors[2] = BlurHashChild.to_linear(dV & 255);
    }


    /**
     * @param c
     * @return int
     */
    public static int find(char c) {
        for (int i = 0; i < TABLE.length; i++) {
            if (TABLE[i] == c) {
                return i;
            }
        }
        return -1;
    }
}
