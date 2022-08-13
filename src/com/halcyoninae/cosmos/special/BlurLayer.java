package com.halcyoninae.cosmos.special;

import javax.swing.*;
import javax.swing.plaf.LayerUI;

import com.halcyoninae.cloudspin.CloudSpinFilters;

import java.awt.image.*;
import java.awt.*;

/**
 * Blur Frame UI
 *
 * @author Jack Meng
 * @since 3.3
 */
public class BlurLayer extends LayerUI<JComponent> {
    private transient BufferedImage bufferImage;
    private transient BufferedImageOp image;

    public BlurLayer() {

        image = new ConvolveOp(new Kernel(3, 3, CloudSpinFilters.BLUR_KERNEL), ConvolveOp.EDGE_NO_OP, null);
    }

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
