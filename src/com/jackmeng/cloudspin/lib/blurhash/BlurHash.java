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

package com.jackmeng.cloudspin.lib.blurhash;

import java.awt.image.BufferedImage;

import com.jackmeng.cloudspin.lib.Blur;

/**
 * The Main BlurHash Extern Class that provides
 * a High Level Method to directly interface with the
 * Child Low Level Class
 *
 * @author Jack Meng
 * @since 1.0
 * @see com.jackmeng.cloudspin.lib.Blur
 */
public class BlurHash implements Blur {

  /**
   * @param image
   * @param _x
   * @param _y
   * @param otherParams
   * @return BufferedImage
   */
  @Override
  public BufferedImage blur(BufferedImage image, int _x, int _y, Object... otherParams) {
    int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
    String hash = BlurHashChild.enc(pixels, image.getWidth(), image.getHeight(), _x, _y);
    double p = 1.0d;
    if (otherParams[0] instanceof Double) {
      if (((Double) otherParams[0]).doubleValue() > 0) {
        p = ((Double) otherParams[0]).doubleValue();
      }
    }
    int[] dec = BlurHashChild.dec(hash, image.getWidth(), image.getHeight(), p);
    BufferedImage res = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    res.setRGB(0, 0, image.getWidth(), image.getHeight(), dec, 0, image.getWidth());
    return res;
  }

}
