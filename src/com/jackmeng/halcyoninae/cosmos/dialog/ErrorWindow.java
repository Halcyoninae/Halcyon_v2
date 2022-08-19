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

package com.jackmeng.halcyoninae.cosmos.dialog;

import javax.swing.*;

import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;

import java.awt.*;

/**
 * This class represents an error dialog,
 * which is used to display error messages.
 * <p>
 * It is basically a text dialog.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class ErrorWindow extends JFrame implements Runnable {

    static final String DIALOG_ERROR_WIN_TITLE = "Exception!";
    /// Error Window Config START
    final int DIALOG_ERROR_MIN_WIDTH = 300;
    final int DIALOG_ERROR_MIN_HEIGHT = 150;
    /// Error Window Config END

    public ErrorWindow(String content) {
        super(DIALOG_ERROR_WIN_TITLE);
        setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        setPreferredSize(new Dimension(DIALOG_ERROR_MIN_WIDTH, DIALOG_ERROR_MIN_HEIGHT));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        JTextArea jt = new JTextArea(content);
        jt.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(jt);
        scrollPane.setPreferredSize(new Dimension(DIALOG_ERROR_MIN_WIDTH, DIALOG_ERROR_MIN_HEIGHT));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane);
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> {
            this.pack();
            this.setAlwaysOnTop(true);
            this.setVisible(true);
        });
    }
}
