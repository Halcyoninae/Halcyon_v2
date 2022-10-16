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

import java.io.File;
import java.net.URL;

/**
 * An interface that provides implementation for
 * Audio creation to the BigContainer Player classes.
 * <p>
 * This interface template does not provide every single
 * method that a player might need; thus, the programmer
 * must implement their own styles and methods to be used along.
 * <p>
 * An example of this implementation is TailwindPlayer
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.tailwind.Tailwind
 * @since 3.1
 */
public interface Audio {
    /**
     * Opens a File for reading
     *
     * @param url The File locale as a {@linkplain java.io.File}
     */
    void open(File url);

    /**
     * Opens a URL resource linker for reading
     *
     * @param url A URL object to a resource for the audio stream.
     */
    void open(URL url);

    /**
     * Close the stream buffer
     */
    void close();

    /**
     * Start reading the loaded stream into buffers
     */
    void play();

    /**
     * Sets the gain of the stream
     *
     * @param percent A percent value
     */
    void setGain(float percent);

    /**
     * Sets the balance of the stream
     *
     * @param balance The balance value as a float
     */
    void setBalance(float balance);

    /**
     * @param mute (true || false) if the stream should be on mute
     */
    void setMute(boolean mute);

    /**
     * Resume from where the stream left off after a pause.
     */
    void resume();

    /**
     * Similar to {@link #setPosition(long)}
     * but will provide additional safety
     * checks to make sure the time being seeked to
     * is not out of reach. (EOF)
     *
     * @param millis
     */
    void seekTo(long millis);

    /**
     * @param millis Moves the buffer reader to the millis frame
     */
    void setPosition(long millis);

    /**
     * Pause the current stream at the current spot.
     */
    void pause();

    /**
     * Stops the stream
     */
    void stop();
}
