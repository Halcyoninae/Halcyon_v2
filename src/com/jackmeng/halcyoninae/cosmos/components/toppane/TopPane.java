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

package com.jackmeng.halcyoninae.cosmos.components.toppane;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist.FileList;
import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.ButtonControlTP;
import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.InfoViewTP;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;

/**
 * The Halcyon Music Player has two main components for it's main component: A
 * top and a
 * bottom. This class represents the top pane, which contains information
 * regarding the current
 * track and any sub-controls to modify playback.
 * <p>
 * This pane is very plain and only serving to align the components together
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cosmos.components.toppane.layout.ButtonControlTP
 * @see com.jackmeng.halcyoninae.cosmos.components.toppane.layout.InfoViewTP
 * @see com.jackmeng.halcyoninae.cosmos.components.bottompane.BottomPane
 * @since 3.0
 */
public class TopPane extends JPanel {

    /// TopPane Config START
    public static final int TOPPANE_MIN_WIDTH  = Manager.MIN_WIDTH;
    public static final int TOPPANE_MIN_HEIGHT = Manager.MIN_HEIGHT / 2;

    final int TOPPANE_MAX_WIDTH                = TOPPANE_MIN_WIDTH - FileList.FILEVIEW_MAX_WIDTH;
    final int TOPPANE_MAX_HEIGHT               = Manager.MAX_HEIGHT / 2;
    /// TopPane Config END

    /**
     * Two instances of an InfoView and a ButtonControl view
     * are used to construct the Top View object.
     *
     * @param ifp  The InfoView instance to attach with
     * @param bctp The ButtonControl instance to attach with
     */
    public TopPane(InfoViewTP ifp, ButtonControlTP bctp) {
        setPreferredSize(new Dimension(TOPPANE_MIN_WIDTH, TOPPANE_MIN_HEIGHT));
        setMinimumSize(new Dimension(TOPPANE_MIN_WIDTH, TOPPANE_MIN_HEIGHT));
        setLayout(new BorderLayout());
        add(ifp, BorderLayout.PAGE_START);
        add(bctp, BorderLayout.PAGE_END);
    }
}
