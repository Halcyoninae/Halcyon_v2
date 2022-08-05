/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

package com.halcyoninae.tailwind;

import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.Manager;
import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.halcyon.utils.DeImage;
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class holds information regarding an audio
 * file; specifically, an MP3 file which can be parsed
 * into ID3 tags.
 *
 * Other formats that are supported such as raw PCM,WAV,OGG
 * are not parsable.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class AudioInfo {
  private File f;
  private Map<String, String> tags;
  private Tag t;
  private AudioHeader header;

  public static final String KEY_ABSOLUTE_FILE_PATH = "absFilePath", KEY_FILE_NAME = "fileName",
      KEY_MEDIA_DURATION = "mediaDur", KEY_MEDIA_TITLE = "mediaTitle", KEY_BITRATE = "mediaBitRate",
      KEY_SAMPLE_RATE = "mediaSRate", KEY_ALBUM = "mediaAlbum", KEY_GENRE = "mediaGenre",
      KEY_MEDIA_ARTIST = "mediaArtist", KEY_ARTWORK = "mediaSpecial", KEY_TRACK = "mediaTrack";

  /**
   * Constructs the AudioInfo object with the specified file.
   *
   * @param f The file to construct the AudioInfo object with.
   */
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

  public AudioInfo(File f, boolean s)
      throws InvalidAudioFrameException, CannotReadException, IOException, TagException, ReadOnlyFileException {
    this.f = f;
    AudioFile af = null;
    af = AudioFileIO.read(f);
    if (af != null) {
      t = af.getTag();
      header = af.getAudioHeader();
    }
    initTags();
  }

  /**
   * Constructs the AudioInfo object with the specified file path.
   *
   * @param locale The file path to construct the AudioInfo object with. (ABSOLUTE
   *               FILE PATH)
   */
  public AudioInfo(String locale) {
    this.f = new File(locale);
    AudioFile af = null;
    try {
      af = AudioFileIO.read(f);
    } catch (Exception e) {
      Debugger.log(e);
    }
    if (af != null) {
      t = af.getTag();
      header = af.getAudioHeader();
    }
    initTags();
  }

  /**
   * Initializes the tags map and adds default values to the map
   *
   * Default init
   */
  public AudioInfo() {
    defInitTags();
  }

  /**
   * Checks if the given String is in any way empty.
   *
   * @param s The string to check
   * @return (true || false) Depending on if the string is empty or not.
   */
  public boolean checkEmptiness(String s) {
    return s == null || s.isEmpty();
  }


  /**
   * @param forceSetMap
   */
  public void forceSet(Map<String, String> forceSetMap) {
    Debugger.warn("Attempting a force set for the current AudioInfo...!!! (Prepare for unforseen consequences (jk)");
    this.tags = forceSetMap;
  }

  /**
   * Initializes the tags map and adds default values to the map
   */
  public void defInitTags() {
    tags = new HashMap<>();
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

  /**
   * Initializes the tags map and adds the appropriate values from the
   * parsed Audio File to the map.
   */
  public void initTags() {
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

  /**
   * Returns the artwork of the Audio File, if there isn't any, then it
   * returns the specified default artwork.
   *
   * @return A BufferedImage representing the artwork without any modifications.
   */
  public BufferedImage getArtwork() {
    BufferedImage img = null;
    try {
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
        return AudioInfo.getDefaultIcon();
      } else {
        return img;
      }
    } catch (NullPointerException e) {
      return AudioInfo.getDefaultIcon();
    }
  }

  /**
   * @return boolean
   */
  public boolean hasArtwork() {
    BufferedImage img = null;
    try {
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
      return img != null;
    } catch (NullPointerException e) {
      return false;
    }
  }

  /**
   * @return BufferedImage
   */
  public static BufferedImage getDefaultIcon() {
    return DeImage.imageIconToBI(Global.rd.getFromAsImageIcon(Manager.INFOVIEW_DISK_NO_FILE_LOADED_ICON));
  }

  /**
   * Returns the map of tags to the Audio file
   *
   * @return A Map of String, String representing the parsed tags from the audio
   *         file.
   */
  public Map<String, String> getTags() {
    return tags;
  }

  /**
   * Get a specific key from the parsed tags
   *
   * @param key the Key see above
   * @return The value of the key
   */
  public String getTag(String key) {
    return tags.get(key);
  }

  /**
   * Returns the value raw from the
   * tagger.
   *
   * @param key A FieldKey object representing the desired value to seek for.
   * @return The value of the key.
   */
  public String getRaw(FieldKey key) {
    try {
      return checkEmptiness(t.getFirst(key)) ? "Unknown" : t.getFirst(key);
    } catch (UnsupportedOperationException e) {
      return "Unsupported";
    }
  }


  /**
   * @param info
   * @return boolean
   */
  public static boolean checkAudioInfo(AudioInfo info) {
    try {
      AudioFileIO.read(new File(info.getTag(KEY_ABSOLUTE_FILE_PATH)));
      info.getTag(KEY_ABSOLUTE_FILE_PATH);
      info.getTag(KEY_ALBUM);
      info.getTag(KEY_BITRATE);
      info.getTag(KEY_GENRE);
      info.getTag(KEY_MEDIA_ARTIST);
      info.getTag(KEY_SAMPLE_RATE);
      info.getTag(KEY_MEDIA_DURATION);
      info.getTag(KEY_MEDIA_TITLE);
      info.getTag(KEY_SAMPLE_RATE);
    } catch (TagException | ReadOnlyFileException | InvalidAudioFrameException | CannotReadException | IOException e) {
      return false;
    }
    return true;
  }
}
