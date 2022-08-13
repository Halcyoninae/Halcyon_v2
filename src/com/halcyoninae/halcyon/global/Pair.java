/*
 *  Copyright: (C) 2022 MP4J Jack Meng
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

package com.halcyoninae.halcyon.global;

/**
 * This class dedicates a Map style data structure,
 * however it is not a list-like data structure.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class Pair<T, E> {
    public T first;
    public E second;

    /**
     * Constructs the Pair object
     *
     * @param first  First element of T type (generic)
     * @param second Second element of E type (generic)
     */
    public Pair(T first, E second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Retrieves the first element
     *
     * @return The first element returned as the original T type (generic)
     */
    public T getFirst() {
        return first;
    }

    /**
     * Retrieves the second element
     *
     * @return The second element returned as the original E type (generic)
     */
    public E getSecond() {
        return second;
    }
}
