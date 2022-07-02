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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.halcyon.utils.FolderInfo;
import com.jackmeng.halcyon.utils.VirtualFolder;
import com.jackmeng.tailwind.TailwindEvent.TailwindStatus;

/**
 * A playlist class that handles playing multiple tracks at once, for example,
 * what happens when another track ends? Well it start playing the next in
 * queue.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class TailwindPlaylist implements TailwindListener.StatusUpdateListener {
  /**
   * A main player instance to target the flow
   * of new resources towards.
   *
   * This is not an Audio interface or of any other that implements
   * the Audio interface. This is because this is a Tailwind subset class
   * meaning everything must be and only be compatible with every other
   * Tailwind classes without having to waste time checking and casting
   * a class if it is an instance of TailwindPlayer or of another Tailwind
   * respective class.
   */
  private TailwindPlayer player;

  /**
   * Individual pointers towards certain tracks
   * to play.
   *
   * Note: all of the tracks must be in the same folder,
   * although this will not cause any errors, it will greatly
   * confuse the processes of the program.
   *
   * This pointer list can also be modified; if it is necessary
   * to get the original state of the list, it is necessary to use
   * {@link #getCurrentFolderInfo()} and get the files from there.
   *
   * The above also acts as a refresh method to rescan the files.
   */
  private List<File> files;

  /**
   * The referenced FolderInfo object for the current playlist.
   */
  private FolderInfo info;

  private List<File> original;

  /**
   * Stores the list of audio tracks that have been played
   * so that the previous method can work properly.
   */
  private List<File> memory;

  private int i = 0;

  private boolean shuffled = false;

  private File currentTrack;

  private TailwindPlaylistEventManager events;

  private Random r;

  public TailwindPlaylist(TailwindPlayer instancePlayer, FolderInfo info) {
    if (info instanceof VirtualFolder) {
      VirtualFolder vf = (VirtualFolder) info;
      this.files = vf.getAsListFiles();
    } else {
      files = Arrays.asList(info.getFiles());
    }
    this.original = files;
    assert instancePlayer != null; // Not sure if this is actually going to cause an assertion exception, but just
                                   // in case. ;)
    this.player = instancePlayer;
    this.memory = new ArrayList<>();
    this.info = info;
    r = new Random();
    player.addStatusUpdateListener(this);
    events = new TailwindPlaylistEventManager();
    events.addStatusListener(new TailwindPlaylistCLI());
    events.dispatchStatus(TailwindPlaylistStatusEvent.ARMED);
  }


  /**
   * @param info
   */
  public void renewPlaylist(FolderInfo info) {
    if (player.isOpen() || player.isPlaying()) {
      player.stop();
      player.close();
    }
    if (info instanceof VirtualFolder) {
      VirtualFolder vf = (VirtualFolder) info;
      this.files = vf.getAsListFiles();
    } else {
      files = Arrays.asList(info.getFiles());
    }
    this.original = files;
    this.memory = new ArrayList<>();
    shuffled = false;
    i = 0;
    player.open(files.get(i));
  }


  /**
   * @param e
   * @return boolean
   */
  public boolean addStatusUpdateListener(TailwindPlaylistListener.TailwindPlaylistStatusListener e) {
    return events.addStatusListener(e);
  }


  /**
   * @param e
   * @return boolean
   */
  public boolean addUpdateListener(TailwindPlaylistListener.TailwindPlaylistUpdateEvent e) {
    return events.addUpdateListener(e);
  }


  /**
   * @return File[]
   */
  public File[] getTracks() {
    return files.toArray(new File[0]);
  }


  /**
   * @return TailwindPlayer
   */
  public TailwindPlayer getPlayer() {
    return player;
  }


  /**
   * @return FolderInfo
   */
  public FolderInfo getCurrentFolderInfo() {
    return info;
  }


  /**
   * @return File
   */
  public File getCurrentTrack() {
    return currentTrack;
  }


  /**
   * @return File[]
   */
  public File[] getMemory() {
    return memory.toArray(new File[0]);
  }

  /**
   * Calls for the next audio track to be played, this track
   * handles going through the array no matter what.
   */
  public void next() {
    if (i == files.size()) {
      i = 0;
    }
    setCurrentTrack(files.get(i));
    i++;
    start();
  }

  /**
   * Calls for the previous audio track that was played.
   */
  public void previous() {
    if (i == 0) {
      events.dispatchStatus(TailwindPlaylistStatusEvent.EOL);
    } else {
      i--;
      setCurrentTrack(files.get(i));
      start();
    }
  }


  /**
   * @return File[]
   */
  public File[] getOriginalTracks() {
    return original.toArray(new File[0]);
  }

  /**
   * Using the FolderInfo object, we gather the information
   * regarding the avaliable tracks.
   */
  public void unShuffle() {
    files = original;
    shuffled = false;
  }

  /**
   * Forces a certain track to be opened right now
   *
   * Note: This method simply closes the old
   * stream (refer to the TailwindPlayer class) and opens
   * the parameter's File object.
   *
   * This should be used with caution as this method is also
   * used internally to set things related to the current playlist.
   *
   * @param f The file or resource to set to the current stream.
   */
  public void setCurrentTrack(File f) {
    player.open(f);
    this.currentTrack = f;
    memory.add(f);
    events.dispatchUpdate(new TailwindPlaylistEvent(f));
    events.dispatchStatus(TailwindPlaylistStatusEvent.FORCE_SET_FILE);
  }

  /**
   * This method is called by an external source when a signal
   * to start playing is given.
   */
  public void start() {
    Debugger.warn(currentTrack.getAbsolutePath());
    if (!player.isOpen()) {
      player.play();
      events.dispatchStatus(TailwindPlaylistStatusEvent.STARTED);
    }
  }

  public void stop() {
    player.stop();
    player.close();
    events.dispatchStatus(TailwindPlaylistStatusEvent.STOPPED);
  }

  /**
   * An entry point method that calls for a shuffling to the
   * track list.
   *
   * It might be unnecessary but optimization is done by determining
   * how many items there are and performing the necessary shuffling
   * algorithm either STL or using Fishy-Yates.
   */
  public void shuffle() {
    if (!shuffled) {
      if (files.size() > 30) {
        Collections.shuffle(files);
      } else {
        fisherYates();
      }
      shuffled = true;
      events.dispatchStatus(TailwindPlaylistStatusEvent.SHUFFLING);
    }
  }

  private void fisherYates() {
    for (int i = files.size() - 1; i > 0; i--) {
      int t = r.nextInt(i + 1);
      File file = files.get(i);
      files.set(i, files.get(t));
      files.set(t, file);
    }
  }


  /**
   * @param status
   */
  @Override
  public void statusUpdate(TailwindStatus status) {
    if (status.equals(TailwindStatus.END)) {
      next();
    }
  }
}
