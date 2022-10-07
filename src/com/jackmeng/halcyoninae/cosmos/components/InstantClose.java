/*
 * Halcyon ~ exoad
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https://raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
 */

package com.jackmeng.halcyoninae.cosmos.components;

import com.jackmeng.halcyoninae.halcyon.Halcyon;
import com.jackmeng.halcyoninae.halcyon.runtime.Program;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;

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
