package com.halcyoninae.tailwind;

import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

/**
 * This class serves no particular purpose but that
 * it is used when the audio format being detected is
 * incorrect.
 *
 * This helps to prevent unnecessary NullPointerExceptions
 * being thrown from the stream.
 *
 * @author Jack Meng
 * @since 3.3
 */
public class DestructiveAudio extends AudioInputStream {

  public DestructiveAudio(InputStream stream, AudioFormat format, long length) {
    super(stream, format, length);
  }

}
