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

package com.halcyoninae.tailwind.wrapper;

import com.halcyoninae.cosmos.components.dialog.LoadingDialog;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.halcyon.utils.TimeParser;
import com.halcyoninae.tailwind.AudioInfo;
import com.halcyoninae.tailwind.TailwindPlayer;
import com.halcyoninae.tailwind.TailwindPlaylist;

import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.File;

/**
 * This simplification is due to some of the methods not being to be needed and
 * to
 * have much more control over the playback library and to make it a global
 * scope player instead of having to reinit everything on something new.
 *
 * 这种简化是由于不需要某些方法，并且至
 * 对播放库有更多的控制权并使其成为全局
 * 范围播放器，而不必在新事物上重新启动所有内容。
 *
 * @author Jack Meng
 * @since 3.0
 */
public class Player {
  private TailwindPlaylist audio;
  private String currentAbsolutePath = "";

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
    this(new File(file));
  }

  /**
   * Constructs a player with a file object
   *
   * @param f The file object
   */
  public Player(File f) {
    try {
      audio = new TailwindPlaylist();
      currentAbsolutePath = f.getAbsolutePath();
    } catch (Exception e) {
      Debugger.log(e);
    }
  }

  /**
   * Starts playing the audio
   */
  public void play() {
    File f = new File(currentAbsolutePath);
    try {
      if (new AudioInfo(f, false).getTag(AudioInfo.KEY_MEDIA_DURATION) == null) {
        LoadingDialog ld = new LoadingDialog("<html><p>No duration metadata found<br>Seeking...</p></html>", true);
        SwingUtilities.invokeLater(ld::run);
        Debugger.warn("No proper duration metadata found for this audio file...\nLagging to find the frame length.");
      }
    } catch (Exception e) {
      // IGNORE
    }
    audio.playlistStart(f);
  }

  /**
   * @param b
   */
  public void setLooping(boolean b) {
    audio.setLoop(b);
  }

  /**
   * @param percent
   */
  public void setVolume(float percent) {
    audio.setGain(percent);
  }

  /**
   * @return boolean
   */
  public boolean isLooping() {
    return audio.isLoop();
  }

  /**
   * @param b
   */
  public void setShuffling(boolean b) {
    audio.setAutoPlay(b);
  }

  public void requestNextTrack() {
    audio.forwardTrack();
  }

  public void requestPreviousTrack() {
    audio.backTrack();
  }

  /**
   * @return boolean
   */
  public boolean isShuffling() {
    return audio.isAutoPlay();
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
  }

  /**
   * @return String
   */
  public String getCurrentFile() {
    return currentAbsolutePath;
  }

  /**
   * @return TailwindPlayer
   */
  public TailwindPlayer getStream() {
    return audio;
  }

  public void absolutePlay() {
    audio.play();
  }

  /**
   * @return String
   */
  public String getStringedTime() {
    return TimeParser.fromSeconds((int) audio.getPosition() * 1000) + " / "
        + TimeParser.fromSeconds((int) audio.getLength() * 1000);
  }

  /**
   * @param key
   * @return Control
   */
  public Control getControl(String key) {
    return audio.getControls().get(key);
  }

  /**
   * @param zeroToHundred
   * @return float
   */
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

  /**
   * @return String
   */
  public String toString() {
    return "isOpen: " + audio.isOpen() + "\nisPlaying" + audio.isPlaying() + "\nisPaused" + audio.isPaused();
  }
}
