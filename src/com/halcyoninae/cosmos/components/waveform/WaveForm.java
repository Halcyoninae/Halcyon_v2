package com.halcyoninae.cosmos.components.waveform;

import javax.swing.*;

import com.halcyoninae.halcyon.constant.ColorManager;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.halcyon.utils.ColorTool;
import com.halcyoninae.tailwind.TailwindListener;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

public class WaveForm extends JPanel implements TailwindListener.FrameBufferListener {
  private Path2D.Float wave = new Path2D.Float();
  private final transient Object lock = new Object();
  private final transient BufferedImage img = (GraphicsEnvironment.getLocalGraphicsEnvironment()
      .getDefaultScreenDevice()
      .getDefaultConfiguration()
      .createCompatibleImage(WaveFormManager.MIN_WIDTH, WaveFormManager.MIN_HEIGHT, Transparency.OPAQUE));

  public WaveForm() {
    setPreferredSize(new Dimension(WaveFormManager.MIN_WIDTH, WaveFormManager.MIN_HEIGHT));
    setOpaque(false);
  }

  public void reset() {
    Graphics2D g2 = img.createGraphics();
    g2.setBackground(ColorManager.MAIN_BG_THEME);
    g2.clearRect(0, 0, img.getWidth(), img.getHeight());
    g2.dispose();
  }


  /**
   * @param samples
   */
  public void make(float[] samples) {
    if(Global.player.getStream().getAudioFormatAbsolute() != null) {
      // draw rectangles for each sample
      Graphics2D g2 = img.createGraphics();
      g2.setBackground(ColorManager.MAIN_BG_THEME);
      g2.clearRect(0, 0, img.getWidth(), img.getHeight());
      g2.setColor(ColorManager.MAIN_FG_THEME);
      g2.setStroke(new BasicStroke(1));
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      for(float f : samples) {
        float y = (float) (f * img.getHeight() / 2);
        wave.moveTo(0, y);
        wave.lineTo(img.getWidth(), y);
        g2.draw(wave);
      }
      g2.dispose();
    }
  }


  /**
   * @param samples
   * @param svalid
   */
  public void make(float[] samples, int svalid) {
    if (Global.player.getStream().getAudioFormatAbsolute() != null) {
      svalid /=4;
      Path2D.Float main = wave;
      float avg = 0f;
      float hd2 = getHeight() / 2f;
      final int chnls = Global.player.getStream().getAudioFormatAbsolute().getChannels();
      int i = 0;
      while (i < chnls && i < svalid)
        avg += samples[i++];
      avg /= chnls;
      main.reset();
      main.moveTo(0, hd2 - avg * hd2);
      int fvalid = svalid / chnls;
      for (int ch, frame = 0; i < svalid; frame++) {
        avg = 0f;
        for (ch = 0; ch < chnls; ch++) {
          avg += samples[i++];
        }
        avg /= chnls;
        main.lineTo(
            (float) frame / fvalid * img.getWidth(), hd2 - avg * hd2);
      }
      wave = main;

      Graphics2D g2 = img.createGraphics();

      synchronized (lock) {
        g2.setBackground(ColorTool.hexToRGBA("#282c34"));
        g2.clearRect(0, 0, img.getWidth(), img.getHeight());
        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(ColorManager.MAIN_FG_THEME);
        g2.draw(wave);
      }

      g2.dispose();
    }
  }


  /**
   * @param g
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    synchronized (lock) {
      g.drawImage(img, 0, 0, this);
    }
  }


  /**
   * @param samples
   * @param s_valid
   */
  @Override
  public void frameUpdate(float[] samples, int s_valid) {
    make(samples, s_valid);
    repaint(20);
  }
}
