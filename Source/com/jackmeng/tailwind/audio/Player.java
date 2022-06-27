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

package com.jackmeng.tailwind.audio;

import com.jackmeng.halcyon.connections.properties.ResourceFolder;
import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.ProgramResourceManager;
import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.halcyon.utils.TextParser;
import com.jackmeng.halcyon.utils.TimeParser;
import com.jackmeng.tailwind.TailwindPlayer;

import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
import java.io.File;

/**
 * A simplified version of the {@link com.jackmeng.test.Audio} interface
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
 * @see com.jackmeng.test.Audio
 * @see com.jackmeng.test.StreamedAudio
 * @since 3.0
 */
public class Player {
  private TailwindPlayer audio;
  private String currentAbsolutePath = "";
  private boolean isLooping = false, isPlayListShuffling = false;
  private File f;

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
      audio = new TailwindPlayer();
      this.f = new File(file);
      currentAbsolutePath = file;
      audio.open(f);
    } catch (Exception e) {
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
      audio = new TailwindPlayer();
      this.f = f;
      currentAbsolutePath = f.getAbsolutePath();
      audio.open(f);
    } catch (Exception e) {
      Debugger.log(e);
    }
  }

  /**
   * Starts playing the audio
   */
  public void play() {
    try {
      audio.open(f);
    } catch (Exception e) {
      Debugger.log(e);
    }
    audio.play();
  }

  /**
   * This method should not be used!!
   *
   * {@link #play()} handles opening the stream before playing
   */
  public void open() {
    try {
      audio.open(f);
    } catch (Exception e) {
      Debugger.log(e);
    }
  }

  public void setLooping(boolean b) {
    isLooping = b;
  }

  public void setVolume(float percent) {
    audio.setGain(percent);
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
    if (audio.isOpen() || audio.isPlaying()) {
      audio.stop();
      audio.close();
    }
    this.currentAbsolutePath = f;
    audio.open(new File(f));
  }

  public String getCurrentFile() {
    return currentAbsolutePath;
  }

  public TailwindPlayer getStream() {
    return audio;
  }

  public synchronized String getStringedTime() {
    return TimeParser.fromSeconds((int) audio.getPosition() * 1000) + " / "
        + TimeParser.fromSeconds((int) audio.getLength() * 1000);
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
