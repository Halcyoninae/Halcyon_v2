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

package com.jackmeng.halcyoninae.cosmos.components.minimizeplayer;

import com.jackmeng.halcyoninae.halcyon.Halcyon;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Jack Meng
 * @since 3.2
 */
public class MiniPlayerClickMenu extends MouseAdapter {
    private final MiniPlayer e;

    public MiniPlayerClickMenu(MiniPlayer e) {
        this.e = e;
    }

    /**
     * @param ex
     */
    private void attempt(MouseEvent ex) {
        int x = ex.getX();
        int y = ex.getY();
        JPopupMenu menu = new JPopupMenu();
        menu.setBorder(BorderFactory.createEmptyBorder());
        JMenuItem close = new JMenuItem("Close");
        close.addActionListener(event_1 -> e.setVisible(!e.isVisible()));
        JMenuItem stayTop = new JMenuItem("Stay On Top");
        stayTop.addActionListener(event_1 -> e.setAlwaysOnTop(!e.isAlwaysOnTop()));
        JMenuItem hideBC = new JMenuItem("Hide Big Player");
        hideBC.addActionListener(event_1 -> Halcyon.bgt.getFrame().setVisible(false));
        JMenuItem showBC = new JMenuItem("Show Big Player");
        showBC.addActionListener(event_1 -> Halcyon.bgt.getFrame().setVisible(true));
        menu.add(close);
        menu.add(stayTop);
        menu.add(showBC);
        menu.add(hideBC);
        e.pounceListener();
        menu.show(e, x + 15, y);
    }

    /**
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            attempt(e);
        }
    }

    /**
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            attempt(e);
        }
    }


    /**
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            attempt(e);
        }
    }

}
