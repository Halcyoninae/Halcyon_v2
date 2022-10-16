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

package com.jackmeng.halcyoninae.cosmos.components.minimizeplayer;

import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * This class represents a frame that holds a minimized player.
 * <p>
 * What is a minimized player?
 * A minimized player is an indirect way to display what is being
 * played, in a very compact form factor. This form factor, however,
 * is not used to directly control the audio instead is maybe to display
 * what is being played without having the main player interface
 * being shown.
 * <p>
 * This class specifically represents the window
 * part of the miniplayer.
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cosmos.components.minimizeplayer.MiniContentPane
 * @since 3.2
 */
public class MiniPlayer extends JFrame implements Runnable {
    private int pX, pY;
    private transient MiniPlayerListener listener;

    public MiniPlayer() {
        setPreferredSize(
            new Dimension(MiniPlayerManager.MINI_PLAYER_MIN_WIDTH, MiniPlayerManager.MINI_PLAYER_MIN_HEIGHT));
        setUndecorated(true);
        setIconImage(Global.ico.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        setAutoRequestFocus(true);
        setFocusable(true);
        MiniContentPane pane = new MiniContentPane();
        Global.ifp.addInfoViewUpdateListener(pane);
        setContentPane(pane);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
            }
        });
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
        addMouseListener(new MiniPlayerClickMenu(this));
        setResizable(true);
    }

    /**
     * Only one listener is allowed per miniplayer.
     *
     * @param listener A Listener implementation or anonymous class.
     */
    public void setMiniPlayerListener(MiniPlayerListener listener) {
        this.listener = listener;
    }

    /**
     * Calls for the listener that the window is closing.
     */
    public void pounceListener() {
        listener.closingWindow();
    }

    @Override
    public void run() {
        pack();
        setVisible(true);
    }
}
