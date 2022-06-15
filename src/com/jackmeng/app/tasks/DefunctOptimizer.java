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

package com.jackmeng.app.tasks;

/**
 * This class aims to optimize tasks throughout the JVM 
 * environment for this application during runtime.
 * 
 * It runs in the background and tries to optimize any
 * tasks that may be running in the background.
 * 
 * @author Jack Meng
 * @since 3.1
 */
public class DefunctOptimizer implements Runnable {
  public DefunctOptimizer() {

  }

  @Override
  public void run() {
    new Thread(() -> {
      while(true) {
        Runtime.getRuntime().gc();
        try {
          Thread.sleep(4000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
}
