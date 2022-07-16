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

package com.jackmeng.tailwind;

import java.util.ArrayList;
import java.util.List;

import com.jackmeng.halcyon.utils.Wrapper;

public class TailwindPlaylistEventManager {
  private List<TailwindPlaylistListener.TailwindPlaylistStatusListener> statusListeners;
  private List<com.jackmeng.tailwind.TailwindPlaylistListener.TailwindPlaylistUpdateEvent> updateListeners;

  public TailwindPlaylistEventManager() {
    statusListeners = new ArrayList<>();
    this.updateListeners = new ArrayList<>();
  }


  /**
   * @param listener
   * @return boolean
   */
  public boolean addStatusListener(TailwindPlaylistListener.TailwindPlaylistStatusListener listener) {
    return statusListeners.add(listener);
  }


  /**
   * @param listener
   * @return boolean
   */
  public boolean addUpdateListener(TailwindPlaylistListener.TailwindPlaylistUpdateEvent listener) {
    return updateListeners.add(listener);
  }


  /**
   * @param event
   */
  public void dispatchStatus(TailwindPlaylistStatusEvent event) {
    Wrapper.async(() -> {
      for (TailwindPlaylistListener.TailwindPlaylistStatusListener e : statusListeners) {
        e.playListStatusUpdate(event);
      }
    });
  }


  /**
   * @param event
   */
  public void dispatchUpdate(TailwindPlaylistEvent event) {
    Wrapper.async(() -> {
      for (TailwindPlaylistListener.TailwindPlaylistUpdateEvent e : updateListeners) {
        e.playListUpdate(event);
      }
    });
  }
}
