package com.jackmeng.audio;

import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Control;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.Control.Type;

import com.jackmeng.audio.api.Out;

public class AudioOut implements Out {
  private FloatControl panControl;
  private FloatControl volumeControl;
  private BooleanControl muteControl;
  private BooleanControl loopControl;
  private SourceDataLine line;
  protected FloatControl audioBalance;

  public boolean permitted(Type controlType, Control c) {
    return c.getType().equals(controlType);
  }

  public float getVolume() {
    return permitted(FloatControl.Type.MASTER_GAIN, getVolumeControl()) ? getVolumeControl().getValue() : 0;
  }

  @Override
  public void drain() {
    if(line != null) {
      line.drain();
    }
  }

  @Override
  public void flush() {
    if(line != null && line.isRunning()) {
      line.flush();
    }
  }

  @Override
  public void free() {
    if(line != null) {
      line.close();
    }
  }

  @Override
  public void stop() {
    if(line != null) {
      line.stop();
    }
  }

  public void run() {
    if(line != null) {
      line.start();
    }
  }

  private void controlPane() {
    if(line != null) {
      if(line.isControlSupported(FloatControl.Type.PAN)) {
        panControl = (FloatControl) line.getControl(FloatControl.Type.PAN);
      }
    }
  }

  private void volumePane() {
    if(line != null) {
      if(line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
        volumeControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
      }
    }
  }

  private void mutePane() {
    if(line != null) {
      if(line.isControlSupported(BooleanControl.Type.MUTE)) {
        muteControl = (BooleanControl) line.getControl(BooleanControl.Type.MUTE);
      }
    }
  }

  private void balancePane() {
    if(line != null) {
      if(line.isControlSupported(FloatControl.Type.BALANCE)) {
        audioBalance = (FloatControl) line.getControl(FloatControl.Type.BALANCE);
      }
    }
  }

  @Override
  public void open(int bufferSize, AudioFormat file) throws LineUnavailableException {
    if(line != null) {
      line.open(file, bufferSize);
      if(line.isOpen()) {
        controlPane();
        volumePane();
        mutePane();
        balancePane();
      }
    }
  }

  @Override
  public void setPanControl(FloatControl panC) {
    panControl = panC;
  }

  @Override
  public void setVolumeControl(FloatControl volumeC) {
    volumeControl = volumeC;
  }

  @Override
  public void setMuteControl(BooleanControl muteC) {
    muteControl = muteC;
  }

  @Override
  public void setLoopControl(BooleanControl loopC) {
    loopControl = loopC;
  }

  @Override
  public void setSourceLine(SourceDataLine line) {
    this.line = line;
  }

  @Override
  public FloatControl getPanControl() {
    return panControl;
  }

  @Override
  public FloatControl getVolumeControl() {
    return volumeControl;
  }

  @Override
  public BooleanControl getMuteControl() {
    return muteControl;
  }

  @Override
  public BooleanControl getLoopControl() {
    return loopControl;
  }

  @Override
  public SourceDataLine getSourceLine() {
    return line;
  }

}
