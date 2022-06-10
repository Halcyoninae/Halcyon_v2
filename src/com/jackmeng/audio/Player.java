package com.jackmeng.audio;

import java.io.File;

import com.jackmeng.app.Global;

import simple.audio.AudioException;
import simple.audio.AudioListener;
import simple.audio.StreamedAudio;

/**
 * A simplified version of the {@link simple.audio.Audio} interface
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
 * @see simple.audio.Audio
 * @see simple.audio.StreamedAudio
 * @since 3.0
 */
public class Player {

  private String current = "";
  private StreamedAudio audio;

  public Player() {
    try {
      audio = new StreamedAudio(Global.rd.getFromAsFile(PlayerManager.BLANK_MP3_FILE));
      current = PlayerManager.BLANK_MP3_FILE;
      audio.addAudioListener(new DefaultAudioListener(this));
    } catch (AudioException e) {
      e.printStackTrace();
    }
  }

  public void setFile(String file) {
    this.current = file;
  }

  public void setFile(File f) {
    this.current = f.getAbsolutePath();
  }

  public String getCurrent() {
    return current;
  }

  public void startPlay() {
    try {
      audio.open();
      audio.play();
    } catch (AudioException e) {
      e.printStackTrace();
    }

  }

  public void pause() {
    audio.pause();
  }

  public StreamedAudio getAudio() {
    return audio;
  }

  public void addAudioListener(AudioListener... listeners) {
    for (AudioListener listener : listeners) {
      audio.addAudioListener(listener);
    }
  }
}
