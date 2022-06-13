package com.jackmeng.app.events;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

/**
 * A class with a listener to constantly keep a divider in between a
 * threshold from the original location
 * 
 * @author Jack Meng
 * @since 3.0
 */
public class ForceDividerLocation implements PropertyChangeListener {
  private JSplitPane e;
  private int min, max;

  /**
   * @param e The JSplitPane instance
   * @param threshold The threshold (center+threshold & center-threshold)
   */
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
