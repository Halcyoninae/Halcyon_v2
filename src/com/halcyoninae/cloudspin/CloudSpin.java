/*
 *  Copyright: (C) 2022 name of Jack Meng
 * CloudSpin a graphics library for image manipulation is licensed under the following
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

package com.halcyoninae.cloudspin;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class is used for general graphical manipulation.
 *
 * For example taking an image and blurring it.
 *
 * This package is intended to replace the overburdened
 * {@link com.halcyoninae.halcyon.utils.DeImage}
 * which has been here since 2.0 and features the old burden class style of
 * having a util
 * class that handles a lot of functions.
 *
 * @author Jack Meng
 * @since 3.2
 */
public final class CloudSpin {
    private CloudSpin() {
    }

    /**
     * Macro Methods
     *
     * @param img A bufferedImage
     * @return An int array representing the individual pixels of the image.
     */
    public static int[] forPixels(BufferedImage img) {
        return img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
    }

    /**
     * This method will apply a toner on the original image.
     *
     * @param img  The original image.
     * @param tone The tone of the color to shift to.
     */
    public static void colorTone(BufferedImage img, Color tone) {
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                img.setRGB(x, y, tone.getRGB());
            }
        }
    }

    /**
     * A lite util method to convert an Icon to an Image
     * preferable for use as a BufferedImage.
     *
     * @param icon An Icon to convert.
     * @return An Image after the conversion.
     */
    public static Image iconToImage(final Icon icon) {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon) icon).getImage();
        } else {
            final int w = icon.getIconWidth();
            final int h = icon.getIconHeight();
            final BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }

    public static BufferedImage grabCrop(BufferedImage target, Rectangle w) {
        int width = 0, height = 0;
        if(w.getWidth() > target.getWidth() && w.getHeight() > target.getHeight()) {
            return target;
        } else {
            // since the decimals are just stripped it will be fine.
            width = (int) w.getWidth();
            height = (int) w.getHeight();
        }
        BufferedImage cropped = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = cropped.createGraphics();
        // crop the image from (x1, y1) to (x2, y2)
        g.drawImage(target, 0, 0, width, height, (int) w.getX(), (int) w.getY(), (int) (w.getX() + w.getWidth()), (int) (w.getY() + w.getHeight()), null);
        g.dispose();
        return cropped;
    }
}
