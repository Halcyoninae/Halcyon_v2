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

package com.jackmeng.cosmos;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.jackmeng.cosmos.tasks.DefunctOptimizer;
import com.jackmeng.cosmos.tasks.PingFileView;
import com.jackmeng.halcyon.connections.properties.ResourceFolder;
import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.ProgramResourceManager;

import javax.swing.*;

public class ThreadedScheduler {
  public ThreadedScheduler() {
  }

  /**
   * Anything that needs initialization should be done here.
   * <br>
   * This init process is done before anything is displayed or run in the program
   * itself. This is done mostly to configure the current host's system to be
   * fitting the program.
   * <br>
   * Along with this, this process also sets up any Threads that must be running
   * throughout the program either for: user comfort or program functionality.
   * <br>
   * On demand tasks such as those that need to be run on a separate thread must
   * be init by themselves not here.
   */
  static {
    System.setProperty("file.encoding", "UTF-8");
    System.setProperty("sun.jnu.encoding", "UTF-8");
    System.setProperty("flatlaf.useJetBrainsCustomDecorations", "true");
    System.setOut(null);
    System.setProperty("sun.java2d.uiScale", ResourceFolder.pm.get(ProgramResourceManager.KEY_PROGRAM_HIDPI_VALUE));
    UIManager.put("FileChooser.readOnly", true);

    try {
      UIManager.setLookAndFeel(FlatOneDarkIJTheme.class.getName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    // PROGRAMMABLE THREADS
    Runnable[] tasks = new Runnable[] {
        new PingFileView(Global.bp),
        new DefunctOptimizer(),
    };

    for (Runnable t : tasks) {
      new Thread(t).start();
    }

  }
}
