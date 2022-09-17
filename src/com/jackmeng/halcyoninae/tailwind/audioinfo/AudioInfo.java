/*
 * Halcyon ~ exoad
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https://raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
 */

package com.jackmeng.halcyoninae.tailwind.audioinfo;

import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.InfoViewTP;
import com.jackmeng.halcyoninae.halcyon.Halcyon;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.debug.CLIStyles;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
import com.jackmeng.halcyoninae.halcyon.debug.TConstr;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;
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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

/**
 * This class holds information regarding an audio
 * file; specifically, an MP3 file which can be parsed
 * into ID3 tags.
 * <p>
 * Other formats that are supported such as raw PCM,WAV,OGG
 * are not parsable.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class AudioInfo {
    public static final String KEY_ABSOLUTE_FILE_PATH = "a", KEY_FILE_NAME    = "f",
            KEY_MEDIA_DURATION                        = "mD", KEY_MEDIA_TITLE = "mT", KEY_BITRATE = "mB",
            KEY_SAMPLE_RATE                           = "mS", KEY_ALBUM       = "mA", KEY_GENRE   = "mG",
            KEY_MEDIA_ARTIST                          = "mAr", KEY_ARTWORK    = "mArt", KEY_TRACK = "mTr";
    static final BufferedImage DEFAULT                = DeImage
            .imageIconToBI(Global.rd.getFromAsImageIcon(InfoViewTP.INFOVIEW_DISK_NO_FILE_LOADED_ICON));
    private File f;
    private Map<String, String> tags;
    private Tag t;
    private AudioHeader header;

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
        AudioFile af;
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
     * <p>
     * Default init
     */
    public AudioInfo() {
        defInitTags();
    }

    /**
     * @return BufferedImage
     */
    public static BufferedImage getDefaultIcon() {
        return DEFAULT;
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
        } catch (TagException | ReadOnlyFileException | InvalidAudioFrameException | CannotReadException
                | IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the given String is in any way empty.
     *
     * @param s The string to check
     * @return (true | | false) Depending on if the string is empty or not.
     */
    public boolean checkEmptiness(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * @param forceSetMap
     */
    public void forceSet(Map<String, String> forceSetMap) {
        Debugger.warn(
                "Attempting a force set for the current AudioInfo...!!! (Prepare for unforseen consequences (jk)");
        this.tags = forceSetMap;
    }

    /**
     * Initializes the tags map and adds default values to the map
     */
    public void defInitTags() {
        tags = new WeakHashMap<>();
        tags.put(KEY_ABSOLUTE_FILE_PATH, "Nowhere");
        tags.put(KEY_FILE_NAME, "Nothing.mp3");
        tags.put(KEY_MEDIA_DURATION, "0");
        tags.put(KEY_MEDIA_TITLE, "Nothing");
        tags.put(KEY_BITRATE, "0kbps");
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
        tags = new WeakHashMap<>();
        tags.put(KEY_ABSOLUTE_FILE_PATH, f.getAbsolutePath());
        tags.put(KEY_FILE_NAME, f.getName());
        tags.put(KEY_MEDIA_DURATION, header.getTrackLength() + "");
        tags.put(KEY_MEDIA_TITLE,
                checkEmptiness(t.getFirst(FieldKey.TITLE)) ? f.getName() : t.getFirst(FieldKey.TITLE));
        tags.put(KEY_BITRATE, header.getBitRate() + "");
        tags.put(KEY_SAMPLE_RATE, header.getSampleRate() + "");
        tags.put(KEY_ALBUM, checkEmptiness(t.getFirst(FieldKey.ALBUM)) ? "Unknown" : t.getFirst(FieldKey.ALBUM));
        tags.put(KEY_GENRE, checkEmptiness(t.getFirst(FieldKey.GENRE)) ? "Unknown" : t.getFirst(FieldKey.GENRE));
        tags.put(KEY_MEDIA_ARTIST,
                checkEmptiness(t.getFirst(FieldKey.ARTIST)) ? "Unknown" : t.getFirst(FieldKey.ARTIST));
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
                assert mp != null;
                if (mp.getTag().getFirstArtwork() != null) {
                    try {
                        img = (BufferedImage) mp.getTag().getFirstArtwork().getImage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return Objects.requireNonNullElseGet(img, AudioInfo::getDefaultIcon);
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
                assert mp != null;
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
     * tagger. Checking is done to avoid any type of exceptions
     *
     * @param key A FieldKey object representing the desired value to seek for.
     * @return The value of the key.
     */
    public String getRaw(FieldKey key) {
        try {
            return checkEmptiness(t.getFirst(key)) ? "Unknown" : t.getFirst(key);
        } catch (UnsupportedOperationException | NullPointerException e) {
            return "Unsupported";
        }
    }

    /**
     * @return AudioInfoDialog
     */
    public AudioInfoDialog launchAsDialog() {
        Debugger.alert(new TConstr(CLIStyles.MAGENTA_TXT,
                "Launching current AudioInfo"));
        return new AudioInfoDialog(this);
    }

    /**
     * @param ai
     */
    public static void extractArtwork(AudioInfo ai) {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Where to?");
        jfc.setFileHidingEnabled(false);
        jfc.setMultiSelectionEnabled(false);
        int sel = jfc.showSaveDialog(Halcyon.bgt.getFrame());
        if (sel == JFileChooser.APPROVE_OPTION) {
            try {
                OutputStream os = new BufferedOutputStream(new FileOutputStream(jfc.getSelectedFile()));

                ImageIO.write(ai.getArtwork(), "png",
                        os);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
                Debugger.crit("Failed to export and extract artwork with code: " + e.getLocalizedMessage());
            }
            Debugger.good(
                    "Successfully exported and extracted the artwork to: " + jfc.getSelectedFile().getAbsolutePath());
        }
    }
}
