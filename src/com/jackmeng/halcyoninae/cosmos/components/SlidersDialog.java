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

import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;
import com.jackmeng.halcyoninae.tailwind.TailwindEvent.TailwindStatus;
import com.jackmeng.halcyoninae.tailwind.TailwindListener;

import javax.swing.*;
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

        setIconImage(Global.ico.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
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
