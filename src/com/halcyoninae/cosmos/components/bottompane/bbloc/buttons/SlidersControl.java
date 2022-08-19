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

package com.halcyoninae.cosmos.components.bottompane.bbloc.buttons;

import com.halcyoninae.cosmos.components.bottompane.bbloc.BBlocButton;
import com.halcyoninae.cosmos.dialog.SlidersDialog;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.Manager;
import com.halcyoninae.halcyon.utils.DeImage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Jack Meng
 * @since 3.2
 */
public class SlidersControl extends JButton implements BBlocButton {
    private final SlidersDialog dialog;
    private boolean isPressed;

    public SlidersControl() {
        setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.SLIDERS_BUTTON_DEFAULT_ICON), 16, 16));
        setToolTipText("Open a window to display sliders that can alter certain things");
        setOpaque(true);
        setBorder(null);
        setDoubleBuffered(true);
        addActionListener(this);
        setBackground(null);
        setContentAreaFilled(false);
        isPressed = false;
        dialog = new SlidersDialog();
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isPressed = false;
            }
        });
    }


    /**
     * @return JComponent
     */
    @Override
    public JComponent getComponent() {
        return this;
    }


    /**
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPressed) {
            dialog.run();
        }
    }

}
