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

package com.jackmeng.halcyon.connections.properties;

import com.jackmeng.halcyon.ProjectManager;
import com.jackmeng.halcyon.constant.ProgramResourceManager;
import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.halcyon.utils.DeImage;
import com.jackmeng.halcyon.utils.FileParser;
import com.jackmeng.halcyon.utils.TextParser;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.w3c.dom.Text;

/**
 * ResourceFolder is a general class that holds information about
 * the external resource folder.
 *
 * The resource folder is named under the name "halcyon-mp4j" it is
 * a constant viewed in {@link project.constants.ProjectManager}
 *
 * For example, it can read and write to the resource folder,
 * and read properties file using
 * {@link project.connection.resource.PropertiesManager}
 *
 * @author Jack Meng
 * @since 2.1
 */
public class ResourceFolder {
  private ResourceFolder() {
  }

  /**
   * Represents the global instance of the PropertiesManager for the
   * user-modifiable "halcyon.properties" file
   *
   * The program has one global instance to reduce overhead.
   */
  public static final PropertiesManager pm = new PropertiesManager(ProgramResourceManager.getProgramDefaultProperties(),
      ProgramResourceManager.getAllowedProperties(),
      ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + ProgramResourceManager.FILE_SLASH
          + ProgramResourceManager.PROGRAM_RESOURCE_FILE_PROPERTIES);

  /**
   * An internal method used to retrieve a random string of letters
   * based on the parameterized length.
   *
   * @param length The length of the random string
   * @return String The random string
   */
  public static String getRandomString(int length) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append((char) (Math.random() * 26 + 'a'));
    }
    return sb.toString();
  }

  /**
   * Based on the folders needed in {@link project.constants.ProjectManager},
   * it checks if these subfolders exists from within the main resource folder.
   *
   * If a folder does not exist, it will be created under the main resource
   * folder.
   *
   * 3.0:
   * Changed to have a parameter being taken
   *
   * @param folderName The folder to be checked
   */
  public static void checkResourceFolder(String folderName) {
    File folder = new File(folderName);
    if (!folder.isDirectory() || !folder.exists()) {
      if (folder.mkdir()) {
        Debugger.log("LOG > Resource folder created.");
      } else {
        Debugger.log("LOG > Resource folder creation failed.");
      }
    }
  }

  /**
   * This is a standard method to write a log file to the resource's log folder.
   *
   * This can be used for anything, however is not a place for things that are
   * cached to be written to.
   *
   * It will only write String based files to the files.
   *
   * @param folderName The name of the folder to write to
   * @param f          The file to write
   */
  public static void writeLog(String folderName, String f) {
    if (!new File(ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + ProgramResourceManager.FILE_SLASH + folderName)
        .isDirectory()) {
      new File(ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + ProgramResourceManager.FILE_SLASH + folderName).mkdir();
    }
    File logFile = new File(
        ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + ProgramResourceManager.FILE_SLASH + folderName
            + ProgramResourceManager.FILE_SLASH
            + System.currentTimeMillis() + "_log.halcylog");
    try {
      logFile.createNewFile();
      FileWriter writer = new FileWriter(logFile);
      writer.write(f);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Writes a buffered-image to the resource folder.
   *
   * Planned:
   * - Make it so that it will write to the "cache" folder
   * of the main resource folder.
   *
   * @param bi The buffered image to write
   * @return String the absolute path of the image
   */
  public static String writeBufferedImageCacheFile(BufferedImage bi, String cacheFolder, String fileName) {
    DeImage.write(bi, ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + "/" + cacheFolder
        + "/" + fileName);
    return new File(ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + "/" + cacheFolder
        + "/" + fileName).getAbsolutePath();

  }

  /**
   * Creates a folder in the standard external resource folder.
   *
   * @param name The name of the folder (can be a subdirectory)
   * @return File The folder that was created
   */
  public static boolean createFolder(String name) {
    File folder = new File(ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + ProgramResourceManager.FILE_SLASH + name);
    if (!folder.exists() || !folder.isDirectory()) {
      return folder.mkdir();
    }
    return false;
  }

  /**
   * Creates a cache file in the standard usr folder that is
   * for this program.
   *
   * @param fileName The file to be created, just file name.
   * @param content  The contents of the file as a varargs of Objects
   * @return (true || false) depending on if the creation process was a
   *         success or not.
   */
  public static boolean cacheFile(String fileName, String[] content) {
    File f = new File(ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + ProgramResourceManager.FILE_SLASH
        + ProgramResourceManager.RESOURCE_SUBFOLDERS[2] + ProgramResourceManager.FILE_SLASH + fileName);
    BufferedWriter bw = null;
    try {
      bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),
          TextParser.getPropertyTextEncodingName().equals("UTF-8") ? StandardCharsets.UTF_8
              : (TextParser.getPropertyTextEncodingName().equals("UTF-16LE") ? StandardCharsets.UTF_16LE
                  : StandardCharsets.UTF_16BE)));
    } catch (Exception e) {
      Debugger.warn(e);
      ResourceFolder.dispatchLog(e);
    }
    try {
      for (String str : content) {
        bw.write(str + "\n");
        bw.flush();
      }
      bw.close();
    } catch (IOException e) {
      Debugger.warn(e);
      ResourceFolder.dispatchLog(e);
    }
    return true;
  }

  /**
   * @param fileName
   * @return File
   */
  public static File getCacheFile(String fileName) {
    return new File(ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + ProgramResourceManager.FILE_SLASH
        + ProgramResourceManager.RESOURCE_SUBFOLDERS[2] + ProgramResourceManager.FILE_SLASH + fileName);
  }

  /**
   * Creates a log file with a varargs of exceptions to be logged.
   * This is done asyncronously, however, the user may be alerted.
   *
   * @param ex The exceptions to be logged
   */
  public static synchronized void dispatchLog(Exception... ex) {
    long start = System.currentTimeMillis();
    ExecutorService dispatch = Executors.newCachedThreadPool();
    Future<Void> writableTask = dispatch.submit(() -> {
      StringBuilder sb = new StringBuilder();
      for (Exception e : ex) {
        sb.append(e.toString());

        Date d = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        writeLog("log",
            "Halcyon/MP4J - LOG EXCEPTION | PLEASE KNOW WHAT YOU ARE DOING\nException caught time: " + df.format(d)
                + "\n"
                + e.getClass() + "\n" + e.toString() + "\n" +
                e.getMessage() + "\nLOCALIZED: " + e.getLocalizedMessage() + "\n"
                + java.util.Arrays.toString(e.getStackTrace()) + "\n"
                + "Submit an issue by making a PR to the file BUGS at " + ProjectManager.PROJECT_GITHUB_PAGE);
      }
      return null;
    });
    while (!writableTask.isDone()) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
    }
    long end = System.currentTimeMillis();
    Debugger.log("LOG > Log dispatch finished in " + (end - start) + "ms.");
  }
}
