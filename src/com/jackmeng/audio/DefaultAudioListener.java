package com.jackmeng.audio;

import simple.audio.AudioEvent;
import simple.audio.AudioListener;

/**
 * Represents a default audio listener,
 * that listens for the stream ending
 * and automatically closing the stream to save 
 * memory and prevent memory leaks.
 * 
 * @author Jack Meng
 * @since 3.0
 */
public class DefaultAudioListener implements AudioListener {
  @Override
  public void update(AudioEvent arg0) {
    if (arg0.getType().equals(AudioEvent.Type.REACHED_END)) {
      arg0.getAudio().close();
    }
  }
}
