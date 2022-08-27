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

import com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.BBlocButton;
import com.jackmeng.halcyoninae.cosmos.components.waveform.WaveFormPane;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This is a primarily experimental BBloc button and is used to
 * open a waveform panel that displays a visual representation of
 * the audio being played through the audio device
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cosmos.components.waveform.WaveForm
 * @since 3.2
 */
public class WaveFormLinker extends JButton implements BBlocButton {
    private WaveFormPane pane;

    public WaveFormLinker() {
        setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.RSC_FOLDER_NAME + "/bbloc/dots.png"), 16, 16));
        setRolloverIcon(DeImage.resizeImage(
            Global.rd.getFromAsImageIcon(Manager.RSC_FOLDER_NAME + "/bbloc/dots_pressed.png"), 16, 16));
        setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(16, 16));
        addActionListener(this);
        setToolTipText("WaveForm display Currently Unavailable due to technical issues");
        setEnabled(false);
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
        SwingUtilities.invokeLater(pane::run);
    }

}
