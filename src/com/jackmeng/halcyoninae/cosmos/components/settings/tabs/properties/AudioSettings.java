/*
 * Halcyon ~ exoad
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https://raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
 */

package com.jackmeng.halcyoninae.cosmos.components.settings.tabs.properties;

import com.jackmeng.halcyoninae.cosmos.components.settings.SettingsTabs;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ProgramResourceManager;
import com.jackmeng.halcyoninae.halcyon.connections.properties.Property;
import com.jackmeng.halcyoninae.halcyon.connections.properties.Property.PropertyFilterType;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jack Meng
 * @since 3.2
 */
public class AudioSettings extends JScrollPane implements SettingsTabs {
    private int i = 0;

    public AudioSettings() {
        setPreferredSize(new Dimension(Manager.SETTINGS_MIN_WIDTH, Manager.SETTINGS_MIN_HEIGHT));
        setMinimumSize(getPreferredSize());

        JPanel panel = new JPanel();
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
