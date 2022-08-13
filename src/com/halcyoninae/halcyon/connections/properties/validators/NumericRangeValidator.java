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

package com.halcyoninae.halcyon.connections.properties.validators;

import com.halcyoninae.halcyon.connections.properties.PropertyValidator;

/**
 * @author Jack Meng
 * @since 3.2
 */
public class NumericRangeValidator implements PropertyValidator {
    private final double step;
    private final double min;
    private final double max;

    public NumericRangeValidator(double min, double max, double step) {
        this.min = min;
        this.max = max;
        this.step = step;
    }


    /**
     * @param propertyValue
     * @return boolean
     */
    @Override
    public boolean isValid(String propertyValue) {
        try {
            double value = Double.parseDouble(propertyValue);
            return value >= min && value <= max && value % step == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
