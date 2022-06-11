package com.jackmeng.audio;

import com.jackmeng.debug.Debugger;

import simple.audio.AbstractAudio;
import simple.audio.AudioEvent;
import simple.audio.AudioListener;

public class DefaultAudioListener implements AudioListener {
  @Override
  public void update(AudioEvent arg0) {
    if (arg0.getType().equals(AudioEvent.Type.REACHED_END)) {
      arg0.getAudio().close();
    }
  }
}
