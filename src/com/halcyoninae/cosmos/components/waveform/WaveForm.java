package com.halcyoninae.cosmos.components.waveform;

import javax.swing.*;

import com.halcyoninae.halcyon.constant.ColorManager;
import com.halcyoninae.halcyon.constant.Global;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

public class WaveForm extends JPanel {
  private final Path2D.Float[] waves = { new Path2D.Float(), new Path2D.Float(), new Path2D.Float() };
  private final Object lock = new Object();
  private final transient BufferedImage img = (GraphicsEnvironment.getLocalGraphicsEnvironment()
      .getDefaultScreenDevice()
      .getDefaultConfiguration()
      .createCompatibleImage(WaveFormManager.MIN_WIDTH, WaveFormManager.MIN_HEIGHT, Transparency.OPAQUE));

  public WaveForm() {
    setPreferredSize(new Dimension(WaveFormManager.MIN_WIDTH, WaveFormManager.MIN_HEIGHT));
    setOpaque(false);
  }

  public void reset() {
    synchronized(lock) {
      Graphics2D g2 = img.createGraphics();
      g2.setBackground(ColorManager.MAIN_BG_THEME);
      g2.clearRect(0, 0, img.getWidth(), img.getHeight());
      g2.dispose();
    }
  }

  public void make(float[] samples, int s_) {
    if (Global.player.getStream().getAudioFormatAbsolute() != null) {
      Path2D.Float x = waves[2];
      waves[2] = waves[1];
      waves[1] = waves[0];
      float avg = 0f;
      float hd2 = getHeight() / 2f;
      int channels = Global.player.getStream().getAudioFormatAbsolute().getChannels();
      int i = 0;
      while (i < channels && i < s_) {
        avg += samples[i++];
      }
      avg /= channels;
      x.reset();
      x.moveTo(0, hd2 - avg * hd2);
      int f_ = s_ / channels;

      for (int j, k = 0; i < s_; k++) {
        avg = 0;
        for (j = 0; j < channels; j++) {
          avg += samples[i++];
        }

        avg /= channels;

        x.lineTo((k / f_ * img.getWidth()), hd2 - avg * hd2);
      }

      waves[0] = x;
      synchronized (lock) {
        Graphics2D g2 = img.createGraphics();
        g2.setBackground(ColorManager.MAIN_BG_THEME);
        g2.clearRect(0, 0, img.getWidth(), img.getHeight());
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setPaint(ColorManager.MAIN_FG_THEME);
        g2.draw(waves[2]);
        g2.setPaint(Color.WHITE);
        g2.draw(waves[1]);
        g2.setPaint(Color.ORANGE);
        g2.draw(waves[0]);
        g2.dispose();
      }
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    synchronized(lock) {
      g.drawImage(img, 0, 0, this);
    }
  }
}
