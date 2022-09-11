/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly.
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

package com.jackmeng.halcyoninae.tailwind;

import javax.sound.sampled.AudioFormat;
import java.io.File;

/**
 * @author Jack Meng
 * @since 3.2
 */
public final class TailwindTranscoder implements Transcoder {

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
     * @param bps
     * @return int
     */
    public static int normalize(int bps) {
        return bps + 7 >> 3;
    }

    /**
     * @param buffer
     * @param transfer
     * @param cum
     * @param b_
     * @param format
     * @return float[]
     */
    public static float[] f_unpack(byte[] buffer, long[] transfer, float[] cum, int b_, AudioFormat format) {
        if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED && format.getEncoding() != AudioFormat.Encoding.PCM_UNSIGNED) {
            return cum;
        }
        int bps = format.getSampleSizeInBits();
        int nb = normalize(bps);

        if (format.isBigEndian()) {
            for (int i = 0, k = 0, j; i < b_; i += nb, k++) {
                transfer[k] = 0L;
                int minima = i + nb - 1;
                for (j = 0; j < nb; j++) {
                    transfer[k] |= (buffer[minima - j] & 0xFFL) << (8 * j);
                }
            }
        } else {
            for (int i = 0, k = 0, j; i < b_; i += nb, k++) {
                transfer[k] = 0L;
                for (j = 0; j < nb; j++) {
                    transfer[k] |= (buffer[i + j] & 0xFFL) << (8 * j);
                }
            }
        }
        long scale = (long) Math.pow(2L, bps - 1d);
        if (format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED) {
            long shift = 64L - bps;
            for (int i = 0; i < transfer.length; i++)
                transfer[i] = ((transfer[i] << shift) >> shift);
        } else {
            for (int i = 0; i < transfer.length; i++) {
                transfer[i] -= scale;
            }
        }
        for (int i = 0; i < transfer.length; i++) {
            cum[i] = ((float) transfer[i]) / scale;
        }
        return cum;
    }

    /**
     * @param cum
     * @param s_
     * @param format
     * @return
     */
    public static float[] window_func(float[] cum, int s_, AudioFormat format) {
        int chnls = format.getChannels();
        int len = s_ / chnls;

        for (int i = 0, k, j; i < chnls; i++) {
            for (j = i, k = 0; j < s_; j += chnls) {
                cum[j] *= Math.sin(Math.PI * (k++) / (len - 1));
            }
        }
        return cum;
    }

    /**
     * @param format
     * @param time
     * @return int
     */
    public static int msToByte(AudioFormat format, int time) {
        return (int) (time * (format.getSampleRate() * format.getChannels() * format.getSampleSizeInBits()) / 8000f);
    }

    /**
     * @param inFormat
     * @param outFormat
     * @param inLocale
     * @param outLocale
     */
    @Override
    public void transcode(int inFormat, int outFormat, File inLocale, File outLocale) {

    }
}
