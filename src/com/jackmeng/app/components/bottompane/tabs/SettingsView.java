package com.jackmeng.app.components.bottompane.tabs;

import javax.swing.JComponent;

import com.jackmeng.app.Global;
import com.jackmeng.app.Manager;
import com.jackmeng.app.components.bottompane.BPTabs;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SettingsView extends JScrollPane implements BPTabs {

  public SettingsView() {
    setPreferredSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    setMinimumSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MAX_HEIGHT));

    
  }

  @Override
  public String getTabName() {
    return Manager.SETTINGS_DEFAULT_TAB_NAME;
  }

  @Override
  public boolean restrainTabName() {
    return true;
  }

  @Override
  public String getTabToolTip() {
    return Manager.SETTINGS_TAB_DEFAULT_TOOLTIP;
  }

  @Override
  public ImageIcon getTabIcon() {
    return Global.rd.getFromAsImageIcon(Manager.SETTINGS_TAB_ICON);
  }

  @Override
  public JComponent getTabContent() {
    return this;
  }
  
}
