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
import java.util.Random;

import com.halcyoninae.halcyon.filesystem.PhysicalFolder;
import com.halcyoninae.tailwind.TailwindEvent.TailwindStatus;

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

  private PhysicalFolder info;

  private File current;

  private Random rnd;

  public TailwindPlaylist(PhysicalFolder info, TailwindPlayer player) {
    this.info = info;
    this.player = player;
    rnd = new Random();
  }


  /**
   * @return TailwindPlayer
   */
  public TailwindPlayer getPlayer() {
    return player;
  }


  /**
   * @param f
   */
  public void start(File f) {
    player.open(f);
    player.play();
    current = f;
  }

  public void stop() {
    player.close();
  }

  public void playNext() {
    File f = info.getFiles()[rnd.nextInt(info.getFiles().length)];
    if(current != null) {
      while(f.equals(current)) {
        f =info.getFiles()[rnd.nextInt(info.getFiles().length)];
      }
    }
    start(f);
  }


  /**
   * @param status
   */
  @Override
  public void statusUpdate(TailwindStatus status) {
    if(status.equals(TailwindStatus.END)) {
      playNext();
    }
  }
}
