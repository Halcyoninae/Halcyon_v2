package com.jackmeng.audio.api;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public interface Out {
  void drain();
  void flush();
  void free();
  void stop();
  void open(int bufferSize, AudioFormat file) throws LineUnavailableException;
  void setPanControl(FloatControl panC);
  void setVolumeControl(FloatControl volumeC);
  void setMuteControl(BooleanControl muteC);
  void setLoopControl(BooleanControl loopC);
  void setSourceLine(SourceDataLine line);
  FloatControl getPanControl();
  FloatControl getVolumeControl();
  BooleanControl getMuteControl();
  BooleanControl getLoopControl();
  SourceDataLine getSourceLine();
}
