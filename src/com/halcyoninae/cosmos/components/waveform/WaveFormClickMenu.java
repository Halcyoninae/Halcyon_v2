package com.halcyoninae.cosmos.components.waveform;

import java.awt.event.*;
import javax.swing.*;

import com.halcyoninae.halcyon.Halcyon;

public class WaveFormClickMenu extends MouseAdapter {
  private WaveFormPane e;

  public WaveFormClickMenu(WaveFormPane e) {
    this.e = e;
  }

  private void attempt(MouseEvent ex) {
    int x = ex.getX();
    int y = ex.getY();
    JPopupMenu menu = new JPopupMenu();
    menu.setBorder(BorderFactory.createEmptyBorder());
    JMenuItem close = new JMenuItem("Close");
    close.addActionListener(event_1 -> e.setVisible(!e.isVisible()));
    JMenuItem stayTop = new JMenuItem("Stay On Top");
    stayTop.addActionListener(event_1 -> e.setAlwaysOnTop(!e.isAlwaysOnTop()));
    JMenuItem hideBC = new JMenuItem("Hide Big Player");
    hideBC.addActionListener(event_1 -> Halcyon.bgt.getFrame().setVisible(false));
    JMenuItem showBC = new JMenuItem("Show Big Player");
    showBC.addActionListener(event_1 -> Halcyon.bgt.getFrame().setVisible(true));
    menu.add(close);
    menu.add(stayTop);
    menu.add(showBC);
    menu.add(hideBC);
    menu.show(e, x + 15, y);
  }

  /**
   * @param e
   */
  @Override
  public void mousePressed(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON3) {
      attempt(e);
    }
  }

  /**
   * @param e
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON3) {
      attempt(e);
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON3) {
      attempt(e);
    }
  }

}
