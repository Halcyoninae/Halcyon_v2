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

package com.halcyoninae.cosmos.components.moreapps;

import com.halcyoninae.halcyon.Halcyon;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jack Meng
 * @since 3.3
 */
public class MoreApps extends JFrame implements Runnable {
  private final JScrollPane jsp;

  public MoreApps() {
    setPreferredSize(new Dimension(MoreAppsManager.MIN_WIDTH, MoreAppsManager.MIN_HEIGHT));
    setAlwaysOnTop(true);
    setUndecorated(true);

    jsp = new JScrollPane(null, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    setContentPane(jsp);
  }

  public void addComponent(JComponent ... c) {
    for(JComponent cc : c) {
    }
  }

  @Override
  public void run() {
    pack();
    setLocationRelativeTo(Halcyon.bgt.getFrame());
    setVisible(true);
  }
}
