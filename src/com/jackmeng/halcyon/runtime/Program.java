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

package com.jackmeng.halcyon.runtime;

import com.jackmeng.halcyon.connections.properties.ResourceFolder;
import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.ProgramResourceManager;
import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.halcyon.filesystem.PhysicalFolder;
import com.jackmeng.halcyon.utils.TextParser;
import com.jackmeng.halcyon.utils.Wrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Provides a concurrent logging system for the program
 * instead of using Debugger, which is meant for debugging during
 * initial development.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class Program {
  private static ExecutorService executorService;

  public static String PLAYLISTS_CACHE_FILE = "playlists.halcyon";
  public static String LIKED_TRACK_CACHE_FILE = "likedtracks.halcyon";

  /**
   * Different from Debugger's log method, this method is meant for
   * printing out messages to the console, however can only
   * print out a string.
   *
   * Asynchronous println t the console.
   *
   * @param e A string
   */
  private static void println(String e) {
    if (executorService == null) {
      executorService = Executors.newCachedThreadPool(
          r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
          });
      executorService.submit(
          new Runnable() {
            @Override
            public synchronized void run() {
              while (true) {
                try {
                  wait();
                  System.err.println(e);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            }
          });
    }
  }

  /**
   * Runs a dispatch on the current saved playlists.
   *
   * This method extracts this information from the FileList
   * object instances.
   */
  public static void forcedSavePlaylists() {
    List<String> list = Global.bp.getStrTabs();
    boolean result = ResourceFolder.cacheFile(PLAYLISTS_CACHE_FILE, list.toArray(new String[0]));
    if (!result) {
      Debugger.warn("Failed to save playlists.");
    } else {
      Debugger.good("Saved playlists.");
    }
  }

  /**
   * Writes a dump file to the bin folder.
   *
   * This is not a serialization byte stream!!!
   *
   * This method is typically used for debugging and coverage
   * telemetry stuffs (idk :/).
   *
   * @param content The Objects to dump or content.
   */
  public static void createDump(Object... content) {
    StringBuilder sb = new StringBuilder();
    for (Object o : content) {
      sb.append(o.toString());
    }
    String s = sb.toString();
    File f = new File(
        ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + "/bin/dump_" + System.currentTimeMillis() + "_.halcyon");
    try {
      FileWriter fw = new FileWriter(f);
      fw.write(s);
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Similar to {@link #forcedSavePlaylists()}; instead this calls
   * for a LikedList extraction and saves to the desired file.
   */
  public static void forcedSaveLikedTracks() {
    Set<String> list = new HashSet<>();
    Debugger.warn("Now saving liked tracks.");
    for (File f : Global.ll.getFolder().getAsListFiles()) {
      list.add(f.getAbsolutePath());
    }
    Debugger.warn(list);
    boolean result = ResourceFolder.cacheFile(LIKED_TRACK_CACHE_FILE, list.toArray(new String[0]));
    if (!result) {
      Debugger.warn("Failed to save liked tracks.");
    } else {
      Debugger.good("Saved liked tracks.");
    }
    Debugger.unsafeLog(list.toArray(new String[0]));
  }

  /**
   * @return File[] Returns all of the liked tracks.
   */
  public static File[] fetchLikedTracks() {
    if (!ResourceFolder.getCacheFile(LIKED_TRACK_CACHE_FILE).isFile()
        || !ResourceFolder.getCacheFile(LIKED_TRACK_CACHE_FILE).exists()) {
      Wrapper.safeLog(() -> {
        try {
          ResourceFolder.getCacheFile(LIKED_TRACK_CACHE_FILE).createNewFile();
        } catch (IOException e) {
          // AUTO HANDLED
        }
      }, true);
      return new File[0];
    }
    Set<File> list = new HashSet<>();
    try (FileInputStream fis = new FileInputStream(ResourceFolder.getCacheFile(LIKED_TRACK_CACHE_FILE))) {
      BufferedReader br = new BufferedReader(new InputStreamReader(fis, TextParser.getCharset()));
      while (br.ready()) {
        String str = br.readLine();
        Debugger.warn(str);
        if (new File(str).isFile() && new File(str).exists()) {
          list.add(new File(str));
        }
      }
      return list.toArray(new java.io.File[0]);
    } catch (IOException e) {
      ResourceFolder.dispatchLog(e);
      Debugger.warn("Failed to retrieve saved liked tracks. " + e.getLocalizedMessage());
    }
    return new File[0];
  }

  /**
   * @return FolderInfo[] Returns all concurrently stored playlists in the running
   *         instance.
   */
  public static PhysicalFolder[] fetchSavedPlayLists() {
    if (!ResourceFolder.getCacheFile(PLAYLISTS_CACHE_FILE).isFile()
        || !ResourceFolder.getCacheFile(PLAYLISTS_CACHE_FILE).exists()) {
      Wrapper.safeLog(() -> {
        try {
          ResourceFolder.getCacheFile(PLAYLISTS_CACHE_FILE).createNewFile();
        } catch (IOException e) {
          // AUTO HANDLED
        }
      }, true);
      return new PhysicalFolder[0];
    }
    List<PhysicalFolder> list = new ArrayList<>();
    try (FileInputStream fis = new FileInputStream(ResourceFolder.getCacheFile(PLAYLISTS_CACHE_FILE))) {
      BufferedReader br = new BufferedReader(new InputStreamReader(fis, TextParser.getCharset()));
      while (br.ready()) {
        String str = br.readLine();
        if (new File(str).exists() && new File(str).isDirectory()) {
          list.add(new PhysicalFolder(str));
        }
      }
      return list.toArray(new com.jackmeng.halcyon.filesystem.PhysicalFolder[0]);
    } catch (Exception e) {
      ResourceFolder.dispatchLog(e);
      Debugger.warn("Failed to retrieve saved playlists. " + e.getLocalizedMessage());
    }
    return new PhysicalFolder[0];
  }

  /**
   * This main function runs the daemon for system logging
   *
   * This is provided under an executor service that runs
   * async to the main thread in order to log everything made by the
   * the program.
   *
   * This async mechanism is called natively.
   *
   * @param args Initial items to log
   */
  public static void main(String... args) {
    for (String arg : args) {
      println(arg);
    }
  }
}
