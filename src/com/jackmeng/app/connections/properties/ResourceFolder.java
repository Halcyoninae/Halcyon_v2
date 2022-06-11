package com.jackmeng.app.connections.properties;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.jackmeng.ProjectManager;
import com.jackmeng.app.constant.ProgramResourceManager;

import java.awt.image.BufferedImage;
import com.jackmeng.app.utils.DeImage;
import com.jackmeng.debug.Debugger;

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
  private static String getRandomString(int length) {
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
   * @return String The name of the file that was written
   */
  public static String writeBufferedImageCacheFile(BufferedImage bi) {
    DeImage.write(bi, ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + ProgramResourceManager.FILE_SLASH
        + getRandomString(10) + ".png");
    return ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + ProgramResourceManager.FILE_SLASH + getRandomString(10)
        + ".png";
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
            "Halcyon/MP4J - LOG EXCEPTION | PLEASE KNOW WHAT YOU ARE DOING\nException caught time: " + df.format(d) + "\n"
                + e.getClass() + "\n" + e.toString() + "\n" +
                e.getMessage() + "\nLOCALIZED: " + e.getLocalizedMessage() + "\n" + e.getStackTrace() + "\n"
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
