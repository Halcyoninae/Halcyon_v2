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

package com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.buttons;

import javax.swing.*;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.BBlocButton;
import com.jackmeng.halcyoninae.cosmos.components.minimizeplayer.MiniPlayer;
import com.jackmeng.halcyoninae.cosmos.components.minimizeplayer.MiniPlayerListener;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;

import java.awt.event.ActionEvent;

/**
 * @author Jack Meng
 * @since 3.2
 */
public class MinimizePlayer extends JButton implements BBlocButton {
    private final MiniPlayer player;
    private boolean pressed = false;

    public MinimizePlayer() {
        setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BBLOC_MINIMIZED_PLAYER), 16, 16));
        setToolTipText("Launch the audio player in a mini display form factor");
        setRolloverEnabled(false);
        setBorder(null);
        setOpaque(true);
        setDoubleBuffered(true);
        setBackground(null);
        addActionListener(this);
        player = new MiniPlayer();
        player.setMiniPlayerListener(new MiniPlayerListener() {
            @Override
            public void closingWindow() {
                pressed = false;
            }
        });
    }

    /**
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!pressed) {
            player.run();
            pressed = true;
        }
    }

    /**
     * @return JComponent
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

}
