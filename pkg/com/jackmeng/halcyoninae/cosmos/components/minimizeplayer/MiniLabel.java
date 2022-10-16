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

package com.jackmeng.halcyoninae.cosmos.components.minimizeplayer;

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

        ellipsisWidth = fontMetrics.stringWidth(ellipsis);
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
