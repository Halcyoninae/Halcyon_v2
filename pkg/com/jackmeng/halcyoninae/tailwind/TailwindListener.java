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

package com.jackmeng.halcyoninae.tailwind;

import com.jackmeng.halcyoninae.tailwind.TailwindEvent.TailwindStatus;

/**
 * A class that holds a collection of a
 * bunch of implementable listeners based
 * on the current BigContainer Player.
 *
 * @author Jack Meng
 * @since 3.1
 */
public final class TailwindListener {
    /**
     * A listener to get information and updates
     * about the current position of the stream.
     *
     * @author Jack Meng
     * @since 3.1
     */
    public interface TimeUpdateListener {
        /**
         * @param time The current time in milliseconds
         */
        void trackCurrentTime(long time);
    }

    /**
     * A listener to get information regarding
     * the stream; for example, is the current stream playing, paused, open, or
     * closed?
     *
     * @author Jack Meng
     * @since 3.1
     */
    public interface StatusUpdateListener {
        /**
         * @param status A TailwindStatus object
         */
        void statusUpdate(TailwindStatus status);
    }

    /**
     * A listener to get information on the current
     * loaded audio file loaded into the stream.
     *
     * @author Jack Meng
     * @since 3.1
     */
    public interface GenericUpdateListener {
        /**
         * @param event A TailwindEvent that holds information regarding the current
         *              loaded audio file
         */
        void genericUpdate(TailwindEvent event);
    }

    /**
     * A listener that dispatches information regarding
     * the current buffer or frame.
     *
     * @author Jack Meng
     * @since 3.1
     */
    public interface FrameBufferListener {
        /**
         * @param samples A byte array representing the buffer at the current frame
         */
        void frameUpdate(byte[] samples);
    }
}
