package com.jackmeng.audio;

import java.io.File;
import java.util.stream.Stream;

import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;

import com.formdev.flatlaf.ui.FlatListCellBorder.Default;
import com.jackmeng.app.constant.Global;
import com.jackmeng.debug.Debugger;

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
  private StreamedAudio audio;
  private String currentAbsolutePath = "";

  public Player() {
    this(Global.rd.getFromAsFile(PlayerManager.BLANK_MP3_FILE));
  }

  public Player(String file) {
    try {
      audio = new StreamedAudio(new File(file));
      audio.addAudioListener(new DefaultAudioListener());
      currentAbsolutePath = file;
    } catch (AudioException e) {
      Debugger.log(e);
    }
  }

  public Player(File f) {
    try {
      audio = new StreamedAudio(f);
      currentAbsolutePath = f.getAbsolutePath();
      audio.addAudioListener(new DefaultAudioListener());
    } catch (AudioException e) {
      Debugger.log(e);
    }
  }

  public void play() {
    try {
      audio.open();
    } catch (AudioException e) {
      Debugger.log(e);
    }
    audio.play();
  }

  public void setFile(String f) {
    this.currentAbsolutePath = f;
    if (audio.isOpen() || audio.isPlaying()) {
      audio.stop();
      audio.close();
    }
    try {
      this.audio = new StreamedAudio(new File(f));
    } catch (AudioException e) {
      e.printStackTrace();
    }
  }

  public String getCurrentFile() {
    return currentAbsolutePath;
  }

  public StreamedAudio getStream() {
    return audio;
  }

  public Control getControl(String key) {
    return audio.getControls().get(key);
  }

  public float convertVolume(float zeroToHundred) {
    FloatControl control = (FloatControl) audio.getControls().get("Master Gain");
    return (control.getMaximum() - control.getMinimum()) * zeroToHundred / 100 + control.getMinimum();
  }
}
