package com.jackmeng.cosmos.components.settings;

import javax.swing.JComponent;

public interface SettingsTabs {
  String getTabName();

  String getTabToolTip();

  JComponent getTabContent();
}
