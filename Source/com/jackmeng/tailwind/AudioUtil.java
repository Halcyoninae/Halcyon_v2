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

package com.jackmeng.tailwind;

import com.jackmeng.tailwind.simple.FileFormat;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.net.URL;

public final class AudioUtil {
  public static AudioInputStream getAudioIS(URL locale) {
    try {
      AudioInputStream ais;
      FileFormat target = FileFormat.getFormatByName(locale.toExternalForm());
      if (target.equals(FileFormat.MP3)) {
        ais = AudioSystem.getAudioInputStream(locale);
        AudioFormat base = ais.getFormat();
        AudioFormat decode = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, base.getSampleRate(), 16,
            base.getChannels(), base.getChannels() * 2, base.getSampleRate(), false);
        ais = AudioSystem.getAudioInputStream(decode, ais);
        return ais;
      } else if (target.equals(FileFormat.WAV)) {
        ais = AudioSystem.getAudioInputStream(locale);
        return ais;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
