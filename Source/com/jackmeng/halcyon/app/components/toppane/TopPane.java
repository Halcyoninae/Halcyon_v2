/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
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

package com.jackmeng.halcyon.app.components.toppane;

import com.jackmeng.halcyon.app.components.toppane.layout.ButtonControlTP;
import com.jackmeng.halcyon.app.components.toppane.layout.InfoViewTP;
import com.jackmeng.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;


/**
 * The Halcyon Music Player has two main components for it's main component: A top and a
 * bottom. This class represents the top pane, which contains information regarding the current
 * track and any sub-controls to modify playback.
 *
 * This pane is very plain and only serving to align the components together
 *
 * @see com.jackmeng.halcyon.app.components.toppane.layout.ButtonControlTP
 * @see com.jackmeng.halcyon.app.components.toppane.layout.InfoViewTP
 * @see com.jackmeng.halcyon.app.components.bottompane.BottomPane
 *
 * @author Jack Meng
 * @since 3.0
 */
public class TopPane extends JPanel {

  /**
   * Two instances of an InfoView and a ButtonControl view
   * are used to construct the Top View object.
   * @param ifp The InfoView instance to attach with
   * @param bctp The ButtonControl instance to attach with
   */
  public TopPane(InfoViewTP ifp, ButtonControlTP bctp) {
    setPreferredSize(new Dimension(Manager.TOPPANE_MIN_WIDTH, Manager.TOPPANE_MIN_HEIGHT));
    setMinimumSize(new Dimension(Manager.TOPPANE_MIN_WIDTH, Manager.TOPPANE_MIN_HEIGHT));
    setLayout(new BorderLayout());
    add(ifp, BorderLayout.PAGE_START);
    add(bctp, BorderLayout.PAGE_END);
  }
}
