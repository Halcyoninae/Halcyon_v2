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

package com.halcyoninae.cosmos.inheritable;

import com.halcyoninae.halcyon.debug.Debugger;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;
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
