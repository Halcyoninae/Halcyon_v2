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

package com.jackmeng.halcyoninae.halcyon.utils;

/**
 * A class used to do things with numbers
 *
 * @author Jack Meng
 * @since 3.3
 */
public final class Numerical {
    private Numerical() {
    }

    /**
     * Safely divide two numbers
     *
     * @param f1 The numerator
     * @param f2 The denominator
     * @return Safe Quotient
     * @author Jack Meng
     * @since 3.3
     */
    public static double __safe_divide(double f1, double f2) {
        return __classifier_method(f1, f2);
    }

    /**
     * A bound method
     *
     * @param f1 Func1 requires first param
     * @param f2 Func2 requires second param
     * @return A double
     */
    private static double __classifier_method(double f1, double f2) {
        return f2 == (double) 0 ? (double) 0 : f1 / f2;
    }
}
