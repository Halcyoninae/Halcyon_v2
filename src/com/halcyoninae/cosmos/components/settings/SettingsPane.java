/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly.
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

import com.halcyoninae.cosmos.components.settings.tabs.properties.AudioSettings;
import com.halcyoninae.halcyon.connections.properties.ExternalResource;
import com.halcyoninae.halcyon.constant.ColorManager;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Literally reads the properties from ResourceFolder's
 * property manager and determine what to do.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class SettingsPane extends JFrame implements Runnable, ActionListener {
    protected static final SettingsTabs[] tabs = {
            new AudioSettings()
    };

    private final JTabbedPane pane;
    private final JPanel aPane;
    private final JPanel buttons;
    private final JButton close;
    private final JButton ok;
    private final JButton apply;

    public SettingsPane() {
        setPreferredSize(new Dimension(Manager.SETTINGS_MIN_WIDTH, Manager.SETTINGS_MIN_HEIGHT + 60));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setFocusable(false);
        setMinimumSize(getPreferredSize());

        setTitle("Halcyon Settings ~ exoad");
        setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());

        pane = new JTabbedPane();
        pane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        pane.setPreferredSize(new Dimension(Manager.SETTINGS_MIN_WIDTH, Manager.SETTINGS_MIN_HEIGHT));
        pane.setMinimumSize(pane.getPreferredSize());
        pane.setBorder(BorderFactory.createLineBorder(ColorManager.BORDER_THEME));

        for (SettingsTabs tab : tabs) {
            pane.addTab(tab.getTabName(), null, tab.getTabContent(), tab.getTabToolTip());
        }

        buttons = new JPanel();
        buttons.setPreferredSize(new Dimension(Manager.SETTINGS_MIN_WIDTH, 30));
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));

        close = new JButton("Close");
        close.addActionListener(this);

        ok = new JButton("OK");
        ok.addActionListener(this);

        apply = new JButton("Apply");
        apply.addActionListener(this);

        buttons.add(ok);
        buttons.add(close);
        buttons.add(apply);

        aPane = new JPanel();
        aPane.setPreferredSize(getPreferredSize());
        aPane.setMinimumSize(getMinimumSize());
        aPane.setLayout(new BorderLayout());

        aPane.add(pane, BorderLayout.NORTH);
        aPane.add(buttons, BorderLayout.SOUTH);

        getContentPane().add(aPane);
    }

    private void ak() {
        pack();
        setVisible(true);
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(this::ak);
    }


    /**
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(close)) {
            dispose();
        } else if (e.getSource().equals(ok)) {
            ExternalResource.pm.save();
            dispose();
        } else if (e.getSource().equals(apply)) {
            ExternalResource.pm.save();
        }
    }
}
