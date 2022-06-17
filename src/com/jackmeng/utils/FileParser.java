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

package com.jackmeng.utils;

import java.io.*;
import java.util.ArrayList;

/**
 * A simply utility that helps
 * with parsing information regarding files or a folder.
 *
 * @author Jack Meng
 * @since 3.0
 * @see java.io.File
 */
public final class FileParser {

  private FileParser() {}

  /**
   * This method parses files with a certain extension.
   *
   * @param folder The folder to search for, however this parameter must be guaranteed to be a folder and must exist and accessible.
   * @param rules An array of extensions to search for and compare to.
   * @return An array of File objects that will be returned of all of the files that match the rules for the specified file extensions.
   */
  public static File[] parseOnlyAudioFiles(File folder, String[] rules) {
    File[] files = folder.listFiles();
    ArrayList<File> audioFiles = new ArrayList<>();
    for (File f : files) {
      if (f.isFile()) {
        for (String rule : rules) {
          if (f.getAbsolutePath().endsWith("." + rule)) {
            audioFiles.add(f);
          }
        }
      }
    }
    return audioFiles.toArray(new File[audioFiles.size()]);
  }

  /**
   * Returns if a folder contains in any way a file with the specified extension.
   * @param folder The folder to search for, however this parameter must be guaranteed to be a folder and must exist and accessible.
   * @param rules An array of extensions to search for and compare to.
   * @return True if the folder contains a file with the specified extension, false otherwise.
   */
  public static boolean contains(File folder, String[] rules) {
    File[] files = folder.listFiles();
    for(File f : files) {
      if (f.isFile()) {
        for(String rule : rules) {
          if(f.getAbsolutePath().endsWith("." + rule)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * This method parses a folder's name
   * @param f The folder to parse
   * @return The name of the folder
   * 
   * Very useless method
   */
  public static String folderName(String f) {
    return new File(f).getName();
  }

  /**
   * This method returns a string array of file names from the 
   * given varargs of files to parse.
   * @param files A varargs of files to parse
   * @return A string array of file names
   */
  public static String[] parseFileNames(File... files) {
    String[] fileNames = new String[files.length];
    for (int i = 0; i < files.length; i++) {
      if (files[i] != null && files[i].isFile()) {
        fileNames[i] = files[i].getName();
      }
    }
    return fileNames;
  }

  /**
   * Checks if the folder is empty (void of any files)
   * @param folder The folder to check
   * @return True if the folder is empty, false otherwise
   */
  public static boolean isEmptyFolder(File folder) {
    File[] files = folder.listFiles();
    return files.length == 0;
  }
}
