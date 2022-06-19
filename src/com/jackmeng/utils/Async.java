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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jackmeng.connections.properties.ResourceFolder;
import com.jackmeng.constant.ProgramResourceManager;

/**
 * This class provides a globalized way
 * for certain asynchronous tasks to be provided.
 *
 * An async task is not run as a thread here, but
 * a quick cached thread if needed.
 *
 * @author Jack Meng
 * @author 3.1
 */
public final class Async {
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
}
