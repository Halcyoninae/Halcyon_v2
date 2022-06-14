package com.jackmeng.app.components.toppane;

import javax.swing.*;

import com.jackmeng.app.components.toppane.layout.ButtonControlTP;
import com.jackmeng.app.components.toppane.layout.InfoViewTP;
import com.jackmeng.constant.Manager;

import java.awt.*;

public class TopPane extends JPanel {
  public TopPane(InfoViewTP ifp, ButtonControlTP bctp) {
    setPreferredSize(new Dimension(Manager.TOPPANE_MIN_WIDTH, Manager.TOPPANE_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.TOPPANE_MAX_WIDTH, Manager.TOPPANE_MAX_HEIGHT));
    setMinimumSize(new Dimension(Manager.TOPPANE_MIN_WIDTH, Manager.TOPPANE_MIN_HEIGHT));
    setLayout(new BorderLayout());
    
    add(ifp, BorderLayout.PAGE_START);
    add(bctp, BorderLayout.PAGE_END);
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    super.paintComponent(g2);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  }
}
