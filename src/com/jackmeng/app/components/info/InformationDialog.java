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

package com.jackmeng.app.components.info;

import com.jackmeng.app.components.info.layout.FileFromSourceTab;
import com.jackmeng.constant.Global;
import com.jackmeng.constant.Manager;

import javax.swing.*;
import java.awt.*;

public class InformationDialog extends JFrame implements Runnable {
    private JTabbedPane panes;

    private transient InformationTab[] tabs = {
            new FileFromSourceTab("Program Properties",
                    FileFromSourceTab.getContent(Manager.LEGAL_NOTICE_PROPERTIES_DOCS)),
            new FileFromSourceTab("Legals", FileFromSourceTab.getContent(Manager.LEGAL_NOTICE_DOCS)),
    };

    public InformationDialog() {
        setTitle("Halcyon ~ Information");
        setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        setPreferredSize(new Dimension(Manager.LEGALNOTICEDIALOG_MIN_WIDTH, Manager.LEGALNOTICEDIALOG_MIN_HEIGHT));
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(false);

        setLocationRelativeTo(null);

        panes = new JTabbedPane();
        panes.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);

        for (InformationTab tab : tabs) {
            panes.addTab(tab.getName(), tab.getComponent());
        }

        getContentPane().add(panes);
    }

    @Override
    public void run() {
        pack();
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);
    }
}
