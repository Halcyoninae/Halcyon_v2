package com.jackmeng.app.components.bbloc.buttons;

import com.jackmeng.app.components.bbloc.BBlocButton;
import com.jackmeng.constant.Global;
import com.jackmeng.constant.Manager;

import javax.swing.*;

import java.awt.event.ActionEvent;

/**
 * A representation of a deprecated SettingsView class
 * which used to be in the bottom tabs view pane.
 * 
 * @author Jack Meng
 * @since 3.0
 */
public class Settings extends JButton implements BBlocButton {
  public Settings() {
    setRolloverIcon(Global.rd.getFromAsImageIcon(Manager.SETTINGS_BUTTON_PRESSED_ICON));
    setIcon(Global.rd.getFromAsImageIcon(Manager.SETTINGS_BUTTON_DEFAULT_ICON));
    setToolTipText(Manager.SETTINGS_BUTTON_TOOLTIP);
    setOpaque(true);
    setBorder(null);
    setBackground(null);
    addActionListener(this);
  }

  @Override
  public JComponent getComponent() {
    return this;
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
