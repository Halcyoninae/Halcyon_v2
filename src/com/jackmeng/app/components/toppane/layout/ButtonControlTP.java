package com.jackmeng.app.components.toppane.layout;

import javax.swing.*;

import com.jackmeng.app.Manager;

import java.awt.*;

public class ButtonControlTP extends JPanel {
  public ButtonControlTP() {
    super();
    setPreferredSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.BUTTONCONTROL_MAX_WIDTH, Manager.BUTTONCONTROL_MAX_HEIGHT));
    setMinimumSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT));
    setOpaque(false);
  }
}
