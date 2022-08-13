/*
 *  Copyright: (C) 2022 MP4J Jack Meng
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

package com.halcyoninae.cosmos.events;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * This class forces a certain size limit upon a JFrame.
 * <p>
 * This means it can only be:
 * >= minWidth x >= minHeight
 * <p>
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
     *
     * @param MAX_WIDTH
     * @param MAX_HEIGHT
     * @param MIN_WIDTH
     * @param MIN_HEIGHT
     */
    public ForceMaxSize(int MAX_WIDTH, int MAX_HEIGHT, int MIN_WIDTH, int MIN_HEIGHT) {
        this.a = MAX_HEIGHT;
        this.b = MAX_WIDTH;
        this.c = MIN_HEIGHT;
        this.d = MIN_WIDTH;
    }


    /**
     * @param e
     */
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


    /**
     * @param e
     */
    // UNUSED
    @Override
    public void componentMoved(ComponentEvent e) {
    }


    /**
     * @param e
     */
    @Override
    public void componentShown(ComponentEvent e) {
    }


    /**
     * @param e
     */
    @Override
    public void componentHidden(ComponentEvent e) {
    }

}
