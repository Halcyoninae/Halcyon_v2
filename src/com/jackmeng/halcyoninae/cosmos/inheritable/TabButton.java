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

package com.jackmeng.halcyoninae.cosmos.inheritable;

import com.jackmeng.halcyoninae.cosmos.dialog.ErrorWindow;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ExternalResource;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class TabButton extends JPanel {

    private final JTabbedPane parentPane;
    private transient RemoveTabListener listener;

    public TabButton(JTabbedPane parent) {
        this.parentPane = parent;
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setOpaque(false);

        JLabel label = new JLabel() {
            @Override
            public String getText() {
                int i = parentPane.indexOfTabComponent(TabButton.this);
                if (i != -1) {
                    return parentPane.getTitleAt(i);
                }
                return null;
            }
        };

        add(label);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3));
        add(new CloseTabButton());
        add(Box.createHorizontalStrut(4));
        add(new MoreActionsButton());
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    /**
     * @return CloseTabButton
     */
    public CloseTabButton getInternalButton() {
        return (CloseTabButton) getComponent(1);
    }

    /**
     * @param listener
     */
    public void setListener(RemoveTabListener listener) {
        this.listener = listener;
    }

    public void dispatchRemoveEvent() {
        if (listener != null) {
            listener.onRemoveTab();
        }
    }


    public interface RemoveTabListener {
        void onRemoveTab();
    }

    public class CloseTabButton extends JButton implements ActionListener {

        public CloseTabButton() {
            setPreferredSize(new Dimension(Manager.BUTTON_STD_ICON_WIDTH_N_HEIGHT, Manager.BUTTON_STD_ICON_WIDTH_N_HEIGHT));
            setToolTipText("Close Tab");
            setUI(getUI());

            setContentAreaFilled(false);
            setFocusable(false);
            setBorder(null);
            setBorderPainted(false);
            setRolloverEnabled(true);
            addActionListener(this);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.WHITE);
            g2.drawLine(3, 3, getWidth() - 3, getHeight() - 3);
            g2.drawLine(getWidth() - 3, 3, 3, getHeight() - 3);
            g2.dispose();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = parentPane.indexOfTabComponent(TabButton.this);
            if (i != -1) {
                parentPane.remove(i);
                dispatchRemoveEvent();
            }
        }
    }

    public class MoreActionsButton extends JButton implements ActionListener {

        public MoreActionsButton() {
            setPreferredSize(new Dimension(Manager.BUTTON_STD_ICON_WIDTH_N_HEIGHT, Manager.BUTTON_STD_ICON_WIDTH_N_HEIGHT));
            setToolTipText("More Playlist Actions");
            setUI(getUI());
            setBorder(null);
            setContentAreaFilled(false);
            setRolloverEnabled(true);
            setBorderPainted(false);
            addActionListener(this);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.WHITE);
            int i = 3, last_loc = 0;
            while (i-- > 0) {
                g2.fillOval(last_loc, this.getHeight() / 2 - 1, 3, 3);
                last_loc += 5;
            }
            g2.dispose();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JPopupMenu popMenu = new JPopupMenu("Playlist Actions");
            JMenuItem openInExplorer = new JMenuItem("Open In File Explorer");
            openInExplorer.addActionListener(x -> {
                try {
                    Desktop.getDesktop().open(new File(parentPane.getToolTipTextAt(parentPane.indexOfTabComponent(TabButton.this))));
                } catch (IOException e1) {
                    ExternalResource.dispatchLog(e1);
                    new ErrorWindow("Failed to open this playlist in the native file explorer.\n" + e1.getMessage());
                    Debugger.crit(e1.getLocalizedMessage());
                }
            });

            popMenu.add(openInExplorer);
            popMenu.show(this, TabButton.this.getX(), TabButton.this.getY());
        }

    }
}
