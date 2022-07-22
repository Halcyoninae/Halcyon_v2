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

package com.halcyoninae.halcyon.connections.properties;

/**
 * A template class that holds
 * information on a single property.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class Property {
  public String propertyName = "", defaultProperty = "";
  public PropertyValidator pr;

  public Property(String propertyName, String defaultProperty, PropertyValidator pr) {
    this.propertyName = propertyName;
    this.defaultProperty = defaultProperty;
    this.pr = pr;
  }

  public enum PropertyFilterType {
    STARTS_WITH, ENDS_WITH, CONTAINS, EQUALS;
  }

  /**
   * A method that filters a property based on the filter type.
   * @param nameTag The name tag to filter
   * @param type  The filter type
   * @param properties The properties to filter
   * @return An array of the allowed filtered content
   *
   * @see Property.PropertyFilterType
   * @since 3.2
   */
  public static Property[] filterProperties(String nameTag, PropertyFilterType type, Property ... properties) {
    Property[] filteredProperties = new Property[properties.length];
    int i = 0;
    // use separate to reduce overhead for constantly checking during looping | might change later ;P
    if(type.equals(PropertyFilterType.STARTS_WITH)) {
      for(Property property : properties) {
        if(property.propertyName.startsWith(nameTag)) {
          filteredProperties[i] = property;
          i++;
        }
      }
    } else if(type.equals(PropertyFilterType.ENDS_WITH)) {
      for(Property property : properties) {
        if(property.propertyName.endsWith(nameTag)) {
          filteredProperties[i] = property;
          i++;
        }
      }
    } else if(type.equals(PropertyFilterType.CONTAINS)) {
      for(Property property : properties) {
        if(property.propertyName.contains(nameTag)) {
          filteredProperties[i] = property;
          i++;
        }
      }
    } else if(type.equals(PropertyFilterType.EQUALS)) {
      for(Property property : properties) {
        if(property.propertyName.equals(nameTag)) {
          filteredProperties[i] = property;
          i++;
        }
      }
    }
    return filteredProperties;
  }
}
