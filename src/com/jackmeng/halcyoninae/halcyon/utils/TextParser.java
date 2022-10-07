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

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A utility class for text manipulation
 *
 * @author Jack Meng
 * @since 3.0
 */
public final class TextParser {
    private TextParser() {
    }

    /**
     * Returns a string that has been stripped based on the desired length.
     * <p>
     * For example:
     * <p>
     * strip("helloworld", 2) --> "he..."
     *
     * @param str         The string to strip
     * @param validLength The valid length (from 1)
     * @return A string that has been stripped based on the desired length
     */
    public static String strip(String str, int validLength) {
        return str != null ? str.length() > validLength ? str.substring(0, validLength) + "..." : str : "";
    }


    /**
     * @param str
     * @param validLength
     * @return String
     */
    public static String fulfill(String str, int validLength) {
        return str != null ? str.length() > validLength ? str.substring(0, validLength) + "..."
            : str.length() < validLength ? str + getCopies(validLength, " ") : str : "";
    }


    /**
     * @param n
     * @param s
     * @return String
     */
    public static String getCopies(int n, String s) {
        return String.valueOf(s).repeat(Math.max(0, n + 1));
    }

    /**
     * @param str
     * @return String
     */
    public static String clipText(String str) {
        return str.substring(0, str.length() - 1);
    }

    /**
     * @param str
     * @return boolean
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @return String
     */
    public static String getPropertyTextEncodingName() {
        return ExternalResource.pm.get(ProgramResourceManager.KEY_USER_CHAR_SET_WRITE_TABLE).equals("utf8") ? "UTF-8"
            : (ExternalResource.pm.get(ProgramResourceManager.KEY_USER_CHAR_SET_WRITE_TABLE).equals("utf16le")
            ? "UTF-16LE"
            : "UTF-16BE");
    }

    /**
     * @param str
     * @return String
     */
    public static String parseAsPure(String str) {
        return new String(ExternalResource.pm.get(ProgramResourceManager.KEY_USER_CHAR_SET_WRITE_TABLE).equals("utf16")
            ? str.getBytes(StandardCharsets.UTF_16)
            : (ExternalResource.pm
            .get(ProgramResourceManager.KEY_USER_CHAR_SET_WRITE_TABLE).equals("utf8")
            ? str.getBytes(StandardCharsets.UTF_8)
            : (ExternalResource.pm.get(ProgramResourceManager.KEY_USER_CHAR_SET_WRITE_TABLE)
            .equals("utf16le")
            ? str.getBytes(StandardCharsets.UTF_16LE)
            : str.getBytes(StandardCharsets.UTF_16BE))));
    }

    /**
     * @return Charset
     */
    public static Charset getCharset() {
        return TextParser.getPropertyTextEncodingName().equals("UTF-8") ? StandardCharsets.UTF_8
            : (TextParser.getPropertyTextEncodingName().equals("UTF-16LE") ? StandardCharsets.UTF_16LE
            : StandardCharsets.UTF_16BE);
    }
}