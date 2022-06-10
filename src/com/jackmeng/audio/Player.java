package com.jackmeng.audio;

import java.io.File;

import com.jackmeng.app.Global;

import de.ralleytn.simple.audio.AudioException;
import de.ralleytn.simple.audio.AudioListener;
import de.ralleytn.simple.audio.StreamedAudio;

/**
 * A simplified version of the {@link de.ralleytn.simple.audio.Audio} interface
 * and all of it's subsets in order to make it easier to communicate with and utilize
 * in the final program.
 * 
 * This simplification is due to some of the methods not being to be needed and to
 * have much more control over the playback library.
 * 
 * @author Jack Meng
 * @see de.ralleytn.simple.audio.Audio
 * @see de.ralleytn.simple.audio.StreamedAudio
 * @since 3.0
 */
public class Player {

  private String current = "";
  private StreamedAudio audio;
  private Thread worker;

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

  public void play() {
    if(!worker.isInterrupted() || !worker.isAlive()) {
      worker = new Thread(() -> {
        try {
          audio.open();
          audio.play();
        } catch (AudioException e) {
          e.printStackTrace();
        }
      });
      worker.start();
    }
  }

  public void pause() {
    if(!worker.isInterrupted() || worker.isAlive()) {
      audio.pause();
    }
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
