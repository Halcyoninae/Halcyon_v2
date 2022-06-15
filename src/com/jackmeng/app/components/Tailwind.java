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

package com.jackmeng.app.components;

import javax.swing.*;

import com.jackmeng.app.events.ForceMaxSize;
import com.jackmeng.app.events.InstantClose;
import com.jackmeng.constant.Global;
import com.jackmeng.constant.Manager;

import java.awt.*;

/**
 * BigContainer is the main window for the program,
 * it is the parent container for BottomPane and TopPane.
 * 
 * It performs no other tasks but to align the components together
 * in a top-to-bottom fashion.
 * 
 * @see com.jackmeng.app.components.bottompane.BottomPane
 * @see com.jackmeng.app.components.toppane.TopPane
 * 
 * @author Jack Meng
 * @since 3.0
 */
public class Tailwind implements Runnable {
  private JFrame container;
  private JSplitPane mainPane;

  /**
   * A JSplitPane contains a BottomPane and TopPane that
   * will be used as the content pane for the JFrame.
   * @param mainPane The JSplitPane instance to attach with
   */
  public Tailwind(JSplitPane mainPane) {
    this.mainPane = mainPane;
    this.mainPane.setBorder(BorderFactory.createEmptyBorder());
    container = new JFrame("Halcyon Beta ~ exoad");
    container.setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
    container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    container.setPreferredSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT));
    container.setMaximumSize(new Dimension(Manager.MAX_WIDTH, Manager.MAX_HEIGHT));
    container.addComponentListener(
        new ForceMaxSize(container, Manager.MAX_WIDTH, Manager.MAX_HEIGHT, Manager.MIN_WIDTH, Manager.MIN_HEIGHT));
    container.getContentPane().add(mainPane);
    
    container.addWindowListener(new InstantClose());
  }

  /**
   * Returns the JFrame instance
   * @return The JFrame instance
   */
  public JFrame getFrame() {
    return container;
  }

  @Override
  public void run() {
    container.pack();
    container.setLocationRelativeTo(null);
    container.setVisible(true);
  }

}