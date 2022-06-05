package com.jackmeng.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;

import com.jackmeng.audio.api.Media;
import com.jackmeng.audio.errors.NoLocaleMediaException;

public class Audio implements Media {
  private File f;

  public Audio(File f) throws NoLocaleMediaException {
    if (f.isFile() || f.exists() || f.length() != 0) {
      this.f = f;
    } else {
      throw new NoLocaleMediaException("Media File not found or could have length 0: " + f.getAbsolutePath());
    }
  }

  public Audio(String str) {
    File fstr = new File(str);
    if (fstr.isFile() || fstr.exists() || fstr.length() != 0) {
      this.f = fstr;
    } else {
      try {
        throw new NoLocaleMediaException("Media File not found or could have length 0: " + f.getAbsolutePath());
      } catch (NoLocaleMediaException e) {
        e.printStackTrace();
      }
    }
  }

  public static String getExtension(File f) {
    String ext = null;
    String s = f.getName();
    int i = s.lastIndexOf('.');

    if (i > 0 && i < s.length() - 1) {
      ext = s.substring(i + 1).toLowerCase();
    }
    return ext;
  }

  @Override
  public File getFile() {
    return f;
  }

  @Override
  public long getDuration() {
    long ms = Long.MIN_VALUE;
    if (getExtension(f) != null) {
      if (getExtension(f).equals("mp3")) {
        try {
          ms = new MP3File(f).getAudioHeader().getTrackLength() * 1000L;
        } catch (IOException | TagException | ReadOnlyFileException | CannotReadException
            | InvalidAudioFrameException e) {
          e.printStackTrace();
        }
      } else if (getExtension(f).equals("wav")) {
        try {
          AudioInputStream ais = AudioSystem.getAudioInputStream(f);
          ms = (long) (ais.getFrameLength() * 1000 / ais.getFormat().getFrameRate());
        } catch (IOException | UnsupportedAudioFileException e) {
          e.printStackTrace();
        }
      }
    }
    return ms;
  }

  @Override
  public AudioFileFormat getFormat() {
    try {
      return AudioSystem.getAudioFileFormat(f);
    } catch (UnsupportedAudioFileException | IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public AudioInputStream getStream() {
    try {
      return AudioSystem.getAudioInputStream(f);
    } catch (UnsupportedAudioFileException | IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
