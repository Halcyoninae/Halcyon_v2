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

package com.halcyoninae.cloudspin.lib.boxblur;

import java.awt.image.*;

import com.halcyoninae.cloudspin.enums.SpeedStyle;
import com.halcyoninae.cloudspin.lib.Blur;

import java.awt.*;

public class BoxBlur implements Blur {


  /**
   * @param image
   * @param _x
   * @param _y
   * @param otherParams
   * @return BufferedImage
   */
  @Override
  public BufferedImage blur(BufferedImage image, int _x, int _y, Object... otherParams) {
    BufferedImage mod = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
    if (((SpeedStyle) otherParams[0]).equals(SpeedStyle.GENERAL)) {
      for (int row = 0; row < image.getHeight(); row++) {
        for (int col = 0; col < image.getWidth(); col++) {
          int x = col - _x;
          int y = row - _y;
          int count = 0;
          int r = 0;
          int g = 0;
          int b = 0;
          for (int i = 0; i < _x * 2 + 1; i++) {
            for (int j = 0; j < _y * 2 + 1; j++) {
              if (x + i < 0 || x + i >= image.getWidth() || y + j < 0 || y + j >= image.getHeight()) {
                continue;
              }
              Color c = new Color(image.getRGB(x + i, y + j));
              r += c.getRed();
              g += c.getGreen();
              b += c.getBlue();
              count++;
            }
          }
          Color c = new Color(r / count, g / count, b / count);
          mod.setRGB(col, row, c.getRGB());
        }
      }
    } else if (((SpeedStyle) otherParams[0]).equals(SpeedStyle.SPEED)) {
      int radius = (int) otherParams[1];
      int size = radius;
      float weight = size * size /radius;
      float[] kernel = new float[size * size];
      for (int i = 0; i < size * size; i++) {
        kernel[i] = weight;
      }
      Kernel k = new Kernel(size, size, kernel);
      // fill the edges with blurring as well
      RenderingHints r = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
      r.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR));
      ConvolveOp op = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, r);
      op.createCompatibleDestRaster(image.getRaster());
      mod = op.filter(image, mod);
    }
    return mod;

  }
}
