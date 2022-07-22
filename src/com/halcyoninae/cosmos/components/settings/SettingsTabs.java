package com.halcyoninae.cosmos.components.settings;

import javax.swing.*;

public interface SettingsTabs {
  String getTabName();

  String getTabToolTip();

  JComponent getTabContent();
}
