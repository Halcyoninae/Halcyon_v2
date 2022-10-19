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

package com.jackmeng.halcyoninae.cosmos.components.waveform.utils;

import java.util.concurrent.Callable;

/**
 * @author Jack Meng
 * @since 3.4.1
 */
public final class Utils {

  /**
   * @param min
   * @param max
   * @return int
   */
  public static int rng(int min, int max) {
    return (int) (Math.random() * (max - min)) + min;
  }


  /**
   * @param i
   * @param lim
   * @return int
   */
  public static int limg(int i, int lim) {
    return i > lim ? lim : i;
  }


  /**
   * @param i
   * @param lim
   * @return int
   */
  public static int liml(int i, int lim) {
    return i < lim ? lim : i;
  }


  /**
   * @param arr
   * @param e
   * @return int[]
   */
  public static int[] fillArr(int[] arr, Callable<Integer> e) {
    for (int i = 0; i < arr.length; i++) {
      try {
        arr[i] = e.call();
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }
    return arr;
  }


  /**
   * @param arr
   * @param defCondition
   * @param e
   * @return int[]
   */
  public static int[] appendArr(int[] arr, int defCondition, Callable<Integer> e) {
    for(int i = 0; i < arr.length; i++) {
      if(arr[i] == defCondition) {
        try {
          arr[i] = e.call();
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    }
    return arr;
  }
}
