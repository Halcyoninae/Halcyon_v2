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

package com.jackmeng.halcyoninae.cosmos.components.waveform;

import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Jack Meng
 * @since 3.2
 */
public class WaveFormPane extends JFrame implements Runnable {
    private int pX, pY;

    public WaveFormPane() {
        setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                pX = me.getX();
                pY = me.getY();

            }

            @Override
            public void mouseDragged(MouseEvent me) {
                setLocation(getLocation().x + me.getX() - pX,
                        getLocation().y + me.getY() - pY);

            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                setLocation(getLocation().x + me.getX() - pX,
                        getLocation().y + me.getY() - pY);

            }
        });
        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                Global.waveForm.setVisibility(true);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Global.waveForm.setVisibility(false);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                Global.waveForm.setVisibility(false);
            }

            @Override
            public void windowIconified(WindowEvent e) {
                Global.waveForm.setVisibility(false);
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                Global.waveForm.setVisibility(true);
            }

            @Override
            public void windowActivated(WindowEvent e) {
                Global.waveForm.setVisibility(true);

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                Global.waveForm.setVisibility(false);
            }

        });
        addMouseListener(new WaveFormClickMenu(this));
        setPreferredSize(new Dimension(WaveFormManager.MIN_WIDTH, WaveFormManager.MIN_HEIGHT));
        getContentPane().add(Global.waveForm);
    }

    @Override
    public void run() {
        pack();
        setVisible(true);
    }
}
