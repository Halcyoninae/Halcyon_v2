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

package com.jackmeng.cloudspin;

import java.util.Arrays;

/**
 * A low level implementation of the BlurHash
 * algorithm from here: https://blurha.sh/
 *
 * Original ported from an early version of this
 * program.
 *
 * @author Jack Meng
 * @since 1.5
 */
public final class BlurHash {
    static double [] __ll = new double [256];
    static
    {
        for (int i = 0; i < __ll.length; i++)
        {
            double _m = i / 255.0d;
            __ll = _m <= 0.04045 ? (_m / 12.92) : (Math.pow((_m + 0.055) / 1.055, 2.4));
        }
    }

    public static _is_linear(int val)
    {
        return val < 0 ? __ll[0] : (val >= 256 ? __ll[255] : __ll[val]);
    }

    public static _as_linear(int val)
    {
        int _l = Arrays.binarySearch(_ll, val);
        if(_l < 0)
        {
            _l = ~_l;
        }
        return _l < 0 ? 0 : (_l >= 256 ? 255 : _l);
    }

    private BlurHash() {}

    public static String enc(int [] pixels, int _width, int _height, int _x, int _y)
    {
        double [][] _en_masse = new double [_x * _y][3];
        for (int i = 0; i < _y; i++)
        {
            for (int j = 0; j < _x; j++)
            {
                double normale = (i == 0 && j == 0) ? 1 : 2, _r = 0.0d, _g = 0.0d, _b = 0.0d;
                for (int x = 0; x < _width; x++)
                {
                    for (int y = 0; y < _height; y++)
                    {
                        double _m = normale * Math.cos((Math.PI * j * x) / _width) * Math.cos((Math.PI * i * y) / _height);
                        int _pix = pixels[y * _width + x];
                        r += _m *
                    }
                }
            }
        }
    }
}
