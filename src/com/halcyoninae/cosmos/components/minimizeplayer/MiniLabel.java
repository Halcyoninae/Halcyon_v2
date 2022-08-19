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

package com.halcyoninae.cosmos.components.minimizeplayer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * @author Jack Meng
 * @since 3.1
 */
public class MiniLabel extends JLabel {
    private final String textproper;
    private final String ellipsis = "...";
    private final int insetsHorizontal;
    private int textproperwidth;
    private FontMetrics fontMetrics;
    private int ellipsisWidth;
    private int borderHorizontal;

    public MiniLabel(String textstart, String textproper, String textend) {
        super(textstart + textproper + textend);
        this.textproper = textproper;
        insetsHorizontal = getInsets().left + getInsets().right;
        fontMetrics = getFontMetrics(getFont());
        calculateWidths();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int availablewidth = getWidth();
                if (textproperwidth > availablewidth - (insetsHorizontal + borderHorizontal)) {
                    String clippedtextproper = textproper;
                    while (clippedtextproper.length() > 0
                            && fontMetrics.stringWidth(clippedtextproper) + ellipsisWidth > availablewidth
                            - (insetsHorizontal + borderHorizontal)) {
                        clippedtextproper = clipText(clippedtextproper);
                    }
                    setText(textstart + clippedtextproper + ellipsis + textend);
                } else {
                    setText(textstart + textproper + textend);
                }
            }
        });
    }

    private void calculateWidths() {
        if (textproper != null) {
            textproperwidth = fontMetrics.stringWidth(textproper);
        }

        if (ellipsis != null) {
            ellipsisWidth = fontMetrics.stringWidth(ellipsis);
        }
    }

    /**
     * @param font
     */
    @Override
    public void setFont(Font font) {
        super.setFont(font);
        fontMetrics = getFontMetrics(getFont());
        calculateWidths();
    }

    /**
     * @param clippedtextproper
     * @return String
     */
    private String clipText(String clippedtextproper) {
        return clippedtextproper.substring(0, clippedtextproper.length() - 1);
    }

    /**
     * @param border
     */
    @Override
    public void setBorder(Border border) {
        super.setBorder(border);
        borderHorizontal = border.getBorderInsets(this).left + border.getBorderInsets(this).right;
    }
}
