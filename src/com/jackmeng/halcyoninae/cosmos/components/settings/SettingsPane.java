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

package com.jackmeng.halcyoninae.cosmos.components.settings;

import com.jackmeng.halcyoninae.cosmos.components.settings.tabs.properties.AudioSettings;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.ExternalResource;

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
        setIconImage(Global.ico.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());

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
