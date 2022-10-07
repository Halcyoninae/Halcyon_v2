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

package com.jackmeng.halcyoninae.cosmos.components.moreapps;

import com.jackmeng.halcyoninae.halcyon.Halcyon;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.Debugger;

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
        setIconImage(Global.ico.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
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
