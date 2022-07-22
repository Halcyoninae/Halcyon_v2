package com.halcyoninae.cosmos.components.settings;

import com.halcyoninae.halcyon.connections.properties.Property;

import javax.swing.*;
import java.awt.*;

public final class SettingsUtil {

  /**
   * @param property A property attached to a component
   * @param dim The dimension of the component
   * @return JPanel The component with the property attached
   */
  public static JPanel getPropertyAsComponent(Property property, Dimension dim) {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.LEFT));

    JLabel label = new JLabel(property.propertyName);

    if (property.pr != null) {
      JComboBox<String> comboBox = new JComboBox<>();
      if (property.commonProperties != null) {
        for (String allowedProperty : property.commonProperties) {
          comboBox.addItem(allowedProperty);
        }
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
