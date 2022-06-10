package com.jackmeng.audio;

import de.ralleytn.simple.audio.Audio;
import de.ralleytn.simple.audio.AudioEvent;
import de.ralleytn.simple.audio.AudioListener;

public class DefaultAudioListener implements AudioListener {
  private PlayType type;
  private Player p;

  public DefaultAudioListener(Player p) {
    this.p = p;
  }

  public synchronized void setPlayType(PlayType type) {
    this.type = type;
  }

  public Player getPlayer() {
    return p;
  }

  public synchronized PlayType getPlayType() {
    return type;
  }

  @Override
  public void update(AudioEvent arg0) {
    if (arg0.getType().equals(AudioEvent.Type.REACHED_END)) {
      if (type.equals(PlayType.STANDARD_CLOSE_ON_END)) {
        arg0.getAudio().close();
      } else if (type.equals(PlayType.STANDARD_LOOP)) {
        arg0.getAudio().loop(Audio.LOOP_ENDLESS);
      }
    }
  }
}
