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

package com.jackmeng.app.components.dialog;

import com.jackmeng.app.components.dialog.ConfirmWindow.ConfirmationListener;
import com.jackmeng.constant.Global;
import com.jackmeng.constant.Manager;

import javax.swing.*;
import java.awt.*;

public class LegalNoticeDialog extends JFrame implements Runnable {
    private String legalContext = "";
    private transient ConfirmationListener[] listeners;

    public LegalNoticeDialog(String legalContext, ConfirmationListener... listeners) {
        this.legalContext = legalContext;
        this.listeners = listeners;

        setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        setTitle("License Agreement");
        setPreferredSize(new Dimension(Manager.LEGALNOTICEDIALOG_MIN_WIDTH, Manager.LEGALNOTICEDIALOG_MIN_HEIGHT));
        setResizable(false);
        setFocusable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        mainPane.setPreferredSize(
                new Dimension(Manager.LEGALNOTICEDIALOG_MIN_WIDTH, Manager.LEGALNOTICEDIALOG_MIN_HEIGHT));

        getContentPane().add(mainPane);

        JTextArea legalNotice = new JTextArea(legalContext);
        legalNotice.setLineWrap(true);
        legalNotice.setWrapStyleWord(true);
        legalNotice.setEditable(false);
        legalNotice.setAlignmentY(Component.CENTER_ALIGNMENT);

        JScrollPane inheritedScrollPane = new JScrollPane(legalNotice);
        inheritedScrollPane.setPreferredSize(
                new Dimension(Manager.LEGALNOTICEDIALOG_MIN_WIDTH, Manager.LEGALNOTICEDIALOG_SCROLL_PANE_MIN_HEIGHT));

        JButton confirm = new JButton("Ok");
        confirm.addActionListener(e -> dispatchEvents(true));

        JButton cancel = new JButton("Close");
        cancel.addActionListener(e -> dispatchEvents(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(confirm);
        buttonPanel.add(cancel);

        mainPane.add(inheritedScrollPane, BorderLayout.CENTER);
        mainPane.add(buttonPanel, BorderLayout.SOUTH);
    }

    public synchronized ConfirmationListener[] getConfirmListeners() {
        return listeners;
    }

    public String getLegalText() {
        return legalContext;
    }

    private void dispatchEvents(boolean status) {
        if (listeners != null) {
            new Thread(() -> {
                for (ConfirmationListener listener : listeners) {
                    listener.onStatus(status);
                }
            }).start();
        }
        dispose();
    }

    @Override
    public void run() {
        pack();
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);
    }
}
