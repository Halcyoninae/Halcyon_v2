package com.halcyoninae.cosmos.components.moreapps;

import javax.swing.*;

import com.halcyoninae.halcyon.constant.ColorManager;

import java.awt.*;

/**
 * @author Jack Meng
 * @since 3.3
 */
public class MoreAppsView extends JPanel {
  public MoreAppsView() {
    setPreferredSize(new Dimension(MoreAppsManager.MIN_WIDTH, MoreAppsManager.MIN_HEIGHT));
    setBorder(BorderFactory.createLineBorder(ColorManager.BORDER_THEME));
  }
}
