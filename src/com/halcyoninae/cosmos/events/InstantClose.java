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

package com.halcyoninae.cosmos.events;

import com.halcyoninae.halcyon.Halcyon;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.runtime.Program;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * An event handler to handle any events
 * when the main big container is going
 * for closing.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class InstantClose implements WindowListener {

    /**
     * @param e
     */
    @Override
    public void windowOpened(WindowEvent e) {
        // TO BE IMPLEMENTED OR IGNORED
    }


    /**
     * @param e
     */
    @Override
    public void windowClosing(WindowEvent e) {
        Halcyon.bgt.getFrame().dispose();
        if (Global.player.getStream().isOpen() || Global.player.getStream().isPlaying()) {
            Global.player.getStream().close();
        }
        Program.forceSaveUserConf();
        System.exit(0);
    }


    /**
     * @param e
     */
    @Override
    public void windowClosed(WindowEvent e) {
    }


    /**
     * @param e
     */
    @Override
    public void windowIconified(WindowEvent e) {
        // TO BE IMPLEMENTED OR IGNORED
    }


    /**
     * @param e
     */
    @Override
    public void windowDeiconified(WindowEvent e) {
        // TO BE IMPLEMENTED OR IGNORED
    }


    /**
     * @param e
     */
    @Override
    public void windowActivated(WindowEvent e) {
        // TO BE IMPLEMENTED OR IGNORED
    }


    /**
     * @param e
     */
    @Override
    public void windowDeactivated(WindowEvent e) {
        // TO BE IMPLEMENTED OR IGNORED
    }

}
