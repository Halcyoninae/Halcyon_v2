/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
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

package com.jackmeng.cloudspin;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;

/**
 * This class features some very simple image manipulation
 * methods which can be useful in dealing with very small images
 * or very small requirements.
 *
 * Most of these methods have little to no process abstraction that
 * enables the user to have any form of control over what happens.
 *
 * @author Jack Meng
 * @since 3.2
 */
public final class CloudLiteSpin {
  public static void colorTone(BufferedImage img, Color tone) {
    for (int x = 0; x < img.getWidth(); x++) {
      for (int y = 0; y < img.getHeight(); y++) {
        img.set(x, y, new Color(tone.getRed(), tone.getGreen(), tone.getBlue()));
      }
    }
  }

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
}
