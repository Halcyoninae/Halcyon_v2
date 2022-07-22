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

package com.halcyoninae.halcyon.utils;

import javax.swing.*;

import com.halcyoninae.halcyon.connections.properties.ResourceFolder;
import com.halcyoninae.halcyon.constant.ProgramResourceManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class provides a globalized way
 * for certain tasks to be executed
 * without making the code too complex.
 *
 * @author Jack Meng
 * @author 3.1
 */
public final class Wrapper {
  /**
   * Launches a runnable in an async pool.
   *
   * @param runnable The runnable to be launched
   */
  public static void async(Runnable runnable) {
    if (ResourceFolder.pm.get(ProgramResourceManager.KEY_PROGRAM_FORCE_OPTIMIZATION).equals("true")) {
      ExecutorService threadpool = Executors.newFixedThreadPool(1);
      threadpool.submit(runnable);
    } else {
      CompletableFuture.runAsync(runnable);
    }
  }


  /**
   * @param runnable
   */
  public static void asyncSwingUtil(Runnable runnable) {
    SwingUtilities.invokeLater(runnable);
  }

  /**
   * Launches a Runnable in a precatched
   * Exception handler.
   *
   * @param runnable The task to run safely on.
   */
  public static void safeLog(Runnable runnable, boolean isAsync) {
    try {
      if (isAsync)
        async(runnable);
      else
        runnable.run();
    } catch (Exception e) {
      ResourceFolder.dispatchLog(e);
    }
  }


  /**
   * @param run
   */
  public static void threadedRun(Runnable run) {
    ExecutorService es = Executors.newWorkStealingPool();
    es.submit(run);
  }
}
