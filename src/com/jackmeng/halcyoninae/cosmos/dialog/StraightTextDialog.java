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

import com.jackmeng.halcyoninae.halcyon.Halcyon;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;

import java.awt.*;

public class StraightTextDialog extends JFrame implements Runnable {
    private final JScrollPane pane;
    private final JEditorPane text;

    public StraightTextDialog(String content) {
        setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        setTitle("Information");
        setPreferredSize(new Dimension(250, 100));
        setAutoRequestFocus(true);

        text = new JEditorPane("text/html", content);
        text.setEditable(false);
        text.setFocusable(false);

        pane = new JScrollPane(text);
        pane.setPreferredSize(new Dimension(250, 100));
        pane.getViewport().setPreferredSize(new Dimension(250, 100));

        setLocationRelativeTo(Halcyon.bgt.getFrame());

        setContentPane(pane);
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> {
            pack();
            setVisible(true);
        });
    }
}
