package com.jackmeng.halcyoninae.cloudspin.lib;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.jackmeng.halcyoninae.cloudspin.SpeedStyle;

/**
 * A gradient generator meant to generate some interesting
 * gradients to be used within the program.
 *
 * @author Jack Meng
 * @since 3.3
 */
public final class GradientGenerator {
    private GradientGenerator() {
    }

    /**
     * Create a gradient based on the colors given.
     * The accepted arguments are just some simple colors;
     * preferably those that are unique and not close to each other.
     *
     * @param style       the style to generate
     * @param requiredDim the dimension to generate the pattern
     * @param yi          the first color sequence
     * @param er          the second color sequence
     * @param lock        whether to make sure to lock or no lock if the random
     *                    numbers are the same
     * @return A bufferedimage of the color gradient
     */
    public static BufferedImage make(SpeedStyle style, Dimension requiredDim, Color yi, Color er, boolean lock) {
        Random r = new Random();
        BufferedImage bi = new BufferedImage(requiredDim.width, requiredDim.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        if (style.equals(SpeedStyle.QUALITY)) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        int x = r.nextInt(requiredDim.width);
        int y = r.nextInt(requiredDim.height);
        // Ok might sound crazy, but there is a chance we might run into the same
        // number, maybe...
        int x2 = r.nextInt(requiredDim.width);
        if (lock) {
            while (x2 == x) {
                x2 = r.nextInt(requiredDim.width);
                if (x2 != x)
                    break;
            }
        }
        int y2 = r.nextInt(requiredDim.height);
        if (lock) {
            while (y2 == y) {
                y2 = r.nextInt(requiredDim.height);
                if (y2 != y)
                    break;
            }
        }
        GradientPaint gp = new GradientPaint(x, y, yi, x2, y2, er, true);
        g.setPaint(gp);
        g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        g.dispose();
        return bi;
    }
}
