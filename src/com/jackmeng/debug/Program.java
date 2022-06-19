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

package com.jackmeng.debug;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.jackmeng.constant.Manager;
import com.jackmeng.utils.Async;

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
   */
  public static void syncPID() {
    Async.async(() -> {
      File f = new File(Manager.RSC_FOLDER_NAME + "/bin/instance.pid");
      if (f.exists()) {
        System.err.println("Only one instance allowed for this program.");
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
