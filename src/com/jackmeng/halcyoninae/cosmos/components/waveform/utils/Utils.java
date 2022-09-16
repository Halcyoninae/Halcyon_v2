/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly..
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
