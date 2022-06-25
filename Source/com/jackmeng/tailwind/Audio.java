package com.jackmeng.tailwind;

import java.io.File;
import java.net.URL;

public interface Audio {
  void open(File url);

  void open(URL url);

  void close();

  void play();

  void setGain(float percent);

  void setBalance(float balance);

  void setMute(boolean mute);

  void resume();

  void seek(long millis);

  void setPosition(long millis);

  void pause();

  void stop();
}
