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

package com.jackmeng.halcyoninae.cosmos.special;

import com.jackmeng.halcyoninae.cloudspin.CloudSpinFilters;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * Blur Frame UI
 *
 * @author Jack Meng
 * @since 3.3
 */
public class BlurLayer extends LayerUI<JComponent> {
    private final transient BufferedImageOp image;
    private transient BufferedImage bufferImage;

    public BlurLayer() {

        image = new ConvolveOp(new Kernel(3, 3, CloudSpinFilters.BLUR_KERNEL), ConvolveOp.EDGE_NO_OP, null);
    }


    /**
     * @param g
     * @param c
     */
    @Override
    public void paint(Graphics g, JComponent c) {
        int w = c.getWidth();
        int h = c.getHeight();

        if (w == 0 || h == 0) {
            return;
        }

        if (bufferImage == null || bufferImage.getWidth() != w || bufferImage.getHeight() != h) {
            bufferImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D g2 = bufferImage.createGraphics();
        g2.setClip(g.getClip());
        super.paint(g2, c);
        g2.dispose();
        Graphics2D g2_2 = (Graphics2D) g;
        g2_2.drawImage(bufferImage, image, 0, 0);
    }
}
