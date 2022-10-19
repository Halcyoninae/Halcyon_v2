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

package com.jackmeng.halcyoninae.tailwind;

import de.jarnbjo.flac.FlacStream;
import de.jarnbjo.ogg.LogicalOggStream;
import de.jarnbjo.ogg.OnDemandUrlStream;
import de.jarnbjo.vorbis.VorbisStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.net.URL;

/**
 * @author Jack Meng
 * @since 3.1
 */
public final class TailwindHelper {

    /**
     * @param locale
     * @return AudioInputStream
     */
    public static AudioInputStream getAudioIS(URL locale) {
        try {
            AudioInputStream ais;
            FileFormat target = FileFormat.getFormatByName(locale.toExternalForm());
            if (target.equals(FileFormat.MP3)) {
                ais = AudioSystem.getAudioInputStream(locale);
                AudioFormat base = ais.getFormat();
                AudioFormat decode = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        base.getSampleRate(),
                        16,
                        base.getChannels(),
                        base.getChannels() * 2,
                        base.getSampleRate(),
                        false);
                ais = AudioSystem.getAudioInputStream(decode, ais);
                return ais;
            } else if (target.equals(FileFormat.WAV) || target.equals(FileFormat.AIFF) || target.equals(FileFormat.AIFC) || target.equals(FileFormat.AU)) {
                ais = AudioSystem.getAudioInputStream(locale);
                return ais;
            } else if (target.equals(FileFormat.OGG)) {
                LogicalOggStream stream = (LogicalOggStream) new OnDemandUrlStream(locale).getLogicalStreams()
                        .iterator()
                        .next();

                if (stream.getFormat().equals(LogicalOggStream.FORMAT_VORBIS)) {
                    VorbisIn v = new VorbisIn(new VorbisStream(stream));
                    ais = new AudioInputStream(v, v.getFormat(), -1L);
                    return ais;
                }
            } else if (target.equals(FileFormat.FLAC)) {
                LogicalOggStream stream = (LogicalOggStream) new OnDemandUrlStream(locale).getLogicalStreams()
                        .iterator()
                        .next();
                if (stream.getFormat().equals(LogicalOggStream.FORMAT_FLAC)) {
                    FlacIn v = new FlacIn(new FlacStream(stream));
                    ais = new AudioInputStream(v, v.getFormat(), -1L);
                    return ais;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This writes an AudioInfo's properties to a file.
     * <p>
     * This process writes to a folder and writes the following:
     * A file with all the attributes
     * A file with the image artwork
     *
     * @param parentDir The folder to write to.
     * @param info      The AudioInfo object
     * @return The File as a directory that has been written to.
     * @since 3.3
     */
    public static File writeAudioInfoConfig(File parentDir, AudioInfo info) {
        return null;
    }
}
