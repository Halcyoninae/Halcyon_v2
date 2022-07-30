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

package com.halcyoninae.cosmos.components.minimizeplayer;

import java.awt.image.*;

import com.halcyoninae.cloudspin.lib.blurhash.BlurHash;

/**
 * @author Jack Meng
 * @since 3.2
 */
public final class MiniDeImage {
  private MiniDeImage() {}


  /**
   * @param r
   * @param x
   * @param y
   * @return BufferedImage
   */
  public static BufferedImage blurHash(BufferedImage r, int x, int y) {
    return new BlurHash().blur(r, x, y, (Object[]) null); // cast a null???? cmon bruh
  }
}
