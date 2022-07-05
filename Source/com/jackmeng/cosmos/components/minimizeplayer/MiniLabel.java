package com.jackmeng.cosmos.components.minimizeplayer;

import javax.swing.*;
import javax.swing.border.Border;

import com.jackmeng.halcyon.debug.Debugger;

import java.awt.*;
import java.awt.event.*;

public class MiniLabel extends JLabel {
  private String textproper;
  private String ellipsis = "...";
  private int textproperwidth;
  private FontMetrics fontMetrics;
  private int ellipsisWidth;
  private int insetsHorizontal;
  private int borderHorizontal;

  public MiniLabel(String textstart, String textproper, String textend) {
    super(textstart + textproper + textend);
    this.textproper = textproper;
    insetsHorizontal = getInsets().left + getInsets().right;
    fontMetrics = getFontMetrics(getFont());
    calculateWidths();
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        int availablewidth = getWidth();
        if (textproperwidth > availablewidth - (insetsHorizontal + borderHorizontal)) {
          String clippedtextproper = textproper;
          while (clippedtextproper.length() > 0
              && fontMetrics.stringWidth(clippedtextproper) + ellipsisWidth > availablewidth
                  - (insetsHorizontal + borderHorizontal)) {
            clippedtextproper = clipText(clippedtextproper);
          }
          setText(textstart + clippedtextproper + ellipsis + textend);
        } else {
          setText(textstart + textproper + textend);
        }
      }
    });
  }

  private void calculateWidths() {
    if (textproper != null) {
      textproperwidth = fontMetrics.stringWidth(textproper);
    }

    if (ellipsis != null) {
      ellipsisWidth = fontMetrics.stringWidth(ellipsis);
    }
  }

  /**
   * @param font
   */
  @Override
  public void setFont(Font font) {
    super.setFont(font);
    fontMetrics = getFontMetrics(getFont());
    calculateWidths();
  }

  /**
   * @param clippedtextproper
   * @return String
   */
  private String clipText(String clippedtextproper) {
    return clippedtextproper.substring(0, clippedtextproper.length() - 1);
  }

  /**
   * @param border
   */
  @Override
  public void setBorder(Border border) {
    super.setBorder(border);
    borderHorizontal = border.getBorderInsets(this).left + border.getBorderInsets(this).right;
  }
}
