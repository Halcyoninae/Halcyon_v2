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
import com.jackmeng.halcyon.constant.ColorManager;
import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.Manager;
import com.jackmeng.halcyon.constant.ProgramResourceManager;
import com.jackmeng.halcyon.debug.Debugger;

import java.awt.Color;
import java.util.logging.LogManager;

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
    System.setProperty("flatlaf.useWindowDecorations", "false");
    System.setProperty("flatlaf.uiScale.enabled", "false");
    System.setProperty("flatlaf.useJetBrainsCustomDecorations", "true");
    UIManager.put("ScrollBar.thumbArc", 999);
    UIManager.put("TabbedPane.showTabSeparators", true);
    UIManager.put("ScrollBar.showButtons", false);
    UIManager.put("TitlePane.closeHoverBackground", ColorManager.MAIN_FG_THEME);
    UIManager.put("TitlePane.closePressedBackground", ColorManager.MAIN_FG_THEME);
    UIManager.put("TitlePane.buttonHoverBackground", ColorManager.MAIN_FG_THEME);
    UIManager.put("TitlePane.buttonPressedBackground", ColorManager.MAIN_FG_THEME);
    UIManager.put("TitlePane.closeHoverForeground", ColorManager.MAIN_BG_THEME);
    UIManager.put("TitlePane.closePressedForeground", ColorManager.MAIN_BG_THEME);
    UIManager.put("TitlePane.buttonHoverForeground", ColorManager.MAIN_BG_THEME);
    UIManager.put("TitlePane.buttonPressedForeground", ColorManager.MAIN_BG_THEME);
    UIManager.put("TitlePane.centerTitle", true);
    UIManager.put("TitlePane.buttonSize", new java.awt.Dimension(25,20));
    UIManager.put("TitlePane.unifiedBackground", true);
    UIManager.put("SplitPaneDivider.gripDotCount", 0);
    System.setOut(null);
    UIManager.put("FileChooser.readOnly", true);
    LogManager.getLogManager().reset();
    try {
      UIManager.setLookAndFeel(FlatOneDarkIJTheme.class.getName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    // fix blurriness on high-DPI screens
    System.setProperty("sun.java2d.opengl", "true");
    System.setProperty("sun.java2d.uiScale", "0.9");

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
