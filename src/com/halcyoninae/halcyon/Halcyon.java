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

package com.halcyoninae.halcyon;

import com.halcyoninae.cosmos.ThreadedScheduler;
import com.halcyoninae.cosmos.components.Cosmos;
import com.halcyoninae.cosmos.components.bbloc.BBlocButton;
import com.halcyoninae.cosmos.components.bbloc.BBlocView;
import com.halcyoninae.cosmos.components.bbloc.buttons.*;
import com.halcyoninae.cosmos.components.dialog.ConfirmWindow;
import com.halcyoninae.cosmos.components.dialog.ErrorWindow;
import com.halcyoninae.cosmos.components.dialog.LoadingDialog;
import com.halcyoninae.cosmos.components.toppane.TopPane;
import com.halcyoninae.halcyon.connections.discord.Discordo;
import com.halcyoninae.halcyon.connections.properties.ProgramResourceManager;
import com.halcyoninae.halcyon.connections.properties.ResourceFolder;
import com.halcyoninae.halcyon.constant.ColorManager;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.Manager;
import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.halcyon.filesystem.PhysicalFolder;
import com.halcyoninae.halcyon.runtime.Program;
import com.halcyoninae.halcyon.utils.TextParser;
import com.halcyoninae.setup.Setup;
import com.halcyoninae.setup.SetupListener;
import com.halcyoninae.setup.SetupStatus;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * <p>
 * Halcyon Music Player Application main
 * entry point class.
 * <br>
 * The Halcyon Music Player is a 3.0 iteration
 * of the original MP4J project, which you can find
 * here: https://github.com/Exoad4JVM/mp4j.
 * However this program iteration is designed to be much
 * more optimized and have a better audio engine with less
 * restrictive licensing towards any end users, including,
 * me being the author of this program.
 * <br>
 * This program is licensed under the GPL-2.0 license:
 * https://www.gnu.org/licenses/gpl-2.0.html
 * <br>
 * A copy of the license must be attached to all distributions
 * and copies of this program, source code, and associated linked
 * libraries. If you have not received a copy of the license, please
 * contact me at: mailto://jackmeng0814@gmail.com
 * <p>
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
 * <p>
 * This is the main class that starts the program.
 * <p>
 * It manages setting up the UI and reading any external
 * resources.
 * <p>
 * Besides this, this class should not pass any references nor
 * should this class be extended or instantiated in any way.
 * <p>
 * If there needs to be any objects that needs to be passed down,
 * the programmer must specify that as a global scope object in
 * {@link com.halcyoninae.halcyon.constant.Global}.
 * </p>
 *
 * @author Jack Meng
 * @see com.halcyoninae.halcyon.constant.Global
 * @since 3.0
 */
public class Halcyon {
    /**
     * This is the only instance of a variable
     * being placed here.
     */
    public static com.halcyoninae.cosmos.components.Cosmos bgt;

    private static void boot_kick_mainUI() {
        Debugger.info(System.getProperty("java.class.path"));
        try {
            Cosmos.refreshUI(ColorManager.programTheme);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        TopPane tp = new TopPane(Global.ifp, Global.bctp);
        Global.ifp.addInfoViewUpdateListener(Global.bctp);
        JSplitPane bottom = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        bottom.setMinimumSize(
                new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
        bottom.setPreferredSize(
                new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
        ArrayList<BBlocButton> bb = new ArrayList<>();
        bb.add(new AddFolder());
        bb.add(new RefreshFileView());
        bb.add(new SlidersControl());
        bb.add(new MinimizePlayer());
        bb.add(new Settings());
        bb.add(new LegalNoticeButton());
        bb.add(
                GenericWebsiteLinker.getButton(
                        DefaultManager.PROJECT_GITHUB_PAGE,
                        Manager.PROJECTPAGE_BUTTON_TOOLTIP,
                        Global.rd.getFromAsImageIcon(Manager.GITHUB_LOGO_LIGHT)));
        BBlocView b = new BBlocView();
        b.addBBlockButtons(bb.toArray(new BBlocButton[0]));
        bottom.add(b);
        bottom.add(Global.bp);

        JSplitPane m = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tp, bottom);
        bgt = new com.halcyoninae.cosmos.components.Cosmos(m);
        Global.bp.pokeewFileList(Global.ll);

        PhysicalFolder[] fi = Program.fetchSavedPlayLists();
        if (fi.length > 0) {
            for (PhysicalFolder f : fi) {
                if (new File(f.getAbsolutePath()).exists() && new File(f.getAbsolutePath()).isDirectory()) {
                    Global.bp.pokeNewFileListTab(f.getAbsolutePath());
                    Debugger.good("Added playlist: " + f.getAbsolutePath());
                } else {
                    Debugger.warn("Could not add playlist: " + f.getAbsolutePath());
                }
            }
        }

        File[] files = Program.fetchLikedTracks();
        if (files.length > 0) {
            for (File f : files) {
                if (f.exists() && f.isFile()) {
                    Global.ll.set(f.getAbsolutePath());
                    Debugger.good("Added Liked Track: " + f.getAbsolutePath());
                } else {
                    Debugger.warn("Could not load liked track: " + f.getAbsolutePath());
                }
            }
        }

        bgt.run();

        if (ResourceFolder.pm.get(ProgramResourceManager.KEY_USER_USE_DISCORD_RPC).equals("true")) {
            Discordo dp = new Discordo();
            Global.ifp.addInfoViewUpdateListener(dp);
            dp.start();
        }
    }

    private static void run() {
        try {
            LoadingDialog ld = new LoadingDialog("Starting the program!\nPlease be patient.", true);

            Thread d = new Thread(ld::run);
            d.start();
            Setup.addSetupListener(new SetupListener() {
                @Override
                public void updateStatus(SetupStatus e) {
                    boot_kick_mainUI();
                }
            });

            Setup.main(Setup.KILL_ARG);

            ld.kill();
            d.interrupt();
            d = null;
            System.gc();
        } catch (Exception e) {
            ResourceFolder.dispatchLog(e);
        }
    }

    /**
     * No arguments are taken from the entry point
     *
     * @param args Null arguments
     */
    public static void main(String... args) {
        if (args.length > 0) {
            if (args[0].equals("-debug")) {
                DefaultManager.DEBUG_PROGRAM = true;
                Debugger.DISABLE_DEBUGGER = false;
            }
        }
        try {
            UIManager.setLookAndFeel(ColorManager.programTheme.getLAF().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResourceFolder.checkResourceFolder(
                    ProgramResourceManager.PROGRAM_RESOURCE_FOLDER);

            for (String str : ProgramResourceManager.RESOURCE_SUBFOLDERS) {
                ResourceFolder.createFolder(str);
            }

            ResourceFolder.pm.checkAllPropertiesExistence();

            Debugger.good("Loading encoding as: " + TextParser.getPropertyTextEncodingName());
            new ThreadedScheduler();

            if (ResourceFolder.pm.get(ProgramResourceManager.KEY_PROGRAM_FORCE_OPTIMIZATION).equals("false")) {
                new ConfirmWindow(
                        "You seemed to have turned off Forced Optimization, this can result in increased performance loss. It is best to keep it on!",
                        new ConfirmWindow.ConfirmationListener() {
                            @Override
                            public void onStatus(boolean status) {
                                if (status) {
                                    run();
                                } else
                                    System.exit(0);
                            }
                        }).run();
            } else {
                run();
            }
        } catch (Exception ex) {
            ResourceFolder.dispatchLog(ex);
            new ErrorWindow(ex.toString()).run();
        }
    }
}
