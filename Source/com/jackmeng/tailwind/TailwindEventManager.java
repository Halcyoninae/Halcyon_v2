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

import com.jackmeng.halcyon.utils.Wrapper;
import com.jackmeng.tailwind.TailwindEvent.TailwindStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * A global scoped targeted towards managing multiple
 * Listeners for the BigContainer player all at the same time without
 * having to hold multiple Lists directly.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class TailwindEventManager {
  private List<TailwindListener.TimeUpdateListener> timeListeners;
  private List<TailwindListener.StatusUpdateListener> statusUpdateListeners;
  private List<TailwindListener.GenericUpdateListener> genericUpdateListeners;
  private List<TailwindListener.FrameBufferListener> bufferListeners;

  public TailwindEventManager() {
    timeListeners = new ArrayList<>();
    statusUpdateListeners = new ArrayList<>();
    genericUpdateListeners = new ArrayList<>();
    bufferListeners = new Vector<>();
  }


  /**
   * @param e
   * @return boolean
   */
  public boolean addTimeListener(TailwindListener.TimeUpdateListener e) {
    return timeListeners.add(e);
  }


  /**
   * @param e
   * @return boolean
   */
  public boolean addStatusUpdateListener(TailwindListener.StatusUpdateListener e) {
    return statusUpdateListeners.add(e);
  }


  /**
   * @param e
   * @return boolean
   */
  public boolean addGenericUpdateListener(TailwindListener.GenericUpdateListener e) {
    return genericUpdateListeners.add(e);
  }


  /**
   * @param e
   * @return boolean
   */
  public boolean addFrameBufferListener(TailwindListener.FrameBufferListener e) {
    return bufferListeners.add(e);
  }


  /**
   * @return List<TimeUpdateListener>
   */
  public List<TailwindListener.TimeUpdateListener> getTimeListeners() {
    return timeListeners;
  }


  /**
   * @return List<StatusUpdateListener>
   */
  public List<TailwindListener.StatusUpdateListener> getStatusUpdateListeners() {
    return statusUpdateListeners;
  }


  /**
   * @return List<GenericUpdateListener>
   */
  public List<TailwindListener.GenericUpdateListener> getGenericUpdateListeners() {
    return genericUpdateListeners;
  }


  /**
   * @return List<FrameBufferListener>
   */
  public List<TailwindListener.FrameBufferListener> getFrameBufferListeners() {
    return bufferListeners;
  }


  /**
   * @param time
   */
  public synchronized void dispatchTimeEvent(long time) {
    Wrapper.async(() -> {
      for (TailwindListener.TimeUpdateListener e : timeListeners) {
        e.trackCurrentTime(time);
      }
    });

  }


  /**
   * @param status
   */
  public synchronized void dispatchStatusEvent(TailwindStatus status) {
    Wrapper.async(() -> {
      for (TailwindListener.StatusUpdateListener e : statusUpdateListeners) {
        e.statusUpdate(status);
      }
    });

  }


  /**
   * @param event
   */
  public synchronized void dispatchGenericEvent(TailwindEvent event) {
    Wrapper.async(() -> {
      for (TailwindListener.GenericUpdateListener e : genericUpdateListeners) {
        e.genericUpdate(event);
      }
    });
  }


  /**
   * @param buffer
   */
  public synchronized void dispatchNewBufferEvent(byte[] buffer) {
    Wrapper.threadedRun(() -> {
      synchronized (bufferListeners) {
        for (TailwindListener.FrameBufferListener e : bufferListeners) {
          e.frameUpdate(buffer);
        }
      }
    });
  }

}
