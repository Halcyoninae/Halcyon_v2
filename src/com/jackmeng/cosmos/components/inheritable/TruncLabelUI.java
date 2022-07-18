package com.jackmeng.cosmos.components.inheritable;

import javax.swing.plaf.basic.BasicLabelUI;

import com.jackmeng.halcyon.debug.Debugger;

import javax.swing.*;
import java.awt.*;

public class TruncLabelUI extends BasicLabelUI {
  private int before, after;

  @Override
  protected String layoutCL(JLabel l, FontMetrics fontMetrics, String text, Icon icon, Rectangle viewR, Rectangle iconR,
      Rectangle textR) {
    before = text.length();
    String s = super.layoutCL(
        l, fontMetrics, text, icon, viewR, iconR, textR);
    after = s.length();
    Debugger.warn(s);
    return s;
  }

  public int getBefore() {
    return before;
  }

  public int getAfter() {
    return after;
  }
}
