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

package com.jackmeng.halcyoninae.cosmos.dialog;

import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.tailwind.TailwindEvent.TailwindStatus;
import com.jackmeng.halcyoninae.tailwind.TailwindListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SlidersDialog extends JFrame implements Runnable, TailwindListener.StatusUpdateListener {
    private int bal = 50, pan = 50;

    public SlidersDialog() {
        JTabbedPane pane = new JTabbedPane();

        JPanel basicSlidersPane = new JPanel();
        basicSlidersPane.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 15));

        JSlider balanceSlider = new JSlider(SwingConstants.VERTICAL, 0, 100, bal);
        balanceSlider.setPaintTicks(true);
        balanceSlider.setSnapToTicks(true);
        balanceSlider.setToolTipText("Balance Slider: " + balanceSlider.getValue());
        balanceSlider.addChangeListener(e -> {
            if (Global.player.getStream().getControls() != null) {
                Global.player.getStream().setBalance(balanceSlider.getValue());
            }
            balanceSlider.setToolTipText("Balance Slider: " + balanceSlider.getValue());
            bal = balanceSlider.getValue();
        });

        JSlider panSlider = new JSlider(SwingConstants.VERTICAL, 0, 100, pan);
        panSlider.setMajorTickSpacing(10);
        panSlider.setMinorTickSpacing(100);
        panSlider.setSnapToTicks(true);
        panSlider.setPaintTicks(true);
        panSlider.setToolTipText("Pan Slider: " + panSlider.getValue());
        panSlider.addChangeListener(e -> {
            if (Global.player.getStream().getControls() != null) {
                Global.player.getStream().setPan(panSlider.getValue());
            }
            panSlider.setToolTipText("Pan Slider: " + panSlider.getValue() + " | "
                    + (balanceSlider.getValue() > 50 ? "RIGHT" : (balanceSlider.getValue() < 50 ? "LEFT" : "CENTER")));
            pan = panSlider.getValue();
        });

        basicSlidersPane.add(balanceSlider);
        basicSlidersPane.add(panSlider);

        pane.addTab("Basic Sliders", basicSlidersPane);

        setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        setTitle("Sliders");
        setPreferredSize(new Dimension(300, 350));
        setAutoRequestFocus(false);
        getContentPane().add(pane);
    }

    @Override
    public void run() {
        pack();
        setVisible(true);
    }


    /**
     * @param status
     */
    @Override
    public void statusUpdate(TailwindStatus status) {
        if (status.equals(TailwindStatus.OPEN)) {
            Global.player.getStream().setBalance(bal);
            Global.player.getStream().setPan(pan);
        }
    }
}
