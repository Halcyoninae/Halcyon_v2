package com.jackmeng.audio.api;

import java.io.File;

public interface Player {
  void play();

  void open(String source);

  void open(File source);

  long mediaDurationSeconds();

  void goTo(long seconds);

  void pause();

  void stop();

  void dispose();

  void setVolume(float volume);

  void setMute(boolean mute);

  void setLoop(boolean loop);

  void setPan(float pan);

  float getVolume();

  float getPan();

  boolean isMuted();

  boolean isLooping();

}
