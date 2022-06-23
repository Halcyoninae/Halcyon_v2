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

package com.jackmeng.halcyon.debug;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.jackmeng.halcyon.connections.properties.ResourceFolder;
import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.Manager;
import com.jackmeng.halcyon.constant.ProgramResourceManager;
import com.jackmeng.halcyon.utils.FolderInfo;
import com.jackmeng.halcyon.utils.Wrapper;

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
          new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
              Thread t = new Thread(r);
              t.setDaemon(true);
              return t;
            }
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
   * This function is run before anything in order to ensure that only one
   * instance of this program is being ran.
   *
   * A signature is generated from the current time as an 8 digit integer using
   * the current unix timestamp.
   *
   * @deprecated This function is not used anymore and will be moved to a native
   *             implementation
   */
  @Deprecated(since = "3.1", forRemoval = true)
  public static void syncPID() {
    Wrapper.async(() -> {
      File f = new File(ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + "/bin/instance.pid");
      if (f.exists()) {
        Debugger.warn(
            "Only one instance of this program is allowed to be run at any given time.\nIf this is a mistake, please delete the instance.pid from the /bin/ folder in the program's halcyon configuration folder.\nIn most cases this should be handled internally.");
        System.exit(1);
      } else {
        try {
          f.createNewFile();
          String s = String.valueOf(System.currentTimeMillis());
          s = s.substring(s.length() - 8);
          FileWriter fw = new FileWriter(f);
          fw.write(s);
          fw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

  public static void forcedSavePlaylists() {
    List<String> list = Global.bp.getStrTabs();
    if (list.size() >= 0) {
      boolean result = ResourceFolder.cacheFile(PLAYLISTS_CACHE_FILE, list.toArray(new String[list.size()]));
      if (!result) {
        Debugger.warn("Failed to save playlists.");
      } else {
        Debugger.good("Saved playlists.");
      }
    }
  }

  public static void forcedSaveLikedTracks() {
    Set<String> list = new HashSet<>();
    Debugger.warn("Now saving liked tracks.");
    for (File f : Global.ll.getFolder().getAsListFiles()) {
      list.add(f.getAbsolutePath());
    }
    Debugger.warn(list);
    if (list.size() >= 0) {
      boolean result = ResourceFolder.cacheFile(LIKED_TRACK_CACHE_FILE, list.toArray(new String[list.size()]));
      if (!result) {
        Debugger.warn("Failed to save liked tracks.");
      } else {
        Debugger.good("Saved liked tracks.");
      }

      Debugger.unsafeLog(list.toArray(new String[list.size()]));
    }
  }

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
    try (Scanner sc = new Scanner(ResourceFolder.getCacheFile(LIKED_TRACK_CACHE_FILE))) {
      while (sc.hasNext()) {
        String str = sc.nextLine();
        if (new File(str).isFile() && new File(str).exists()) {
          list.add(new File(str));
        }
      }
      return list.toArray(new File[list.size()]);
    } catch (IOException e) {
      ResourceFolder.dispatchLog(e);
      Debugger.warn("Failed to retrieve saved liked tracks. " + e.getLocalizedMessage());
    }
    return new File[0];
  }

  public static FolderInfo[] fetchSavedPlayLists() {
    if (!ResourceFolder.getCacheFile(PLAYLISTS_CACHE_FILE).isFile()
        || !ResourceFolder.getCacheFile(PLAYLISTS_CACHE_FILE).exists()) {
      Wrapper.safeLog(() -> {
        try {
          ResourceFolder.getCacheFile(PLAYLISTS_CACHE_FILE).createNewFile();
        } catch (IOException e) {
          // AUTO HANDLED
        }
      }, true);
      return new FolderInfo[0];
    }
    List<FolderInfo> list = new ArrayList<>();
    try (Scanner sc = new Scanner(ResourceFolder.getCacheFile(PLAYLISTS_CACHE_FILE))) {
      while (sc.hasNext()) {
        String s = sc.nextLine();
        list.add(new FolderInfo(s));
      }
      return list.toArray(new FolderInfo[list.size()]);
    } catch (Exception e) {
      ResourceFolder.dispatchLog(e);
      Debugger.warn("Failed to retrieve saved playlists. " + e.getLocalizedMessage());
    }
    return new FolderInfo[0];
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
