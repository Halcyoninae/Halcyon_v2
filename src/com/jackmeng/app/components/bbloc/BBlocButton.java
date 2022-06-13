package com.jackmeng.app.components.bbloc;

import javax.swing.JComponent;
import java.awt.event.*;

public interface BBlocButton extends ActionListener {
  JComponent getComponent();

  @Override
  default void actionPerformed(ActionEvent e) {
    // DO NOTHING
  }
}
