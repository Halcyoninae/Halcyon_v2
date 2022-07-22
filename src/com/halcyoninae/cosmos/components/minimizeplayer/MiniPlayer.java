package com.halcyoninae.cosmos.components.minimizeplayer;

import com.halcyoninae.halcyon.constant.Global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * This class represents a frame that holds a minimized player.
 *
 * What is a minimized player?
 * A minimized player is an indirect way to display what is being
 * played, in a very compact form factor. This form factor, however,
 * is not used to directly control the audio instead is maybe to display
 * what is being played without having the main player interface
 * being shown.
 *
 * This class specifically represents the window
 * part of the miniplayer.
 *
 * @see com.halcyoninae.cosmos.components.minimizeplayer.MiniContentPane
 *
 * @author Jack Meng
 * @since 3.2
 */
public class MiniPlayer extends JFrame implements Runnable {
  private int pX, pY;
  private MiniContentPane pane;

  public MiniPlayer() {
    setPreferredSize(new Dimension(MiniPlayerManager.MINI_PLAYER_MIN_WIDTH, MiniPlayerManager.MINI_PLAYER_MIN_HEIGHT));
    setUndecorated(true);
    setAutoRequestFocus(true);
    pane = new MiniContentPane();
    Global.ifp.addInfoViewUpdateListener(pane);
    setContentPane(pane);
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
  }

  @Override
  public void run() {
    pack();
    setVisible(true);
  }
}
