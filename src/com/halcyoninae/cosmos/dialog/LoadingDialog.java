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

package com.halcyoninae.cosmos.dialog;

import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a buffering dialog
 *
 * @author Jack Meng
 * @since 3.1
 */
public class LoadingDialog extends JFrame {
    private final JProgressBar bar;

    public LoadingDialog(String text, boolean isImportant) {
        setPreferredSize(new Dimension(400, 200));
        setResizable(false);
        setAlwaysOnTop(isImportant);
        setLocationRelativeTo(null);
        setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(400, 200));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        bar = new JProgressBar();
        bar.setPreferredSize(new Dimension(400, 20));
        bar.setStringPainted(true);
        bar.setString("Loading...");
        bar.setAlignmentX(Component.CENTER_ALIGNMENT);
        bar.setIndeterminate(true);
        panel.add(bar);
        getContentPane().add(panel);
    }


    /**
     * @param task
     */
    public void run(Runnable task) {
        this.pack();
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        new Thread(task::run);
    }

    public void run() {
        run(() -> {
        });
    }

    public void kill() {
        this.dispose();
    }

}
