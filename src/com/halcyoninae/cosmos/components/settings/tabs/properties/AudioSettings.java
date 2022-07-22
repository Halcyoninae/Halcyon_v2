package com.halcyoninae.cosmos.components.settings.tabs.properties;

import javax.swing.*;

import com.halcyoninae.cosmos.components.settings.SettingsTabs;
import com.halcyoninae.cosmos.components.settings.SettingsUtil;
import com.halcyoninae.halcyon.connections.properties.Property;
import com.halcyoninae.halcyon.connections.properties.Property.PropertyFilterType;
import com.halcyoninae.halcyon.constant.Manager;
import com.halcyoninae.halcyon.constant.ProgramResourceManager;
import com.halcyoninae.halcyon.debug.Debugger;

import java.awt.*;

public class AudioSettings extends JScrollPane implements SettingsTabs {
  private JPanel panel;
  private int i = 0;

  public AudioSettings() {
    setPreferredSize(new Dimension(Manager.SETTINGS_MIN_WIDTH, Manager.SETTINGS_MIN_HEIGHT));
    setMinimumSize(getPreferredSize());

    panel = new JPanel();
    /*
     * for(Property p : Property.filterProperties("audio",
     * PropertyFilterType.STARTS_WITH, ProgramResourceManager.propertiesList)) {
     * panel.add(SettingsUtil.getPropertyAsComponent(p, new
     * Dimension(Manager.SETTINGS_MIN_WIDTH, 30)));
     * }
     */
    String[] str = new String[ProgramResourceManager.propertiesList.length];
    int i = 0;
    for (Property p : Property.filterProperties("audio", PropertyFilterType.STARTS_WITH,
        ProgramResourceManager.propertiesList)) {
      if (p != null) {
        str[i] = p.propertyName;
        i++;
      }
    }
    Debugger.good(this.i);
    Debugger.unsafeLog(
        str[0]);
    this.i++;
    setViewportView(panel);
  }


  /**
   * @return String
   */
  @Override
  public String getTabName() {
    return "Audio";
  }


  /**
   * @return String
   */
  @Override
  public String getTabToolTip() {
    return "Audio Settings";
  }


  /**
   * @return JComponent
   */
  @Override
  public JComponent getTabContent() {
    return this;
  }

}
