/*
 *  Copyright: (C) 2022 MP5J Jack Meng
 * Halcyon MP5J is a music player designed openly.
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

package com.jackmeng.halcyoninae.cosmos.components.toppane.layout.buttoncontrol;

import com.jackmeng.halcyoninae.cosmos.components.toppane.TopPane;
import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.ButtonControlTP;
import com.jackmeng.halcyoninae.halcyon.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the time control buttons
 * below the progress slider.
 * <p>
 * It is used to manipulate the progress in a controlled
 * fashion.
 *
 * @author Jack Meng
 * @since 3.3
 */
public class TimeControlSubTP extends JPanel implements ActionListener {
    /// TimeControl Config START
    final String FAST_FORWARD_ICON = Manager.RSC_FOLDER_NAME + "/time_control/forward.png";
    final String FAST_BACKWARD_ICON = Manager.RSC_FOLDER_NAME + "/time_control/backward.png";
    final String TO_END_ICON = Manager.RSC_FOLDER_NAME + "/time_control/end.png";
    final String TO_START_ICON = Manager.RSC_FOLDER_NAME + "/time_control/start.png";
    public static long INTERVAL_TIMING_MILLISECONDS = 10000L;
    final int ICON_SIZING = 18;
    final int TIMECONTROL_HEIGHT = ButtonControlTP.BUTTONCONTROL_MIN_HEIGHT
            - ButtonControlTP.BUTTONCONTROL_BOTTOM_TOP_BUDGET;
    private final JButton fastForward;
    private final JButton fastBackward;
    private final JButton toEnd;
    private final JButton toStart;
    private final JLabel timeLabel;
    /// TimeControl Config END

    public TimeControlSubTP() {
        setPreferredSize(
                new Dimension(TopPane.TOPPANE_MIN_WIDTH, TIMECONTROL_HEIGHT / 5));
        setMinimumSize(getPreferredSize());
        setOpaque(false);
        setLayout(new GridLayout(1, 5));

        timeLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timeLabel.setForeground(ColorManager.MAIN_FG_THEME);
        timeLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        fastForward = new JButton();
        fastForward.setLayout(new BorderLayout());
        fastForward.setIcon(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(FAST_FORWARD_ICON), ICON_SIZING, ICON_SIZING));
        fastForward.setPreferredSize(new Dimension(TopPane.TOPPANE_MIN_WIDTH / 5,
                TIMECONTROL_HEIGHT / 5));
        fastForward.setMaximumSize(fastForward.getPreferredSize());
        fastForward.setBorder(null);
        fastForward.setBackground(null);
        fastForward.addActionListener(this);

        fastBackward = new JButton();
        fastBackward.setLayout(new BorderLayout());
        fastBackward.setIcon(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(FAST_BACKWARD_ICON), ICON_SIZING, ICON_SIZING));
        fastBackward.setPreferredSize(new Dimension(TopPane.TOPPANE_MIN_WIDTH / 5,
                TIMECONTROL_HEIGHT / 5));
        fastBackward.setMaximumSize(fastBackward.getPreferredSize());
        fastBackward.setBorder(null);
        fastBackward.setBackground(null);
        fastBackward.addActionListener(this);

        toEnd = new JButton();
        toEnd.setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(TO_END_ICON), ICON_SIZING, ICON_SIZING));
        toEnd.setPreferredSize(new Dimension(TopPane.TOPPANE_MIN_WIDTH / 5,
                TIMECONTROL_HEIGHT / 5));
        toEnd.setMaximumSize(toEnd.getPreferredSize());
        toEnd.setBorder(null);
        toEnd.setBackground(null);
        toEnd.addActionListener(this);

        toStart = new JButton();
        toStart.setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(TO_START_ICON), ICON_SIZING, ICON_SIZING));
        toStart.setPreferredSize(new Dimension(TopPane.TOPPANE_MIN_WIDTH / 5,
                TIMECONTROL_HEIGHT / 5));
        toStart.setMaximumSize(toStart.getPreferredSize());
        toStart.setBorder(null);
        toStart.setBackground(null);
        toStart.addActionListener(this);

        add(toStart);
        add(fastBackward);
        add(timeLabel);
        add(fastForward);
        add(toEnd);
    }

    public void setTimeText(String str) {
        timeLabel.setText(str);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource().equals(toEnd)) {
            Debugger.info("> Submitting time control for [toEnd]");
            Global.player.getStream().seekTo(-1);
        } else if (arg0.getSource().equals(toStart)) {
            Debugger.info("> Submitting time control for [toStart]");
            Global.player.getStream().seekTo(-2);
        } else if (arg0.getSource().equals(fastBackward)) {
            Debugger.info("> Submitting time control for [fastBackward]");
            Global.player.getStream().seekTo(-INTERVAL_TIMING_MILLISECONDS);
        } else if (arg0.getSource().equals(fastForward)) {
            Debugger.info("> Submitting time control for [fastForward]");
            Global.player.getStream().seekTo(INTERVAL_TIMING_MILLISECONDS);
        }
    }
}
