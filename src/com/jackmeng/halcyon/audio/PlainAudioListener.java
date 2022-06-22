package com.jackmeng.halcyon.audio;

import com.jackmeng.simple.audio.AudioEvent;
import com.jackmeng.simple.audio.AudioListener;

public class PlainAudioListener implements AudioListener {
  private Player p;

  public PlainAudioListener(Player p) {
    this.p = p;
  }

  @Override
  public void update(AudioEvent argo) {
    if (argo.getType().equals(AudioEvent.Type.REACHED_END)) {
      p.getStream().close();
    }
  }
}
