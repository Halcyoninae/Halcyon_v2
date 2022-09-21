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

import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
import com.jackmeng.halcyoninae.tailwind.TailwindEvent.TailwindStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A sort of wrapper for the default TailwindPlayer class.
 * <p>
 * This media player is intended to be constantly fed a list
 * of media to play and can then keep track of what it had played.
 *
 * @author Jack Meng
 * @since 3.2
 * (Technically 3.1)
 */
public class TailwindPlaylist extends Tailwind implements TailwindListener.StatusUpdateListener {
    private final List<File> history;
    private boolean loop     = false, autoPlay = false;
    private int pointer      = 0;
    private float gain;
    private File currentFile = new File(".");

    public TailwindPlaylist() {
        super();
        history = new ArrayList<>();
        setForceCloseOnOpen(false);
        setDynamicAllocation(true); // for optimal performance
        addStatusUpdateListener(this);
        gain = Float.NaN;
    }

    /**
     * @param f
     */
    public void playlistStart(File f) {
        if (isPlaying()) {
            stop();
        }
        if (!this.currentFile.getAbsolutePath().equals(f.getAbsolutePath())) {
            history.add(f);
            this.currentFile = f;
            open(f);
            if (!Float.isNaN(gain)) {
                this.setGain(gain);
            }
            play();
        }
    }

    /**
     * @param f
     */
    public void rawPlay(File f) {
        close();
        open(f);
        if (!Float.isNaN(gain)) {
            this.setGain(gain);
        }
        play();
    }

    public void backTrack() {
        boolean state = false;
        if (history.size() > 1 && pointer - 1 >= 0) {
            Debugger.warn(pointer);
            pointer -= 1;
            Debugger.warn(pointer);
            if (isOpen()) {
                close();
            }
            open(history.get(pointer));
            play();
            state = true;
        }
        Debugger.good("Backtrack marked (" + state + ")...\nPointer Information: " + pointer + " | " + history.size());
    }

    public void forwardTrack() {
        boolean state = false;
        if (history.size() > 1 && pointer >= 0 && pointer < history.size() - 1) {
            Debugger.warn(pointer);
            pointer += 1;
            Debugger.warn(pointer);
            if (isOpen()) {
                close();
            }
            open(history.get(pointer));
            play();
            state = true;
        }
        Debugger.good(
            "Forwardtrack marked (" + state + ")...\nPointer Information: " + pointer + " | " + history.size());
    }


    /**
     * @param gain
     */
    @Override
    public void setGain(float gain) {
        super.setGain(gain);
        this.gain = gain;
    }

    /**
     * @return return the playlist history
     */
    public List<File> getHistory() {
        return history;
    }

    /**
     * @return int
     */
    public int getpointer() {
        return pointer;
    }

    /**
     * @return File
     */
    public File getCurrentTrack() {
        return currentFile;
    }

    /**
     * @param i
     * @return File
     */
    public File getFromHistory(int i) {
        return history.get((Math.min(i, history.size())));
    }

    /**
     * @return boolean
     */
    public boolean isLoop() {
        return loop;
    }

    /**
     * @param loop
     */
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    /**
     * @return boolean
     */
    public boolean isAutoPlay() {
        return autoPlay;
    }

    /**
     * @param autoPlay
     */
    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    /**
     * @param status
     */
    @Override
    public void statusUpdate(TailwindStatus status) {
        if (loop && status.equals(TailwindStatus.END)) {
            rawPlay(currentFile);
        } else if (status.equals(TailwindStatus.END)) {
            stop();
            close();
        }
    }
}
