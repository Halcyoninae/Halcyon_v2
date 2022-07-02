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

package com.jackmeng.halcyon.utils;

import de.ralleytn.simple.image.DesaturationGrayscaleFilter;
import de.ralleytn.simple.image.SimpleImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

/**
 * This is a class that modifies images that are fed to it.
 * It is primarily used to handle resources that are in image form.
 *
 * This is a general utility class and is licensed under GPL-3.0.
 *
 * @author Jack Meng
 * @since 2.0
 */
public final class DeImage {
  /**
   * Holds enum constants for the different aspects of the image.
   */
  public enum Directional {
    TOP, LEFT, RIGHT, BOTTOM;
  }

  private DeImage() {
  }

  /**
   * Turns an Image read raw from a stream to be enwrapped by a BufferedImage
   * object.
   *
   * @param image An Image from a stream.
   * @return BufferedImage A modified image that has been converted and held in a
   *         BufferedImage object.
   */
  public static BufferedImage imagetoBI(Image image) {
    BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    Graphics2D big = bi.createGraphics();
    big.drawImage(image, 0, 0, null);
    big.dispose();
    return bi;
  }

  /**
   * Combines a mask between a source image and a mask image.
   *
   * @param sourceImage The image to be masked.
   * @param maskImage   The image to be used as a mask.
   * @param method      The method to be used to combine the images.
   * @return BufferedImage The combined image.
   */
  public static BufferedImage applyMask(BufferedImage sourceImage, BufferedImage maskImage, int method) {
    BufferedImage maskedImage = null;
    if (sourceImage != null) {
      int width = maskImage.getWidth();
      int height = maskImage.getHeight();
      maskedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D mg = maskedImage.createGraphics();
      int x = (width - sourceImage.getWidth()) / 2;
      int y = (height - sourceImage.getHeight()) / 2;
      mg.drawImage(sourceImage, x, y, null);
      mg.setComposite(AlphaComposite.getInstance(method));
      mg.drawImage(maskImage, 0, 0, null);
      mg.dispose();
    }
    return maskedImage;
  }

  /**
   * Applies a grayscale to an image.
   *
   * @param sourceImage The image to be grayscaled.
   * @return BufferedImage The grayscaled image.
   */
  public static BufferedImage grayScale(BufferedImage sourceImage) {
    SimpleImage img = new SimpleImage(sourceImage);
    img = img.filter(new DesaturationGrayscaleFilter());
    return img.toBufferedImage();
  }

  /**
   * Writes a BufferedImage to a file.
   *
   * @param r    The BufferedImage to be written.
   * @param path The path to the file to be written.
   */
  public static void write(BufferedImage r, String path) {
    try {
      ImageIO.write(imagetoBI(r), "png", new File(path));
    } catch (IOException e) {
      // print the exception in red text
      System.err.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
    }
  }

  /**
   * Resizes a BufferedImage by preserving the aspect ratio.
   *
   * @param img  The BufferedImage to be resized.
   * @param newW The new width of the image.
   * @param newH The new height of the image.
   * @return BufferedImage The resized image.
   */
  public static BufferedImage resizeNoDistort(BufferedImage img, int newW, int newH) {
    int w = img.getWidth();
    int h = img.getHeight();
    BufferedImage dimg;
    if (w > h) {
      dimg = img.getSubimage(w / 2 - h / 2, 0, h, h);
    } else {
      dimg = img.getSubimage(0, h / 2 - w / 2, w, w);
    }
    return resize(dimg, newW, newH);
  }

  /**
   * Scales down an image by a scale factor
   *
   * @param src         The image to be scaled down.
   * @param scaleFactor The scale factor.
   * @return BufferedImage The scaled down image.
   */
  public static BufferedImage scaleDown(BufferedImage src, float scaleFactor) {
    SimpleImage img = new SimpleImage(src);
    img = img.scaleByFactor(scaleFactor);
    return img.toBufferedImage();
  }

  /**
   * Scales up an image to it's center
   *
   * @param img         The image to be scaled up.
   * @param scaleFactor The scale factor.
   * @return BufferedImage The scaled up image.
   */
  public static BufferedImage zoomToCenter(BufferedImage img, double scaleFactor) {
    int w = img.getWidth();
    int h = img.getHeight();
    int newW = (int) (w * scaleFactor);
    int newH = (int) (h * scaleFactor);
    BufferedImage dimg;
    if (w > h) {
      dimg = img.getSubimage(w / 2 - h / 2, 0, h, h);
    } else {
      dimg = img.getSubimage(0, h / 2 - w / 2, w, w);
    }
    return resize(dimg, newW, newH);
  }

  /**
   * Blurs an image using a Gaussian blur.
   *
   * @param srcIMG The image to be blurred.
   * @return BufferedImage The blurred image.
   */
  public static BufferedImage blurImage(BufferedImage srcIMG) {
    BufferedImage dest = srcIMG;
    ColorModel cm = dest.getColorModel();
    BufferedImage src = new BufferedImage(cm, dest.copyData(dest.getRaster().createCompatibleWritableRaster()),
        cm.isAlphaPremultiplied(), null).getSubimage(0, 0, dest.getWidth(), dest.getHeight());
    float[] matrix = new float[400];
    for (int i = 0; i < matrix.length; i++) {
      matrix[i] = 1f / matrix.length;
    }
    new ConvolveOp(new Kernel(20, 20, matrix), ConvolveOp.EDGE_NO_OP, null).filter(src, dest);
    return dest;
  }

