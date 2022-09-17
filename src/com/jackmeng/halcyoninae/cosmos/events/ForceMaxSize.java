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

package com.jackmeng.halcyoninae.cosmos.events;

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
