package com.jackmeng.halcyoninae.cloudspin.lib.hinter;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class GradientImg {
    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public static final float[] STABLE_MATRIX_SRC_IN = { 0F, 1F };

    private GradientImg() {
    }


    /**
     * @param img
     * @param p1x
     * @param p1y
     * @param p2x
     * @param p2y
     * @param matrix
     * @param s_o
     * @param e_o
     * @return BufferedImage
     */
    private static BufferedImage __modify_matrix_alphamask_(BufferedImage img, int p1x, int p1y, int p2x, int p2y,
            float[] matrix, int s_o, int e_o) {
        if (img != null && matrix.length > 0) {
            BufferedImage mask_able = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = mask_able.createGraphics();
            LinearGradientPaint lgp = new LinearGradientPaint(new Point(p1x, p1y), new Point(p2x, p2y), matrix,
                    new Color[] { new Color(0, 0, 0, s_o), new Color(0, 0, 0, e_o) });
            g2d.setPaint(lgp);
            g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
            g2d.dispose();
            return mask_able;
        }
        return img;
    }


    /**
     * @param img
     * @param img2
     * @return BufferedImage
     */
    private static BufferedImage __apply_img_source_top_(BufferedImage img, BufferedImage img2) {
        if (img != null) {
            int width = img2.getWidth();
            int height = img2.getHeight();
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bi.createGraphics();
            int x = (width - img.getWidth()) / 2;
            int y = (height - img.getHeight()) / 2;
            g2d.drawImage(img, x, y, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
            g2d.drawImage(img2, 0, 0, null);
            g2d.dispose();
            return bi;
        }
        return null;
    }


    /**
     * @param img
     * @param startOpacity
     * @param endOpacity
     * @param directionFade
     * @return BufferedImage
     */
    public static BufferedImage createGradientFade(BufferedImage img, int startOpacity, int endOpacity,
            int directionFade) {
        if (img != null) {
            if (directionFade == TOP) {
                return __apply_img_source_top_(img,
                        __modify_matrix_alphamask_(img, 0, 0, 0, img.getHeight(), STABLE_MATRIX_SRC_IN, startOpacity,
                                endOpacity));
            } else if (directionFade == BOTTOM) {
                return __apply_img_source_top_(img, __modify_matrix_alphamask_(img, 0, img
                        .getHeight(), 0, 0, STABLE_MATRIX_SRC_IN, startOpacity,
                        endOpacity));
            } else if (directionFade == LEFT) {
                return __apply_img_source_top_(img, __modify_matrix_alphamask_(img, img.getWidth(), img
                        .getHeight() / 2, 0, img.getHeight() / 2, STABLE_MATRIX_SRC_IN, startOpacity,
                        endOpacity));
            } else if (directionFade == RIGHT) {
                return __apply_img_source_top_(img, __modify_matrix_alphamask_(img, img.getWidth(), img
                        .getHeight() / 2, 0, img.getHeight() / 2, STABLE_MATRIX_SRC_IN, startOpacity,
                        endOpacity));
            }
        }
        return img;
    }
}
