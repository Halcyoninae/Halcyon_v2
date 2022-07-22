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

package com.halcyoninae.cosmos.components.bbloc;

import com.halcyoninae.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;

public class BBlocView extends JPanel {
  public BBlocView() {
    super();
    setPreferredSize(new Dimension(Manager.B_MIN_WIDTH, Manager.B_MIN_HEIGHT));
    setMinimumSize(new Dimension(Manager.B_MIN_WIDTH, Manager.B_MIN_HEIGHT));
  }


  /**
   * @param buttons
   */
  public void addBBlockButtons(BBlocButton... buttons) {
    for (BBlocButton b : buttons) {
      add(b.getComponent());
    }
  }
}
