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

package com.halcyoninae.halcyon.connections.properties.validators;

import com.halcyoninae.halcyon.connections.properties.PropertyValidator;

import java.io.File;

/**
 * @author Jack Meng
 * @since 3.2
 */
public class DirectoryValidator implements PropertyValidator {


  /**
   * @param propertyValue
   * @return boolean
   */
  @Override
  public boolean isValid(String propertyValue) {
    return new File(propertyValue).isDirectory() && new File(propertyValue).exists();
  }

}
