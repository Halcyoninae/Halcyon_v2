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

package com.jackmeng.halcyoninae.cosmos.components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;

import com.jackmeng.halcyoninae.halcyon.utils.Debugger;

import java.awt.*;

public class TruncLabelUI extends BasicLabelUI {
    private int before, after;


    /**
     * @param l
     * @param fontMetrics
     * @param text
     * @param icon
     * @param viewR
     * @param iconR
     * @param textR
     * @return String
     */
    @Override
    protected String layoutCL(JLabel l, FontMetrics fontMetrics, String text, Icon icon, Rectangle viewR, Rectangle iconR,
                              Rectangle textR) {
        before = text.length();
        String s = super.layoutCL(
            l, fontMetrics, text, icon, viewR, iconR, textR);
        after = s.length();
        Debugger.warn(s);
        return s;
    }


    /**
     * @return int
     */
    public int getBefore() {
        return before;
    }


    /**
     * @return int
     */
    public int getAfter() {
        return after;
    }
}
