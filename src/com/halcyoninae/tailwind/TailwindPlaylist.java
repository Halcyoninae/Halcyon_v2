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

package com.halcyoninae.tailwind;

import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.tailwind.TailwindEvent.TailwindStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Meng
 * @since 3.2
 * (Technically 3.1)
 */
public class TailwindPlaylist extends TailwindPlayer implements TailwindListener.StatusUpdateListener {
    private final List<File> history;
    private boolean loop = false, autoPlay = false;
    private int pointer = 0;
    private float gain;
    private File currentFile = new File(".");

    public TailwindPlaylist() {
        history = new ArrayList<>();
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
            play();
            if (gain != Float.NaN) {
                this.setGain(gain);
            }
        }
    }


    /**
     * @param f
     */
    public void rawPlay(File f) {
        open(f);
        play();
        if (gain != Float.NaN) {
            this.setGain(gain);
        }
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
        Debugger.good("Forwardtrack marked (" + state + ")...\nPointer Information: " + pointer + " | " + history.size());
    }

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
        return history.get((i > history.size() ? history.size() : i));
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
        }
    }

}
