package com.jackmeng.audio;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.tritonus.share.sampled.TAudioFormat;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import com.jackmeng.audio.api.Media;
import com.jackmeng.audio.api.Player;
import com.jackmeng.audio.errors.NoLocaleMediaException;

public class AudioPlayer implements Player {
  private ExecutorService executor;
  private Future<Void> musicTask;
  private AudioFormat origFormat;
  private Audio media;
  private AudioOut out;
  private volatile AudioInputStream ais;
  private int currentLineBufferSize = -1;

  @Override
  public void play() {

  }

  @Override
  public void open(String source) {
    media = new Audio(source);
  }

  @Override
  public void open(File source) {
    try {
      media = new Audio(source);
    } catch (NoLocaleMediaException e) {
      e.printStackTrace();
    }
  }

  private void init() {
    ais = media.getStream();
    origFormat = ais.getFormat();
  }

  private void sourceLine() {
    if(out.getSourceLine() == null) {
      AudioFormat format = ais.getFormat();
      int sampleSizeInBits = format.getSampleSizeInBits();
      if(format.getEncoding() == AudioFormat.Encoding.ULAW || format.getEncoding() == AudioFormat.Encoding.ALAW || sampleSizeInBits!= 8) {
        sampleSizeInBits = 16;
      }
      AudioFormat desiredFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), sampleSizeInBits, format.getChannels(), 
          sampleSizeInBits / 8 * format.getChannels(), format.getFrameRate(), format.isBigEndian());
      
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, desiredFormat);
      try {
        out.setSourceLine((SourceDataLine) AudioSystem.getLine(info));
      } catch (LineUnavailableException e) {
        e.printStackTrace();
      }
    }
  }

  private void initSourceLine() {
    if(out.getSourceLine() == null) {
      sourceLine();
    }
    if(!out.getSourceLine().isOpen()) {
      currentLineBufferSize = out.getSourceLine().getBufferSize() >= 0 ? ;
      out.getSourceLine().open(origFormat, currentLineBufferSize);
    }

  }

  @Override
  public long mediaDurationSeconds() {
    return 0;
  }

  @Override
  public void goTo(long seconds) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void stop() {

  }

  @Override
  public void dispose() {

  }

  @Override
  public void setVolume(float volume) {

  }

  @Override
  public void setMute(boolean mute) {

  }

  @Override
  public void setLoop(boolean loop) {

  }

  @Override
  public void setPan(float pan) {

  }

  @Override
  public float getVolume() {
    return 0;
  }

  @Override
  public float getPan() {
    return 0;
  }

  @Override
  public boolean isMuted() {
    return false;
  }

  @Override
  public boolean isLooping() {
    return false;
  }

}
