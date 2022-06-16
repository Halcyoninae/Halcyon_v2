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

package com.jackmeng.audio;

import com.jackmeng.constant.Global;
import com.jackmeng.debug.Debugger;
import com.jackmeng.simple.audio.AudioException;
import com.jackmeng.simple.audio.StreamedAudio;

import java.io.File;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;

/**
 * A simplified version of the {@link com.jackmeng.simple.audio.Audio} interface
 * and all of it's subsets in order to make it easier to communicate with and
 * utilize
 * in the final program.
 *
 * This simplification is due to some of the methods not being to be needed and
 * to
 * have much more control over the playback library and to make it a global
 * scope player instead of having to reinit everything on something new.
 *
 * @author Jack Meng
 * @see com.jackmeng.simple.audio.Audio
 * @see com.jackmeng.simple.audio.StreamedAudio
 * @since 3.0
 */
public class Player {
  private StreamedAudio audio;
  private String currentAbsolutePath = "";
  private boolean isLooping = false, isPlayListShuffling = false;

  /**
   * Constructs a player with a blank mp3 file
   */
  public Player() {
    this(Global.rd.getFromAsFile(PlayerManager.BLANK_MP3_FILE));
  }

  /**
   * Constructs a player with a file location
   *
   * Note: This constructor does assert for the file path leading to the file to
   * be having access to the file
   * or the file existing at all.
   * 
   * @param file The absolute file path leading to the audio track
   */
  public Player(String file) {
    try {
      audio = new StreamedAudio(new File(file));
      audio.addAudioListener(new DefaultAudioListener(this));
      currentAbsolutePath = file;
    } catch (AudioException e) {
      Debugger.log(e);
    }
  }

  /**
   * Constructs a player with a file object
   * 
   * @param f The file object
   */
  public Player(File f) {
    try {
      audio = new StreamedAudio(f);
      currentAbsolutePath = f.getAbsolutePath();
      audio.addAudioListener(new DefaultAudioListener(this));
    } catch (AudioException e) {
      Debugger.log(e);
    }
  }

  /**
   * Starts playing the audio
   */
  public void play() {
    try {
      audio.open();
    } catch (AudioException e) {
      Debugger.log(e);
    }
    audio.play();
  }

  public void setLooping(boolean b) {
    isLooping = b;
  }

  public boolean isLooping() {
    return isLooping;
  }

  public void setShuffling(boolean b) {
    isPlayListShuffling = b;
  }

  public boolean isShuffling() {
    return isPlayListShuffling;
  }

  /**
   * Resets the audio to a different track.
   * 
   * This method will create the new track in a threaded manner in order
   * prevent any other processes from being blocked.
   * 
   * @param f The new file location (absolute path)
   */
  public void setFile(String f) {
    this.currentAbsolutePath = f;
    if (audio.isOpen() || audio.isPlaying()) {
      audio.stop();
      audio.close();
    }
    new Thread(
        () -> {
          try {
            this.audio = new StreamedAudio(new File(f));
          } catch (AudioException e) {
            e.printStackTrace();
          }
        })
        .start();
  }

  public String getCurrentFile() {
    return currentAbsolutePath;
  }

  public StreamedAudio getStream() {
    return audio;
  }

  public Control getControl(String key) {
    return audio.getControls().get(key);
  }

  public float convertVolume(float zeroToHundred) {
    try {
      FloatControl control = (FloatControl) audio
          .getControls()
          .get("Master Gain");
      float range = control.getMaximum() - control.getMinimum();
      return (zeroToHundred / 100.0f) * range + control.getMinimum();
    } catch (NullPointerException e) {
      return 0;
    }
  }
}
