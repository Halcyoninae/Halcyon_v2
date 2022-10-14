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

/**
 * This class handles all mathematics related functions
 * that can be used to manipulate concurrent or nonconcurrent
 * audio data.
 * 
 * @author Jack Meng
 * @since 3.4.1
 */
public final class TailwindMath {
    public static final class Tailwind_FFT {
        public static void factor_4_decomp_1(double[] data, double[] transfer, int fft_sz, int step, int i) {
            int sz = fft_sz << 1;
            int transfer_len = transfer.length >> 1;
            while(step < sz) {
                if(step << 2 == transfer_len) {
                    factor_4_decomp_1(data, transfer, fft_sz, step, i);
                    return;
                }
                int max_1 = 
            }
        }
    }

    private TailwindMath() {}

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

    public static long align_by_block_sz(long sz, int block) {
        return block <= 1 ? sz : sz - (sz % block);
    }

    /**
     * Converts a linear scaled value to a logarithmic
     * value (decibels)
     * 
     * @param val The linear scaled value (float)
     * @return float The logarithmic scaled value (float_decibels)
     */
    public static float linear_to_db(float val) {
        return (float) (Math.log((double) (val == 0.0 ? 0.0001 : val)) / Math.log(10.0d) * 20.0d);
    }

    /**
     * Converts a given time unit (milliseconds)
     * to the byte length based on a provided/known
     * AudioFormat. This function represents method_1
     * of doing this which uses a very lack luster 
     * sampleRate * channelcount * samplesize
     * 
     * @param fmt An AudioFormat object (javax.sound.sampled)
     * @param time An int time unit representing the milliseconds (int_ms)
     * @return long The byte length calculated
     */
    public static long millis_to_byte_len_1(AudioFormat fmt, int time) {
        return (long) (time * (fmt.getSampleRate() * fmt.getChannels() * fmt.getSampledSizeInBits()) / 8000.0F);
    }

    /**
     * Converts a given time unit (milliseconds)
     * to the byte length based on a provided/known
     * AudioFormat. This function represents method_2
     * 
     * @param fmt An AudioFormat object (javax.sound.sampled)
     * @param time An int time unit representing the milliseconds (int_ms)
     * @return long The byte length calculated
     */
    public static long millis_to_byte_len_2(AudioFormat fmt, int time) {
        return align((long) (time * fmt.getFrameRate() / 1000.0F * fmt.getFrameSize()), fmt.getFrameSize());
    }

    /**
     * Converts a logarithmic scaled value (decibels)
     * to its linear scaled counterpart.
     * 
     * @param val Decibels value (float)
     * @return float The linear scaled value (float_linear)
     */
    public static float db_to_linear(float val) {
        return (float) (Math.pow(10.0d, (double) (val / 20.0d)));
    }

    /**
     * Converts a signed byte array to an unsigned 
     * byte array.
     * 
     * @param signed_arr Byte Array Data
     * @param offset Offset (int)
     * @param length Maxim (int)
     */
    public static void unsign_signed(byte[] signed_arr, int offset, int length) {
        for(int i = offset; i < offset + length; i++) {
            signed_arr[i] += 128;
        }
    }

    /**
     * @param bps Bits per Second (int)
     * @return int Normalized data 
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
}
