package com.jackmeng.audio;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import com.jackmeng.app.constant.Global;
import com.jackmeng.app.constant.Manager;
import com.jackmeng.app.utils.DeImage;

import java.awt.image.BufferedImage;

public class AudioInfo {
  private File f;
  private Map<String, String> tags;
  private Tag t;
  private AudioHeader header;

  public static final String KEY_ABSOLUTE_FILE_PATH = "absFilePath", KEY_FILE_NAME = "fileName",
      KEY_MEDIA_DURATION = "mediaDur", KEY_MEDIA_TITLE = "mediaTitle", KEY_BITRATE = "mediaBitRate",
      KEY_SAMPLE_RATE = "mediaSRate", KEY_ALBUM = "mediaAlbum", KEY_GENRE = "mediaGenre",
      KEY_MEDIA_ARTIST = "mediaArtist", KEY_ARTWORK = "mediaSpecial";

  public AudioInfo(File f) {
    this.f = f;
    AudioFile af = null;
    try {
      af = AudioFileIO.read(f);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (af != null) {
      t = af.getTag();
      header = af.getAudioHeader();
    }
    initTags();
  }

  public AudioInfo(String locale) {
    this.f = new File(locale);
    AudioFile af = null;
    try {
      af = AudioFileIO.read(f);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (af != null) {
      t = af.getTag();
      header = af.getAudioHeader();
    }
    initTags();
  }

  public AudioInfo() {
    defInitTags();
  }

  private boolean checkEmptiness(String s) {
    return s == null || s.isEmpty();
  }

  private void defInitTags() {
    tags = new HashMap<String, String>();
    tags.put(KEY_ABSOLUTE_FILE_PATH, "Nowhere");
    tags.put(KEY_FILE_NAME, "Nothing.mp3");
    tags.put(KEY_MEDIA_DURATION, "0");
    tags.put(KEY_MEDIA_TITLE, "Nothing");
    tags.put(KEY_BITRATE, "0kpbs");
    tags.put(KEY_SAMPLE_RATE, "0.0khz");
    tags.put(KEY_ALBUM, "");
    tags.put(KEY_GENRE, "");
    tags.put(KEY_MEDIA_ARTIST, "No-one");
    tags.put(KEY_ARTWORK, "");
  }

  private void initTags() {
    tags = new HashMap<>();
    tags.put(KEY_ABSOLUTE_FILE_PATH, f.getAbsolutePath());
    tags.put(KEY_FILE_NAME, f.getName());
    tags.put(KEY_MEDIA_DURATION, header.getTrackLength() + "");
    tags.put(KEY_MEDIA_TITLE, checkEmptiness(t.getFirst(FieldKey.TITLE)) ? f.getName() : t.getFirst(FieldKey.TITLE));
    tags.put(KEY_BITRATE, header.getBitRate() + "");
    tags.put(KEY_SAMPLE_RATE, header.getSampleRate() + "");
    tags.put(KEY_ALBUM, checkEmptiness(t.getFirst(FieldKey.ALBUM)) ? "Unknown" : t.getFirst(FieldKey.ALBUM));
    tags.put(KEY_GENRE, checkEmptiness(t.getFirst(FieldKey.GENRE)) ? "Unknown" : t.getFirst(FieldKey.GENRE));
    tags.put(KEY_MEDIA_ARTIST, checkEmptiness(t.getFirst(FieldKey.ARTIST)) ? "Unknown" : t.getFirst(FieldKey.ARTIST));
  }

  public BufferedImage getArtwork() {
    BufferedImage img = null;
    if (f.getAbsolutePath().endsWith(".mp3")) {
      MP3File mp = null;
      try {
        mp = new MP3File(f);
      } catch (IOException | TagException | ReadOnlyFileException | CannotReadException
          | InvalidAudioFrameException e1) {
        e1.printStackTrace();
      }
      if (mp.getTag().getFirstArtwork() != null) {
        try {
          img = (BufferedImage) mp.getTag().getFirstArtwork().getImage();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    if (img == null) {
      return DeImage.imageIconToBI(Global.rd.getFromAsImageIcon(Manager.INFOVIEW_DISK_NO_FILE_LOADED_ICON));
    } else {
      return img;
    }
  }

  public Map<String, String> getTags() {
    return tags;
  }

  public String getTag(String key) {
    return tags.get(key);
  }
}
