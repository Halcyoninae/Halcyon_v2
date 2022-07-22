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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * This class is localized meaning
 * that one should not use it for anything besides
 * this program.
 *
 * This program provides a simple Replace On Demand
 * properties reader, instead of the default {@link java.util.Properties}
 * class.
 *
 * A Replace On Demand means that it will not let the
 * the receiver decide what to do with the properties, but will
 * go by rules. If a value is deemed unacceptable, it will be
 * be replaced by the default value, and the original value in
 * the file will be overwritten. Another thing to note is that,
 * on demand means only checking the current value it is being called upon
 * {@link #get(String)}.
 * This class does not do a pre-check of all of the properties, unless the
 * properties file does not
 * exist or is empty, then everything will be replaced
 * {@link #createWithDefaultVals()}.
 *
 * @author Jack Meng
 * @since 2.1
 */
public final class PropertiesManager {
  private Map<String, String> map;
  private final Map<String, PropertyValidator> allowedProperties;
  private final Properties util;
  private FileReader fr;
  private final String location;

  /**
   * Creates a new PropertiesManager instance with the defined rules and
   * allowed-properties for the
   * given properties file.
   *
   * @param defaultProperties Contains a key and a value with the value being the
   *                          fallback value for
   *                          that key if the key's value in the properties is not
   *                          allowed. This is not an optional parameter to set as
   *                          null or anything that makes it not have a value.
   * @param allowedProperties Contains a key and an array of allowed properties as
   *                          rules, if the value from file's key does not match
   *                          any of the given rules, the PropertiesManager will
   *                          return the default property and alter the file. This
   *                          is an optional parameter, which can be that the
   *                          array can be empty (NOT NULL).
   * @param location          The location of the properties file
   */
  public PropertiesManager(Map<String, String> defaultProperties, Map<String, PropertyValidator> allowedProperties,
      String location) {
    this.map = defaultProperties;
    this.allowedProperties = allowedProperties;
    this.location = location;
    util = new Properties();
  }

  /// BEGIN PRIVATE METHODS

  /**
   * Checks if the file has all the necessary properties' keys.
   *
   * As previously stated, this is an Replace On Demand method, meaning that
   * this method does not care about the value of the properties, it only cares
   * if the key exists.
   *
   * If a key does not exist, it will be created with the default value.
   */
  public void checkAllPropertiesExistence() {
    try {
      if (!new File(location).exists() || !new File(location).isFile()) {
        new File(location).createNewFile();
        createWithDefaultVals();
      }
      util.load(new FileReader(location));
    } catch (IOException e) {
      e.printStackTrace();
    }
    for (String key : allowedProperties.keySet()) {
      if (!util.containsKey(key)) {
        util.setProperty(key, map.get(key));
      }
    }
    save();
  }

  /**
   * Creates an empty file with the location given in the constructor.
   */
  private void wipeContents() {
    try {
      FileWriter writer = new FileWriter(location);
      writer.write("");
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Writes to the file with the default values of all the properties.
   *
   * It will also first wipe the contents of the file in order to prevent
   * overwrite.
   */
  private void createWithDefaultVals() {
    wipeContents();
    try {
      fr = new FileReader(location);
      util.load(fr);
    } catch (IOException e) {
      e.printStackTrace();
    }
    for (Map.Entry<String, String> entry : map.entrySet()) {
      util.setProperty(entry.getKey(), entry.getValue());
    }
    save();
  }

  /// END PRIVATE METHODS
  /// BEGIN PUBLIC METHODS

  /**
   * Returns the Map of default properties provided in the constructor.
   *
   * @return The Map of default properties provided in the constructor.
   */
  public Map<String, String> getDefaultProperties() {
    return map;
  }

  /**
   * UNSAFE: Resets the default properties to something else.
   *
   * It is unadvised to use this method, as it does not do a
   * pre-check of the properties of the file for all the properties after,
   * forcing the receiver to check for all the properties.
   *
   * @param map The new default properties.
   */
  public void setDefaultProperties(Map<String, String> map) {
    this.map = map;
    createWithDefaultVals();
  }

  /**
   * Returns the current referenced properties file.
   *
   * @return The current referenced properties file.
   */
  public String getLocation() {
    return location;
  }

  /**
   * Returns (true || false) based ont he existence of a key in the properties
   * file.
   *
   * @param key A key to check for
   * @return (true || false) based on the existence of a key in the properties
   *         file.
   */
  public boolean contains(String key) {
    return util.containsKey(key);
  }

  /**
   * Consults if the given key and value are allowed to be paired or
   * is allowed in general based on the rules provided in the constructor.
   *
   * If the allowed-properties' string array is empty, then it will always return
   * true.
   *
   * @param key   The key to check for
   * @param value The value to check for
   * @return (true || false) based the allowance of the value upon the key
   */
  public boolean allowed(String key, String value) {
    if (allowedProperties.containsKey(key)) {
      if (allowedProperties.get(key) == null) {
        return true;
      }
      return allowedProperties.get(key).isValid(value);
    }
    return false;
  }

  /**
   * Returns the value of the key in the properties file.
   *
   * @param key The key to get the value of
   * @return The value of the key in the properties file.
   */
  public String get(String key) {
    if (!new File(location).exists() || !new File(location).isFile()) {
      try {
        new File(location).createNewFile();
      } catch (IOException ignored) {
        // IGNORED
      }
      createWithDefaultVals();
    }
    if (fr == null) {
      try {
        if (!new File(location).exists() || !new File(location).isFile()) {
          new File(location).createNewFile();
          createWithDefaultVals();
        }
        fr = new FileReader(location);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    try {
      fr = new FileReader(location);
      util.load(fr);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (util.getProperty(key) == null) {
      util.setProperty(key, map.get(key));
      save();
    }
    return util.getProperty(key) == null || !allowed(key, util.getProperty(key)) ? map.get(key) : util.getProperty(key);
  }

  /**
   * Sets the value of the key in the properties file.
   *
   * @param key      The key to set the value of
   * @param value    The value to set the key to
   * @param comments The comments to add to the file
   */
  public void set(String key, String value, String comments) {
    try {
      util.load(fr);
    } catch (IOException e) {
      e.printStackTrace();
    }
    util.setProperty(key, value);
    try {
      FileWriter writer = new FileWriter(location);
      util.store(writer, comments);
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Saves the properties file with comments.
   *
   * @param comments The comments to add to the top of the file.
   */
  public void save(String comments) {
    try {
      util.store(new FileWriter(location), comments);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Save with no comments parameter
   */
  public void save() {
    save("");
  }

  /**
   * Opens the properties file for editing and consulting.
   *
   * @return (true || false) based on if the file was able to be opened.
   */
  public boolean open() {
    if (!new File(location).exists()) {
      File f = new File(location);
      try {
        f.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
      createWithDefaultVals();
    } else {
      try {
        fr = new FileReader(location);
        util.load(fr);
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  /// END PUBLIC METHODS
}
