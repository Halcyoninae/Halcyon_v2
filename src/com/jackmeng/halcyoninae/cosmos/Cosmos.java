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

package com.jackmeng.halcyoninae.cosmos;

import javax.swing.*;

import com.jackmeng.halcyoninae.cosmos.dialog.ErrorWindow;
import com.jackmeng.halcyoninae.cosmos.events.InstantClose;
import com.jackmeng.halcyoninae.cosmos.theme.Theme;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ExternalResource;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ProgramResourceManager;
import com.jackmeng.halcyoninae.halcyon.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.GUITools;
import com.jackmeng.halcyoninae.tailwind.TailwindListener;
import com.jackmeng.halcyoninae.tailwind.TailwindEvent.TailwindStatus;

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

        container.setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        container.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        container.setPreferredSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT));
        container.setMinimumSize(container.getPreferredSize());
        container.getContentPane().add(mainPane);
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
     * @see com.jackmeng.halcyoninae.cosmos.theme.Theme
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
            container.setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_GREEN_LOGO).getImage());
        } else if (status.equals(TailwindStatus.OPEN)) {
            container.setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_BLUE_LOGO).getImage());
        } else if (status.equals(TailwindStatus.PAUSED)) {
            container.setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_RED_LOGO).getImage());
        } else {
            container.setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        }
    }

}