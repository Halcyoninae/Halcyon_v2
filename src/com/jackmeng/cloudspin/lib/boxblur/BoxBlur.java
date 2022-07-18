package com.jackmeng.cloudspin.lib.boxblur;

import java.awt.image.*;

import java.awt.*;

import com.jackmeng.cloudspin.enums.SpeedStyle;
import com.jackmeng.cloudspin.lib.Blur;

public class BoxBlur implements Blur {

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
