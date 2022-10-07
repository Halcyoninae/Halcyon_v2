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

package com.jackmeng.halcyoninae.cosmos.components;

import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.Debugger;
import com.jackmeng.halcyoninae.halcyon.utils.ExternalResource;

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
