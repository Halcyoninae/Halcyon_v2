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

public class TailwindPlaylist {
  /**
   * A main player instance to target the flow
   * of new resources towards.
   */
  private Audio player;

  /**
   * Individual pointers towards certain tracks
   * to play.
   */
  private List<File> files;

  private Random r;

  public TailwindPlaylist(Audio instancePlayer, FolderInfo info) {
    if (info instanceof VirtualFolder) {
      VirtualFolder vf = (VirtualFolder) info;
      this.files = vf.getAsListFiles();
    } else {
      files = Arrays.asList(info.getFiles());
    }
    this.player = instancePlayer;
    r = new Random();
  }

  public TailwindPlaylist(Audio instancePlayer, File... files) {
    this.files = Arrays.asList(files);
    this.player = instancePlayer;
  }

  public File[] getTracks() {
    return files.toArray(new File[0]);
  }

  public Audio getPlayer() {
    return player;
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
}
