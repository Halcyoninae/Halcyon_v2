package com.jackmeng.audio.api;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;

public interface Media {
  File getFile();
  long getDuration();
  AudioFileFormat getFormat();
  AudioInputStream getStream();
}
