package com.jackmeng.app.utils;

import java.io.File;

import com.jackmeng.debug.Debugger;

/**
 * A simple class that inits and holds
 * a folder information.
 * 
 * @author Jack Meng
 * @since 3.1
 */
public class FolderInfo {
  private String absPath = ".";

  /**
   * Constructs the folder-info object instance with 
   * the specified path.
   * 
   * Note: this path does not assert that the entered instance is
   * a folder that exists or the file system has access/permission to.
   * @param absolutePath The path to construct the folder-info object with.
   */
  public FolderInfo(String absolutePath) {
    this.absPath = absolutePath;
  }

  /**
   * Returns the absolute path of the folder-info object.
   * @return The absolute path of the folder-info object.
   */
  public String getAbsolutePath() {
    return absPath;
  }

  /**
   * Returns the Files (not sub-folders) in the folder-info object
   * or the folder as the file's names.
   * @return A string array
   */
  public String[] getFilesAsStr() {
    File[] f = new File(absPath).listFiles();
    String[] s = new String[f.length];
    for (int i = 0; i < f.length; i++) {
      s[i] = f[i].getName();
    }
    return s;
  }

  /**
   * Returns all of the files within the folder
   * @return File array 
   */
  public File[] getFiles() {
    return new File(absPath).listFiles();
  }

  /**
   * Get this folder's name
   * @return A String
   */
  public String getName() {
    return new File(absPath).getName();
  }

  /**
   * Returns an array of String in which each String
   * represents a file inside the folder (absolute path).
   * @param rules An array of extensions to search for and compare to.
   * @return An array of String
   */
  public String[] getFilesAsStr(String... rules) {
    File[] f = new File(absPath).listFiles();
    String[] s = new String[f.length];
    for (int i = 0; i < f.length; i++) {
      if (f[i].isFile()) {
        String curr = f[i].getAbsolutePath();
        rulesLoop: for (String r : rules) {
          if (curr.endsWith(r)) {
            s[i] = curr;
            break rulesLoop;
          }
        }
      }
    }
    return s;
  }

  /**
   * @param rules An array of extensions to search for and compare to.
   * @return An array of Files with the specified extension.
   */
  public File[] getFiles(String... rules) {
    File[] f = new File(absPath).listFiles();
    File[] s = new File[f.length];
    for (int i = 0; i < f.length; i++) {
      if (f[i].isFile()) {
        String curr = f[i].getAbsolutePath();
        rulesLoop: for (String r : rules) {
          if (curr.endsWith(r)) {
            s[i] = f[i];
            break rulesLoop;
          }
        }
      }
    }
    return s;
  }

  /**
   * Represents the folder-info object as a string.
   */
  public String toString() {
    return absPath + "[" + getFilesAsStr() + "]";
  }
}
