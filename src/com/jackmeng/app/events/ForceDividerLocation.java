package com.jackmeng.app.events;

import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

public class ForceDividerLocation implements PropertyChangeListener {
  private JSplitPane e;
  private int min, max;

  public ForceDividerLocation(JSplitPane e, int threshold) {
    this.e = e;
    min = (int) ((e.getPreferredSize().getHeight() / 2) - threshold);
    max = (int) (threshold + (e.getPreferredSize().getHeight() / 2));

  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("dividerLocation")) {
      int dividerLoc = e.getDividerLocation();
      if (dividerLoc < min) {
        e.setDividerLocation(min);
      }
      if (dividerLoc > max) {
        e.setDividerLocation(max);
      }
    }
  }
}
