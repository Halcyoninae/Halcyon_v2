/*
 * Halcyon ~ exoad
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https://raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
 */

package com.jackmeng.halcyoninae.halcyon.utils;

/**
 * A template class that holds
 * information on a single property.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class Property {
    public String propertyName, defaultProperty;
    public PropertyValidator pr;
    public String[] commonProperties;

    public Property(String propertyName, String defaultProperty, PropertyValidator pr, String... commonProperties) {
        this.propertyName     = propertyName;
        this.defaultProperty  = defaultProperty;
        this.pr               = pr;
        this.commonProperties = commonProperties;
    }

    /**
     * A method that filters a property based on the filter type.
     *
     * @param nameTag    The name tag to filter
     * @param type       The filter type
     * @param properties The properties to filter
     * @return An array of the allowed filtered content
     * @see Property.PropertyFilterType
     * @since 3.2
     */
    public static Property[] filterProperties(String nameTag, PropertyFilterType type, Property... properties) {
        Property[] filteredProperties = new Property[properties.length];
        int i = 0;
        // use separate to reduce overhead for constantly checking during looping | might change later ;P
        if (type.equals(PropertyFilterType.STARTS_WITH)) {
            for (Property property : properties) {
                if (property.propertyName.startsWith(nameTag)) {
                    filteredProperties[i] = property;
                    i++;
                }
            }
        } else if (type.equals(PropertyFilterType.ENDS_WITH)) {
            for (Property property : properties) {
                if (property.propertyName.endsWith(nameTag)) {
                    filteredProperties[i] = property;
                    i++;
                }
            }
        } else if (type.equals(PropertyFilterType.CONTAINS)) {
            for (Property property : properties) {
                if (property.propertyName.contains(nameTag)) {
                    filteredProperties[i] = property;
                    i++;
                }
            }
        } else if (type.equals(PropertyFilterType.EQUALS)) {
            for (Property property : properties) {
                if (property.propertyName.equals(nameTag)) {
                    filteredProperties[i] = property;
                    i++;
                }
            }
        }
        return filteredProperties;
    }

    public enum PropertyFilterType {
        STARTS_WITH, ENDS_WITH, CONTAINS, EQUALS
    }
}
