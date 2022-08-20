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
import com.jackmeng.halcyoninae.cosmos.components.info.InformationDialog;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * A button that when pressed launches
 * {@link com.jackmeng.halcyoninae.cosmos.components.info.InformationDialog} which displays
 * information regarding legal documentation for all libraries and this program.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class LegalNoticeButton extends JButton implements BBlocButton {

    /// LEGALNOTICEDIALOG Config START
    public static final int LEGALNOTICEDIALOG_MIN_WIDTH = 500;
    public static final int LEGALNOTICEDIALOG_MIN_HEIGHT = 550;
    public static final String LEGAL_NOTICE_DOCS = Manager.RSC_FOLDER_NAME + "/files/LICENSE.html";
    public static final String LEGAL_NOTICE_PROPERTIES_DOCS = Manager.RSC_FOLDER_NAME + "/files/PROPERTIES_FILE.html";
    final String LEGALNOTICEBBLOC_ICON_BUTTON_NORMAL = Manager.RSC_FOLDER_NAME + "/bbloc/legals_normal.png";
    final String LEGALNOTICEBBLOC_ICON_BUTTON_PRESSED = Manager.RSC_FOLDER_NAME + "/bbloc/legals_pressed.png";
    final int LEGALNOTICEDIALOG_SCROLL_PANE_MIN_HEIGHT = LEGALNOTICEDIALOG_MIN_HEIGHT - 100;
    /// LEGALNOTICEDIALOG Config END

    public LegalNoticeButton() {
        setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(LEGALNOTICEBBLOC_ICON_BUTTON_NORMAL), 16, 16));
        setRolloverIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(LEGALNOTICEBBLOC_ICON_BUTTON_PRESSED), 16, 16));
        addActionListener(this);
        setOpaque(true);
        setBackground(null);
        setDoubleBuffered(true);
        setBorder(null);
        setContentAreaFilled(false);
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
        new InformationDialog().run();
    }

}
