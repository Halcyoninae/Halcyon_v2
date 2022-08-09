/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
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

package com.halcyoninae.cosmos.inheritable;

import com.halcyoninae.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        add(new CloseTabButton());
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
            setBorder(null);
            setBorderPainted(false);
            setRolloverEnabled(false);
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
}
