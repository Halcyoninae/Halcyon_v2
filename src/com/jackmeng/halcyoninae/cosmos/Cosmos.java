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

package com.jackmeng.halcyoninae.cosmos;

import com.jackmeng.halcyoninae.cosmos.components.ErrorWindow;
import com.jackmeng.halcyoninae.cosmos.components.InstantClose;
import com.jackmeng.halcyoninae.cosmos.util.Theme;
import com.jackmeng.halcyoninae.halcyon.Halcyon;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.ExternalResource;
import com.jackmeng.halcyoninae.halcyon.utils.GUITools;
import com.jackmeng.halcyoninae.halcyon.utils.ProgramResourceManager;
import com.jackmeng.halcyoninae.tailwind.TailwindEvent.TailwindStatus;
import com.jackmeng.halcyoninae.tailwind.TailwindListener;

import javax.swing.*;
import java.awt.*;

/**
 * BigContainer is the main window for the program,
 * it is the parent container for BottomPane and TopPane.
 * <p>
 * It performs no other tasks but to align the components together
 * in a top-to-bottom fashion.
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cosmos.components.bottompane.BottomPane
 * @see com.jackmeng.halcyoninae.cosmos.components.toppane.TopPane
 * @since 3.0
 */
public class Cosmos implements Runnable, TailwindListener.StatusUpdateListener {
    private JFrame container;

    /**
     * A JSplitPane contains a BottomPane and TopPane that
     * will be used as the content pane for the JFrame.
     *
     * @param mainPane The JSplitPane instance to attach with
     */
    public Cosmos(JSplitPane mainPane) {
        mainPane.setBorder(BorderFactory.createEmptyBorder());
        container = new JFrame("Halcyon Beta ~ exoad");
        if (ExternalResource.pm.get(ProgramResourceManager.KEY_PROGRAM_FORCE_OPTIMIZATION).equals("false")) {
            container.setUndecorated(true);
            container = new JFrame("Halcyon Beta ~ exoad") {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRoundRect(0, 0, container.getWidth(), container.getHeight(), 20, 20);
                }
            };
        }

        container.setIconImage(Halcyon.ico.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        container.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        container.setPreferredSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT));
        //container.setMinimumSize(container.getPreferredSize());

        container.setContentPane(mainPane);
        container.addWindowListener(new InstantClose());
    }

    /**
     * Dispatches an event to update all LAF components
     * of the current Swing
     * This implementation is adapted from the original MP4J project
     *
     * @param theme The theme to update to
     * @throws UnsupportedLookAndFeelException If the LAF is not supported
     * @author Jack Meng
     * @see com.jackmeng.halcyoninae.cosmos.util.Theme
     * @since 3.3
     */
    public static void refreshUI(Theme theme) throws UnsupportedLookAndFeelException {
        try {
            UIManager.setLookAndFeel(theme.getLAF().getName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
            new ErrorWindow(ex.getMessage()).run();
            ExternalResource.dispatchLog(ex);
        }
        for (Window frame : java.awt.Frame.getFrames()) {
            try {
                SwingUtilities.updateComponentTreeUI(frame);
            } catch (NullPointerException ex) {
                // Do nothing bc sometimes swing is bad...
            }

            for (Component e : GUITools.getAllComponents(frame)) {
                if (e instanceof JLabel) {
                    e.setForeground(theme.getForegroundColor());
                }
                SwingUtilities.updateComponentTreeUI(e);
            }
            frame.pack();
            frame.repaint(30);
        }
        ColorManager.programTheme = theme;
        ColorManager.refreshColors();
    }

    /**
     * Returns the JFrame instance
     *
     * @return The JFrame instance
     */
    public JFrame getFrame() {
        return container;
    }

    @Override
    public void run() {
        container.pack();
        container.setLocationRelativeTo(null);
        container.setVisible(true);
    }

    /**
     * @param status
     */
    @Override
    public void statusUpdate(TailwindStatus status) {
        if (status.equals(TailwindStatus.PLAYING)) {
            container.setIconImage(Halcyon.ico.getFromAsImageIcon(Manager.PROGRAM_GREEN_LOGO).getImage());
        } else if (status.equals(TailwindStatus.OPEN)) {
            container.setIconImage(Halcyon.ico.getFromAsImageIcon(Manager.PROGRAM_BLUE_LOGO).getImage());
        } else if (status.equals(TailwindStatus.PAUSED)) {
            container.setIconImage(Halcyon.ico.getFromAsImageIcon(Manager.PROGRAM_RED_LOGO).getImage());
        } else {
            container.setIconImage(Halcyon.ico.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        }
    }

}