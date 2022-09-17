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
