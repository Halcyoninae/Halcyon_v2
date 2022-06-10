package com.jackmeng.audio;

import java.io.File;

import com.jackmeng.app.Global;

import de.ralleytn.simple.audio.Audio;
import de.ralleytn.simple.audio.AudioEvent;
import de.ralleytn.simple.audio.AudioException;
import de.ralleytn.simple.audio.AudioListener;
import de.ralleytn.simple.audio.StreamedAudio;

public class Player {

  private String current = "";
  private Audio audio;
  private transient Thread worker;

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

  public void addAudioListener(AudioListener... listeners) {
    for (AudioListener listener : listeners) {
      audio.addAudioListener(listener);
    }
  }
}
