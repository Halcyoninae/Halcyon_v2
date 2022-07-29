package com.halcyoninae.cosmos.components.waveform;

import javax.swing.*;

import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.Manager;

import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;

public class WaveFormPane extends JFrame implements Runnable {
  private int pX, pY;

  public WaveFormPane() {
    setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
    setUndecorated(true);
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
      }
    });
    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent me) {
        pX = me.getX();
        pY = me.getY();

      }

      @Override
      public void mouseDragged(MouseEvent me) {
        setLocation(getLocation().x + me.getX() - pX,
            getLocation().y + me.getY() - pY);

      }
    });
    addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent me) {
        setLocation(getLocation().x + me.getX() - pX,
            getLocation().y + me.getY() - pY);

      }
    });
    addMouseListener(new WaveFormClickMenu(this));
    setPreferredSize(new Dimension(WaveFormManager.MIN_WIDTH, WaveFormManager.MIN_HEIGHT));
    getContentPane().add(Global.waveForm);
  }

  @Override
  public void run() {
    pack();
    setVisible(true);
  }
}