  /**
   * Generates a rounded image with borders
   *
   * @param r         The image to be rounded
   * @param thickness The thickness of the border
   * @return BufferedImage The rounded image
   */
  public static BufferedImage createRoundedBorder(BufferedImage r, int arcH, int arcV, int thickness, Color color) {
    BufferedImage rounded = new BufferedImage(r.getWidth(), r.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = rounded.createGraphics();
    g.setColor(color);
    g.setStroke(new BasicStroke(thickness));

    g.draw(new RoundRectangle2D.Float(0, 0, r.getWidth(), r.getHeight(), arcV, arcH));
    g.drawImage(r, 0, 0, null);
    g.dispose();
    return rounded;
  }

  /**
   * Converts an ImageIcon to a BufferedImage
   *
   * @param icon The ImageIcon to be converted
   * @return BufferedImage The converted BufferedImage
   */
  public static BufferedImage imageIconToBI(ImageIcon icon) {
    return imagetoBI(icon.getImage());
  }

  /**
   * Resizes a BufferedImage
   *
   * @param img  The BufferedImage to be resized
   * @param newW The new width
   * @param newH The new height
   * @return BufferedImage The resized BufferedImage
   */
  public static BufferedImage resize(BufferedImage img, int newW, int newH) {
    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2d = dimg.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();

    return dimg;
  }

  /**
   * Makes a gradient from top to bottom.
   *
   * @param img          The source image
   * @param startOpacity The begin opacity of the gradient.
   * @param endOpacity   The end opacity of the gradient.
   * @return BufferedImage The gradient image.
   */
  public static BufferedImage createGradientVertical(BufferedImage img, int startOpacity, int endOpacity) {
    BufferedImage alphamask = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = alphamask.createGraphics();
    LinearGradientPaint lgp = new LinearGradientPaint(
        new Point(0, 0),
        new Point(0, alphamask.getHeight()),
        new float[] { 0, 1 },
        new Color[] { new Color(0, 0, 0, startOpacity), new Color(0, 0, 0, endOpacity) });
    g2d.setPaint(lgp);
    g2d.fillRect(0, 0, alphamask.getWidth(), alphamask.getHeight());
    g2d.dispose();
    return applyMask(img, alphamask, AlphaComposite.DST_IN);
  }

  /**
   * Creates a gradient from a certain side.
   *
   * @param img                   The source image
   * @param startOpacity          The begin opacity of the gradient.
   * @param endOpacity            The end opacity of the gradient.
   * @param opacityStartDirection The direction of the gradient.
   * @return BufferedImage The gradient image.
   */
  public static BufferedImage createGradient(BufferedImage img, int startOpacity, int endOpacity,
      Directional opacityStartDirection) {
    if (opacityStartDirection == Directional.TOP) {
      return createGradientVertical(img, startOpacity, endOpacity);
    } else if (opacityStartDirection == Directional.BOTTOM) {
      BufferedImage alphamask = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = alphamask.createGraphics();
      LinearGradientPaint lgp = new LinearGradientPaint(
          new Point(0, alphamask.getHeight()),
          new Point(0, 0),
          new float[] { 0, 1 },
          new Color[] { new Color(0, 0, 0, startOpacity), new Color(0, 0, 0, endOpacity) });
      g2d.setPaint(lgp);
      g2d.fillRect(0, 0, alphamask.getWidth(), alphamask.getHeight());
      g2d.dispose();
      return applyMask(img, alphamask, AlphaComposite.DST_IN);
    } else if (opacityStartDirection == Directional.LEFT) {
      BufferedImage alphamask = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = alphamask.createGraphics();
      LinearGradientPaint lgp = new LinearGradientPaint(
          new Point(0, alphamask.getHeight() / 2),
          new Point(alphamask.getWidth(), alphamask.getHeight() / 2),
          new float[] { 0, 1 },
          new Color[] { new Color(0, 0, 0, startOpacity), new Color(0, 0, 0, endOpacity) });
      g2d.setPaint(lgp);
      g2d.fillRect(0, 0, alphamask.getWidth(), alphamask.getHeight());
      g2d.dispose();
      return applyMask(img, alphamask, AlphaComposite.DST_IN);
    } else if (opacityStartDirection == Directional.RIGHT) {
      BufferedImage alphamask = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = alphamask.createGraphics();
      LinearGradientPaint lgp = new LinearGradientPaint(
          new Point(alphamask.getWidth(), alphamask.getHeight() / 2),
          new Point(0, alphamask.getHeight() / 2),
          new float[] { 0, 1 },
          new Color[] { new Color(0, 0, 0, startOpacity), new Color(0, 0, 0, endOpacity) });
      g2d.setPaint(lgp);
      g2d.fillRect(0, 0, alphamask.getWidth(), alphamask.getHeight());
      g2d.dispose();
      return applyMask(img, alphamask, AlphaComposite.DST_IN);
    }
    return img;
  }

  /**
   * @param image  An ImageIcon from a stream.
   * @param width  The width to scale down to
   * @param height The height to scale down to
   * @return ImageIcon A modified image that has been scaled to width and height.
   */
  public static ImageIcon resizeImage(ImageIcon image, int width, int height) {
    Image img = image.getImage();
    Image newimg = img.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
    return new ImageIcon(newimg);
  }
}
