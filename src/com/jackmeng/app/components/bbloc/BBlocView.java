package com.jackmeng.app.components.bbloc;

import javax.swing.*;

import com.jackmeng.app.constant.Manager;

import java.awt.*;

public class BBlocView extends JPanel {
  public BBlocView() {
    super();
    setPreferredSize(new Dimension(Manager.B_MIN_WIDTH, Manager.B_MIN_HEIGHT));
    setMinimumSize(new Dimension(Manager.B_MIN_WIDTH, Manager.B_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.B_MAX_WIDTH, Manager.B_MAX_HEIGHT));

  }

  public void addBBlockButtons(BBlocButton... buttons) {
    for (BBlocButton b : buttons) {
      add(b.getComponent());
    }
  }
}
