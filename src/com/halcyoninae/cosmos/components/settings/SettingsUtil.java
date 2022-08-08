/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

package com.halcyoninae.cosmos.components.settings;

import com.halcyoninae.halcyon.connections.properties.Property;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jack Meng
 * @since 3.2
 */
public final class SettingsUtil {

    /**
     * @param property A property attached to a component
     * @param dim      The dimension of the component
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
