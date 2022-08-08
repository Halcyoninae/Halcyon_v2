package com.halcyoninae.cosmos.components.moreapps;

import com.halcyoninae.halcyon.constant.ColorManager;

import javax.swing.*;
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
