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

package com.jackmeng.halcyon.audio;

import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.simple.audio.AudioEvent;
import com.jackmeng.simple.audio.AudioException;
import com.jackmeng.simple.audio.AudioListener;

import java.io.File;

/**
 * Represents a default audio listener,
 * that listens for the stream ending
 * and automatically closing the stream to save
 * memory and prevent memory leaks.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class DefaultAudioListener implements AudioListener {
  private Player p;
  private File curr, next;

  public DefaultAudioListener(Player p) {
    this.p = p;
    curr = null;
  }

  private static File getRandomFile(File folder) {
    File[] files = folder.listFiles();
    int random = (int) (Math.random() * files.length);
    return files[random];
  }

  @Override
  public void update(AudioEvent arg0) {
    Debugger.unsafeLog("AudioEvent: " + arg0.toString());
    if (arg0.getType().equals(AudioEvent.Type.REACHED_END)) {
      if (p.isShuffling()) {
        if (next != null) {
          p.setFile(next.getAbsolutePath());
        }
      } else if (p.isLooping()) {
        p.getStream().stop();
        try {
          p.getStream().open();
        } catch (AudioException e) {
          Debugger.log(e.getLocalizedMessage());
        }
        p.getStream().play();
      } else {
        p.getStream().stop();
        p.getStream().close();
      }
      Debugger.unsafeLog(p.isLooping() + " " + p.isShuffling());
    } else if (arg0.getType().equals(AudioEvent.Type.OPENED)) {
      curr = new File(p.getCurrentFile());
      new Thread(() -> next = getRandomFile(curr.getParentFile())).start();
    }
  }
}
