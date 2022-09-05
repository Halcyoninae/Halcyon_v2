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

package com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist.FileList;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;

/**
 * BBloc short for Button Bloc
 * is a view to display a button palette.
 * <p>
 * These buttons can be used to address certain
 * things regarding the player.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class BBlocView extends JPanel {

    /// BBloc Config START
    /// B Bloc Size Config START ///
    final int B_MIN_WIDTH  = Manager.MIN_WIDTH - FileList.FILEVIEW_MIN_WIDTH - 40;
    final int B_MIN_HEIGHT = Manager.MIN_HEIGHT / 2;

    final int B_MAX_WIDTH  = B_MIN_WIDTH - FileList.FILEVIEW_MAX_WIDTH;
    final int B_MAX_HEIGHT = Manager.MAX_HEIGHT / 2;
    /// B Bloc Size Config END ///
    /// BBloc Config END

    public BBlocView() {
        super();
        setPreferredSize(new Dimension(B_MIN_WIDTH, B_MIN_HEIGHT));
        setMinimumSize(new Dimension(B_MIN_WIDTH, B_MIN_HEIGHT));
    }

    /**
     * Attempts to add an N amount of BBlocButton implementations
     * to the current BBloc container.
     *
     * @param buttons A Vararg of BBloc Buttons
     * @author Jack Meng
     * @see com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.BBlocButton
     * @since 3.0
     */
    public void addBBlockButtons(BBlocButton... buttons) {
        if (buttons.length > 0) {
            for (BBlocButton b : buttons) {
                add(b.getComponent());
            }
        }
    }
}
