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

package com.jackmeng.halcyoninae.tailwind;

import com.jackmeng.halcyoninae.tailwind.audioinfo.AudioInfo;

/**
 * Represents a generic tailwind event
 * which contains certain things about the most recent
 * stream.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class TailwindEvent {
    private final AudioInfo current;

    public TailwindEvent(AudioInfo set) {
        this.current = set;
    }

    /**
     * @return The current event's AudioInfo object
     */
    public AudioInfo getCurrentAudioInfo() {
        return current;
    }

    /**
     * Enum constants that represents
     * the different status of a stream.
     *
     * @author Jack Meng
     * @since 3.1
     */
    public enum TailwindStatus {
        PLAYING, PAUSED, OPEN, CLOSED, END, RESUMED
    }
}
