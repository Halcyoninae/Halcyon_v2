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

package com.jackmeng.halcyon;

import com.jackmeng.halcyon.app.ThreadedScheduler;
import com.jackmeng.halcyon.app.components.Tailwind;
import com.jackmeng.halcyon.app.components.bbloc.BBlocButton;
import com.jackmeng.halcyon.app.components.bbloc.BBlocView;
import com.jackmeng.halcyon.app.components.bbloc.buttons.AddFolder;
import com.jackmeng.halcyon.app.components.bbloc.buttons.GenericWebsiteLinker;
import com.jackmeng.halcyon.app.components.bbloc.buttons.LegalNoticeButton;
import com.jackmeng.halcyon.app.components.bbloc.buttons.RefreshFileView;
import com.jackmeng.halcyon.app.components.bbloc.buttons.Settings;
import com.jackmeng.halcyon.app.components.dialog.ErrorWindow;
import com.jackmeng.halcyon.app.components.toppane.TopPane;
import com.jackmeng.halcyon.connections.properties.ResourceFolder;
import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.Manager;
import com.jackmeng.halcyon.constant.ProgramResourceManager;
import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.halcyon.debug.Program;
import com.jackmeng.halcyon.utils.FolderInfo;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

/**
 * <p>
 * Halcyon Music Player Application main
 * entry point class.
 * <br>
 * The Halcyon Music Player is a 3.0 iteration
 * of the original MP4J project, which you can find
 * here: {@linkplain https://github.com/Exoad4JVM/mp4j}.
 * However this program iteration is designed to be much
 * more optimized and have a better audio engine with less
 * restrictive licensing towards any end users, including,
 * me being the author of this program.
 * <br>
 * This program is licensed under the GPL-2.0 license:
 * {@linkplain https://www.gnu.org/licenses/gpl-2.0.html}
 * <br>
 * A copy of the license must be attached to all distributions
 * and copies of this program, source code, and associated linked
 * libraries. If you have not received a copy of the license, please
 * contact me at: {@link mailto://jackmeng0814@gmail.com}
 *
 * Any external libraries used by this program including the audio
 * engine are licensed separately and are included in this program,
 * and also any other subsequent programs that are distributed with
 * or utilizing this library.
 *
 * <br>
 * NOTICE: This program is made in the purpose for educational and private use,
 * the original author, Jack Meng and any other contributors, cannot be held
 * responsible for any damage, loss, or anything else that may occur due to the
 * usage
 * of this program. Nor, can such contributors can be held responsible for any
 * illegal activities, of those that voids a country's copyright, and/or
 * international
 * copyright laws.
 * <br>
 *
 * This is the main class that starts the program.
 *
 * It manages setting up the UI and reading any external
 * resources.
 *
 * Besides this, this class should not pass any references nor
 * should this class be extended or instantiated in any way.
 *
 * If there needs to be any objects that needs to be passed down,
 * the programmer must specify that as a global scope object in
 * {@link com.jackmeng.halcyon.constant.Global}.
 * </p>
 *
 * @author Jack Meng
 * @since 3.0
 * @see com.jackmeng.halcyon.constant.Global
 */
public class Halcyon {
  /**
   * This is the only instance of a variable
   * being placed here.
   */
  public static Tailwind bgt;

  /**
   * No arguments are taken from the entry point
   *
   * @param args Null arguments
   */
  public static void main(String... args) {
    Program.syncPID();
    if (args.length > 0) {
      if (args[0].equals("-debug")) {
        ProjectManager.DEBUG_PROGRAM = true;
        Debugger.DISABLE_DEBUGGER = false;
      }
    }
    try {
      ResourceFolder.checkResourceFolder(
          ProgramResourceManager.PROGRAM_RESOURCE_FOLDER);
      for (String str : ProgramResourceManager.RESOURCE_SUBFOLDERS) {
        ResourceFolder.createFolder(str);
      }

      ResourceFolder.pm.checkAllPropertiesExistence();

      new ThreadedScheduler();
      TopPane tp = new TopPane(Global.ifp, Global.bctp);
      Global.ifp.addInfoViewUpdateListener(Global.bctp);
      JSplitPane bottom = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
      bottom.setMinimumSize(
          new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
      bottom.setPreferredSize(
          new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
      bottom.setMaximumSize(
          new Dimension(Manager.MAX_WIDTH, Manager.MAX_HEIGHT / 2));
      ArrayList<BBlocButton> bb = new ArrayList<>();
      bb.add(new AddFolder());
      bb.add(new RefreshFileView());
      bb.add(new Settings());
      bb.add(new LegalNoticeButton());
      bb.add(
          GenericWebsiteLinker.getButton(
              ProjectManager.PROJECT_GITHUB_PAGE,
              Manager.PROJECTPAGE_BUTTON_TOOLTIP,
              Global.rd.getFromAsImageIcon(Manager.GITHUB_LOGO_LIGHT)));
      BBlocView b = new BBlocView();
      b.addBBlockButtons(bb.toArray(new BBlocButton[bb.size()]));
      bottom.add(b);
      bottom.add(Global.bp);

      JSplitPane m = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tp, bottom);
      bgt = new Tailwind(m);

      FolderInfo[] fi = Program.fetchSavedPlayLists();
      if (fi.length > 0) {
        for (FolderInfo f : fi) {
          if (new File(f.getAbsolutePath()).exists() && new File(f.getAbsolutePath()).isDirectory()) {
            Global.bp.pokeNewFileListTab(f.getAbsolutePath());
            Debugger.good("Added playlist: " + f.getAbsolutePath());
          } else {
            Debugger.warn("Could not add playlist: " + f.getAbsolutePath());
          }
        }
      }

      bgt.run();
      // IGNORED FOR NOW: Global.ifp.addInfoViewUpdateListener(new Discordo());

    } catch (Exception ex) {
      ResourceFolder.dispatchLog(ex);
      new ErrorWindow(ex.toString()).run();
    }
  }
}
