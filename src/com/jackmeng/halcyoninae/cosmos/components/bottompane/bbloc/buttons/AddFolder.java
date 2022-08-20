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
import com.jackmeng.halcyoninae.cosmos.dialog.ConfirmWindow;
import com.jackmeng.halcyoninae.cosmos.dialog.SelectApplicableFolders;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.filesystem.FileParser;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;

import javax.swing.*;
import java.io.File;

/**
 * A BBloc button that handles when a user selects
 * a folder from the
 * {@link com.jackmeng.halcyoninae.cosmos.dialog.SelectApplicableFolders}
 * instance.
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cosmos.dialog.SelectApplicableFolders
 * @since 3.0
 */
public class AddFolder extends JButton implements BBlocButton {
    public AddFolder() {
        super(DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.ADDFOLDER_BUTTON_DEFAULT_ICON), 16, 16));
        setRolloverIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.ADDFOLDER_BUTTON_PRESSED_ICON), 16, 16));
        setToolTipText(Manager.ADDFOLDER_BUTTON_TOOLTIP);
        setOpaque(true);
        setBackground(null);
        setBorder(null);
        setDoubleBuffered(true);
        setContentAreaFilled(false);
        addActionListener(e -> {
            SelectApplicableFolders s = new SelectApplicableFolders();
            s.setFolderSelectedListener(folder -> {
                if (FileParser.isEmptyFolder(new File(folder))
                        || !FileParser.contains(new File(folder), Manager.ALLOWED_FORMATS)) {
                    new ConfirmWindow(
                            "This folder seems to be empty or does not seem to contain any Audio Files. Would you like to add this folder?",
                            status -> {
                                if (status) {
                                    Global.bp.pokeNewFileListTab(folder);
                                }
                            }).run();
                } else {
                    Global.bp.pokeNewFileListTab(folder);
                }
            });
            s.run();
        });
    }

    /**
     * @return JComponent
     */
    @Override
    public JComponent getComponent() {
        return this;
    }
}
