/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly..
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

package com.jackmeng.halcyoninae.cloudspin.lib.swing;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.LayerUI;

import java.awt.image.*;

public class StdBlurLayer extends LayerUI<Component> {
    private transient BufferedImageOp oImageOp;

    public StdBlurLayer(int blur, RenderingHints renderer) {
        float[] matrix = new float[blur * blur];
        float frac = 1.0F / (blur * blur);
        for (int i = 0; i < blur * blur; i++) {
            matrix[i] = frac;
        }
        oImageOp = new ConvolveOp(new Kernel(blur, blur, matrix), ConvolveOp.EDGE_ZERO_FILL, renderer);
    }

    public StdBlurLayer() {
        this(5, null);
    }

    @Override
    public void paint(Graphics g, JComponent comp) {
        if (comp.getWidth() == 0 || comp.getHeight() == 0)
            return;

        BufferedImage img = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D ig2 = img.createGraphics();
        ig2.setClip(g.getClip());
        super.paint(ig2, comp);
        ig2.dispose();
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(img, oImageOp, 0, 0);
        g2.dispose();
        g.dispose();
    }
}
