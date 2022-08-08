/*
 *  Copyright: (C) 2022 name of Jack Meng
 * CloudSpin a graphics library for image manipulation is licensed under the following
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

package com.halcyoninae.cloudspin.lib;

import java.awt.image.BufferedImage;

/**
 * @author Jack Meng
 * @since 3.2
 */
public interface Blur {

    /**
     * This method will be implemented by all Blurring
     * classes that will help to blur an image.
     *
     * @param image       The image to blur.
     * @param _x          The x-coordinate of the image.
     * @param _y          The y-coordinate of the image.
     * @param otherParams Any other parameters that may be needed by that particular
     *                    method
     * @return A BufferedImage created by the blurring method
     */
    BufferedImage blur(BufferedImage image, int _x, int _y, Object... otherParams);
}
