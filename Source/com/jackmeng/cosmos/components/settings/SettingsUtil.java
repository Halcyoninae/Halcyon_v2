package com.jackmeng.cosmos.components.settings;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jackmeng.halcyon.connections.properties.Property;

import java.awt.*;

public final class SettingsUtil {
  public static JPanel getPropertyAsComponent(Property property, Dimension dim) {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.LEFT));

    JLabel label = new JLabel(property.propertyName);

    if(property.allowedProperties.length != 0) {
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
