package com.jackmeng.halcyon.connections.properties;

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
