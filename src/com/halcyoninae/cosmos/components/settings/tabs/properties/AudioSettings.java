/*
 *  Copyright: (C) 2022 MP4J Jack Meng
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

package com.halcyoninae.cosmos.components.settings.tabs.properties;

import com.halcyoninae.cosmos.components.settings.SettingsTabs;
import com.halcyoninae.halcyon.connections.properties.ProgramResourceManager;
import com.halcyoninae.halcyon.connections.properties.Property;
import com.halcyoninae.halcyon.connections.properties.Property.PropertyFilterType;
import com.halcyoninae.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jack Meng
 * @since 3.2
 */
public class AudioSettings extends JScrollPane implements SettingsTabs {
    private final JPanel panel;
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
