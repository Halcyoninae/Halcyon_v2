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

package com.halcyoninae.halcyon.global;

/**
 * A rarely used data structure class that holds
 * 3 generic objects. It extends a pair class.
 *
 * @author Jack Meng
 * @see com.halcyoninae.halcyon.global.Pair
 * @since 3.0
 */
public class Trio<T, E, M> extends Pair<T, E> {
    public M third;

    /**
     * Constructs a trio with the given first and second elements and third elements
     *
     * @param first  The first element which will be found in a Pair data structure
     * @param second The second element which will also be supered to the extended Pair class
     * @param third  The third element which is held by this child class
     */
    public Trio(T first, E second, M third) {
        super(first, second);
        this.third = third;
    }
}
