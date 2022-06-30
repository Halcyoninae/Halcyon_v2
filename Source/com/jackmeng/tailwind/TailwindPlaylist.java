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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
   */
  private List<File> files;

  private FolderInfo info;

  private File currentTrack;

  private Random r;

  public TailwindPlaylist(TailwindPlayer instancePlayer, FolderInfo info) {
    if (info instanceof VirtualFolder) {
      VirtualFolder vf = (VirtualFolder) info;
      this.files = vf.getAsListFiles();
    } else {
      files = Arrays.asList(info.getFiles());
    }
    assert instancePlayer != null; // Not sure if this is actually going to cause an assertion exception, but just
                                   // in case. ;)
    this.player = instancePlayer;
    this.info = info;
    r = new Random();
    player.addStatusUpdateListener(this);
  }

  public File[] getTracks() {
    return files.toArray(new File[0]);
  }

  public TailwindPlayer getPlayer() {
    return player;
  }

  public FolderInfo getCurrentFolderInfo() {
    return info;
  }

  /**
   * Forces a certain track to be opened right now
   *
   * Note: This method simply closes the old
   * stream (refer to the TailwindPlayer class) and opens
   * the parameter's File object.
   *
   * @param f The file or resource to set to the current stream.
   */
  public void setCurrentTrack(File f) {
    player.open(f);
    this.currentTrack = f;
  }

  /**
   * This method is called by an external source when a signal
   * to start playing is given.
   */
  public void start() {
    player.play();
  }

  public void shuffle() {
    if (files.size() > 15) {
      Collections.shuffle(files);
    } else {
      fisherYates();
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

  @Override
  public void statusUpdate(TailwindStatus status) {

  }
}
