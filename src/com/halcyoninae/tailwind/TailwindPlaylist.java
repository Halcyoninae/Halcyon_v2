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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.halcyoninae.tailwind.TailwindEvent.TailwindStatus;

/**
 * @author Jack Meng
 * @since 3.2
 * (Technically 3.1)
 */
public class TailwindPlaylist extends TailwindPlayer implements TailwindListener.StatusUpdateListener {
  private boolean loop = false, autoPlay = false;
  private List<File> history;
  private int indexKeeper = 0;
  private File currentFile;

  public TailwindPlaylist() {
    history = new ArrayList<>();
    currentFile = null;
    addStatusUpdateListener(this);
  }

  public void playlistStart(File f, boolean autoOpen) {
    if(isPlaying()) {
      stop();
    }
    this.currentFile = f;
    history.add(f);
    if(autoOpen) {
      open(f);
    }
    play();
    indexKeeper++;
  }

  public void rawPlay(File f) {
    open(f);
    play();
  }

  public void backTrack() {
    if (history.size() > 0) {
      indexKeeper--;
      open(history.get(indexKeeper));
      play();
    }
  }

  public void forwardTrack() {
    if (history.size() > 0) {
      indexKeeper++;
      open(history.get(indexKeeper));
      play();
    }
  }

  public int getIndexKeeper() {
    return indexKeeper;
  }

  public File getCurrentTrack() {
    return currentFile;
  }

  public File getFromHistory(int i) {
    return history.get((i > history.size() ? history.size() : i));
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
  public boolean isLoop() {
    return loop;
  }

  /**
   * @param autoPlay
   */
  public void setAutoPlay(boolean autoPlay) {
    this.autoPlay = autoPlay;
  }

  /**
   * @return boolean
   */
  public boolean isAutoPlay() {
    return autoPlay;
  }

  @Override
  public void statusUpdate(TailwindStatus status) {
    if (loop && status.equals(TailwindStatus.END)) {
      rawPlay(currentFile);
    }
  }

}
