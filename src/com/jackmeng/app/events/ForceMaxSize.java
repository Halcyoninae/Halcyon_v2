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

package com.jackmeng.app.events;

import javax.swing.JFrame;
import java.awt.event.*;

/**
 * This class forces a certain size limit upon a JFrame.
 * 
 * This means it can only be:
 * >= minWidth x >= minHeight
 * 
 * This mode can be quite janky at times, making the application
 * glitch around if the Max or Min size is exceeded.
 * 
 * @author Jack Meng
 * @since 3.0
 */
public class ForceMaxSize implements ComponentListener {
  private int a = 0, b = 0, c = 0, d = 0;

  /**
   * Constructs this object instance with the necessary instance variables.
   * @param c The JFrame instance
   * @param MAX_WIDTH
   * @param MAX_HEIGHT
   * @param MIN_WIDTH
   * @param MIN_HEIGHT
   */
  public ForceMaxSize(JFrame c, int MAX_WIDTH, int MAX_HEIGHT, int MIN_WIDTH, int MIN_HEIGHT) {
    this.a = MAX_HEIGHT;
    this.b = MAX_WIDTH;
    this.c = MIN_HEIGHT;
    this.d = MIN_WIDTH;
  }

  @Override
  public void componentResized(ComponentEvent e) {
    JFrame de = (JFrame) e.getComponent();
    if (de.getWidth() > b) {
      de.setSize(b, a);
    }
    if (de.getHeight() > a) {
      de.setSize(b, a);
    }
    if (de.getWidth() < d) {
      de.setSize(d, de.getHeight());
    }
    if (de.getHeight() < c) {
      de.setSize(de.getWidth(), c);
    }
    
  }

  // UNUSED
  @Override
  public void componentMoved(ComponentEvent e) {
  }

  @Override
  public void componentShown(ComponentEvent e) {
  }

  @Override
  public void componentHidden(ComponentEvent e) {
  }

}
