package com.jackmeng.app.events;

import javax.swing.JFrame;
import java.awt.event.*;

public class ForceMaxSize implements ComponentListener {
  private int a = 0, b = 0, c = 0, d = 0;

  public ForceMaxSize(JFrame c, int MAX_WIDTH, int MAX_HEIGHT, int MIN_WIDTH, int MIN_HEIGHT) {
    this.a = MAX_HEIGHT;
    this.b = MAX_WIDTH;
    this.c = MIN_HEIGHT;
    this.d = MIN_WIDTH;
  }

  @Override
  public void componentResized(ComponentEvent e) {
    JFrame de = (JFrame) e.getComponent();
    if (de.getWidth() > b) {
      de.setSize(b, a);
    }
    if (de.getHeight() > a) {
      de.setSize(b, a);
    }
    if (de.getWidth() < d) {
      de.setSize(d, de.getHeight());
    }
    if (de.getHeight() < c) {
      de.setSize(de.getWidth(), c);
    }
    
  }

  // UNUSED
  @Override
  public void componentMoved(ComponentEvent e) {
  }

  @Override
  public void componentShown(ComponentEvent e) {
  }

  @Override
  public void componentHidden(ComponentEvent e) {
  }

}
