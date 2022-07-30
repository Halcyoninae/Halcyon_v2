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

package com.halcyoninae.tailwind;

import com.halcyoninae.tailwind.TailwindEvent.TailwindStatus;

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
     * @param samples A float array representing the buffer at the current frame
     * @param s_valid
     */
    void frameUpdate(float[] samples, int s_valid);
  }
}
