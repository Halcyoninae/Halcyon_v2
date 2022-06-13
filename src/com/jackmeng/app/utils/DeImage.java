package com.jackmeng.app.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.*;
import javax.imageio.*;

import java.awt.geom.RoundRectangle2D;

/**
 * This is a class that modifies images that are fed to it.
 * It is primarily used to handle resources that are in image form.
 * 
 * This is a general utility class and is licensed under GPL-3.0.
 * 
 * @author Jack Meng
 * @since 2.0
 */
public class DeImage {
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
   * @param img The BufferedImage to be resized.
   * @param newW The new width of the image.
   * @param newH The new height of the image.
   * @return BufferedImage The resized image.
   */
  public static BufferedImage resizeNoDistort(BufferedImage img, int newW, int newH) {
    int w = img.getWidth();
    int h = img.getHeight();
    BufferedImage dimg = null;
    if (w > h) {
      dimg = img.getSubimage(w / 2 - h / 2, 0, h, h);
    } else {
      dimg = img.getSubimage(0, h / 2 - w / 2, w, w);
    }
    return resize(dimg, newW, newH);
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
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g.draw(new RoundRectangle2D.Float(0, 0, r.getWidth(), r.getHeight(), arcV, arcH));
    g.drawImage(r, 0, 0, null);
    g.dispose();
    return rounded;
  }

  /**
   * Converts an ImageIcon to a BufferedImage
   * @param icon The ImageIcon to be converted
   * @return BufferedImage The converted BufferedImage
   */
  public static BufferedImage imageIconToBI(ImageIcon icon) {
    return imagetoBI(icon.getImage());
  }

  /**
   * Resizes a BufferedImage
   * @param img The BufferedImage to be resized
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
        new Point(0, alphamask.getHeight() / 2),
        new float[] { 0, 1 },
        new Color[] { new Color(0, 0, 0, startOpacity), new Color(0, 0, 0, endOpacity) });
    g2d.setPaint(lgp);
    g2d.fillRect(0, 0, alphamask.getWidth(), alphamask.getHeight());
    g2d.dispose();
    return applyMask(img, alphamask, AlphaComposite.DST_IN);
  }

  /**
   * @param image  An ImageIcon from a stream.
   * @param width  The width to scale down to
   * @param height The height to scale down to
   * @return ImageIcon A modified image that has been scaled to width and height.
   */
  public static ImageIcon resizeImage(ImageIcon image, int width, int height) {
    Image img = image.getImage();
    Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(newImg);
  }
}
