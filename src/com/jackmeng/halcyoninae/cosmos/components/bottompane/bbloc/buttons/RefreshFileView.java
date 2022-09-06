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
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Dispatches a call for all of the tabs in the FileList
 * to be refreshed. This button is shown to the user
 * if an automatic refresh call could not make it in time for a
 * refresh.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class RefreshFileView extends JButton implements BBlocButton {

    public RefreshFileView() {
        super(new ImageIcon(DeImage
            .resize(DeImage.imageIconToBI(Global.rd.getFromAsImageIcon(Manager.BBLOC_REFRESH_FILEVIEW_ICON)), 16,
                16)));
        setRolloverIcon(new ImageIcon(DeImage
            .resize(DeImage
                    .imageIconToBI(Global.rd.getFromAsImageIcon(Manager.BBLOC_REFRESH_FILEVIEW_ICON_PRESSED)), 16,
                16)));
        setToolTipText(Manager.REFRESH_BUTTON_TOOLTIP);
        setOpaque(true);
        setBackground(null);
        setBorder(null);
        setDoubleBuffered(true);
        setContentAreaFilled(false);
        addActionListener(this);
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
        Global.bp.mastRevalidate();
    }
}
