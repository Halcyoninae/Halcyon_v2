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

import javax.sound.sampled.AudioFormat;
import java.io.File;

public final class TailwindTranscoder implements Transcoder {

  /**
   * @param inFormat
   * @param outFormat
   * @param inLocale
   * @param outLocale
   */
  @Override
  public void transcode(int inFormat, int outFormat, File inLocale, File outLocale) {

  }


  /**
   * @param arr
   * @param shift_n
   * @return int[]
   */
  public static int[] byteify(byte[] arr, int shift_n) {
    int[] temp = new int[arr.length / 2];
    for (int i = 0; i < arr.length / 2; i++) {
      temp[i] = (arr[i * 2] & 0xFF) | (arr[i * 2 + 1] << (shift_n == 0 ? 8 : shift_n));
    }
    return temp;
  }


  /**
   * @param format
   * @param time
   * @return int
   */
  public static int msToByte(AudioFormat format, int time) {
    return (int) (time * (format.getSampleRate() * format.getChannels() * format.getSampleSizeInBits()) / 8000f);
  }
}
