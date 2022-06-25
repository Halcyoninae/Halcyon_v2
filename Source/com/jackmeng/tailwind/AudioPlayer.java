package com.jackmeng.tailwind;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AudioPlayer implements Audio {
  private URL resource;

  public AudioPlayer() {
    
  }

  @Override
  public void open(File url) {
    try {
      this.resource = url.toURI().toURL();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void open(URL url) {
  }

  @Override
  public void close() {
  }

  @Override
  public void play() {
  }

  @Override
  public void setGain(float percent) {
  }

  @Override
  public void setBalance(float balance) {
  }

  @Override
  public void setMute(boolean mute) {
  }

  @Override
  public void resume() {
  }

  @Override
  public void seek(long millis) {
  }

  @Override
  public void setPosition(long millis) {
  }

  @Override
  public void pause() {
  }

  @Override
  public void stop() {
  }

}
