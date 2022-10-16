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

package com.jackmeng.halcyoninae.cosmos.components.waveform;

import com.jackmeng.halcyoninae.halcyon.Halcyon;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Jack Meng
 * @since 3.2
 */
public class WaveFormClickMenu extends MouseAdapter {
    private final WaveFormPane e;

    public WaveFormClickMenu(WaveFormPane e) {
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
