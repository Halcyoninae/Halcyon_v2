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

package com.jackmeng.halcyoninae.cosmos.components.moreapps;

import com.jackmeng.halcyoninae.halcyon.Halcyon;
import com.jackmeng.halcyoninae.halcyon.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * @author Jack Meng
 * @since 3.3
 */
public class MoreApps extends JFrame implements Runnable {
    private static final int maxCols = 5;
    private final JScrollPane jsp;
    private final GridLayout gl;
    private int colIndex = 0;
    private int pX, pY;

    public MoreApps() {
        setPreferredSize(new Dimension(MoreAppsManager.MIN_WIDTH, MoreAppsManager.MIN_HEIGHT));
        setUndecorated(true);
        setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        setFocusable(true);
        setAutoRequestFocus(true);

        jsp = new JScrollPane(null, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setPreferredSize(new Dimension(MoreAppsManager.MIN_WIDTH, MoreAppsManager.MIN_HEIGHT));

        gl = new GridLayout(1, maxCols);

        jsp.getViewport().setLayout(gl);
        getContentPane().add(jsp);
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

        jsp.addMouseListener(new MouseAdapter() {
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

        jsp.addMouseListener(new MouseAdapter() {
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
        jsp.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                setLocation(getLocation().x + me.getX() - pX,
                    getLocation().y + me.getY() - pY);
            }
        });
        setResizable(true);
    }

    /**
     * This method will dynamically allocate the necessary space for
     * the individual component cells.
     * <p>
     * All elements will be resized to fit the correct cell size!!!
     *
     * @param c Components to add to the screen.
     */
    public void addComponent(JComponent... c) {
        for (JComponent cc : c) {
            cc.setPreferredSize(
                new Dimension(MoreAppsManager.ELEMENT_MAX_WIDTH_HEIGHT, MoreAppsManager.ELEMENT_MAX_WIDTH_HEIGHT));
            cc.setBorder(BorderFactory.createLineBorder(ColorManager.BORDER_THEME));
            if (colIndex % maxCols == 0) {
                Debugger.info("Adding a component: " + cc.getClass().getCanonicalName() + " to row a new row");
                gl.setRows(gl.getRows() + 1);
                jsp.getViewport().revalidate();
                jsp.getViewport().repaint();
                jsp.getViewport().add(cc);
            } else {
                Debugger.info("Adding a component: " + cc.getClass().getCanonicalName() + " to row the last row");
                jsp.getViewport().add(cc);
            }
            colIndex++;
            Debugger.warn("MoreApps (ColIndex): " + colIndex);
        }
    }

    @Override
    public void run() {
        pack();
        try {
            setOpacity(0.6f);
        } catch (UnsupportedOperationException e) {
            Debugger.warn("Failing setting Opacity for MoreApps dialog");
        }
        setLocationRelativeTo(Halcyon.bgt.getFrame());
        setVisible(true);
    }
}
