package com.jackmeng.app.constant;

import java.util.Map;
import java.util.HashMap;

public class ProgramResourceManager {
  private ProgramResourceManager() {}
  public static final String FILE_SLASH = "/";
  public static final String PROGRAM_RESOURCE_FOLDER = "halcyon";
  public static final String PROGRAM_RESOURCE_FILE_PROPERTIES = "halcyon.properties";
  public static final String KEY_USER_DEFAULT_FOLDER = "user.default.folder";
  public static final String VALUE_USER_DEFAULT_FOLDER = ".";
  public static final String[] RESOURCE_SUBFOLDERS = {
    "log",
    "bin"
  };

  public static Map<String, String> getProgramDefaultProperties() {
    Map<String, String> properties = new HashMap<>();
    properties.put(KEY_USER_DEFAULT_FOLDER, VALUE_USER_DEFAULT_FOLDER);

    return properties;
  }

  public static Map<String, String[]> getAllowedProperties() {
    Map<String, String[]> properties = new HashMap<>();
    properties.put(KEY_USER_DEFAULT_FOLDER, new String[] {});

    return properties;
  }
}
