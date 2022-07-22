package com.halcyoninae.cosmos.components.settings;

import com.halcyoninae.halcyon.connections.properties.Property;

import javax.swing.*;
import java.awt.*;

public final class SettingsUtil {

  /**
   * @param property
   * @param dim
   * @return JPanel
   */
  public static JPanel getPropertyAsComponent(Property property, Dimension dim) {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.LEFT));

    JLabel label = new JLabel(property.propertyName);

    if(property.pr != null) {
      JComboBox<String> comboBox = new JComboBox<>();
      for(String allowedProperty : property.allowedProperties) {
        comboBox.addItem(allowedProperty);
      }
      comboBox.setSelectedItem(property.defaultProperty);
      panel.add(label);
      panel.add(comboBox);
    } else {
      JTextField textField = new JTextField(property.defaultProperty);
      panel.add(label);
      panel.add(textField);
    }

    return panel;
  }
}
