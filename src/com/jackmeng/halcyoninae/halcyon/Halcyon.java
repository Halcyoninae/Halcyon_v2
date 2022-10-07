/*
 * Halcyon ~ exoad
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https://raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
 */

package com.jackmeng.halcyoninae.halcyon;

import com.jackmeng.halcyoninae.cosmos.Cosmos;
import com.jackmeng.halcyoninae.cosmos.components.ConfirmWindow;
import com.jackmeng.halcyoninae.cosmos.components.ErrorWindow;
import com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.BBlocButton;
import com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.BBlocView;
import com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.buttons.*;
import com.jackmeng.halcyoninae.cosmos.components.toppane.TopPane;
import com.jackmeng.halcyoninae.cosmos.tasks.ThreadedScheduler;
import com.jackmeng.halcyoninae.halcyon.runtime.Discordo;
import com.jackmeng.halcyoninae.halcyon.runtime.Program;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.CLIStyles;
import com.jackmeng.halcyoninae.halcyon.utils.Debugger;
import com.jackmeng.halcyoninae.halcyon.utils.ExternalResource;
import com.jackmeng.halcyoninae.halcyon.utils.ProgramResourceManager;
import com.jackmeng.halcyoninae.halcyon.utils.TConstr;
import com.jackmeng.halcyoninae.setup.Setup;
import com.jackmeng.locale.PhysicalFolder;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
 * {@link com.jackmeng.halcyoninae.halcyon.runtime.constant.Global}.
 * </p>
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.halcyon.runtime.constant.Global
 * @since 3.0
 */
public final class Halcyon {

    public static com.jackmeng.halcyoninae.cosmos.Cosmos bgt;

    public static void boot_kick_mainUI() {
        try {
            Cosmos.refreshUI(ColorManager.programTheme);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        TopPane tp = new TopPane(Global.ifp, Global.bctp);
        Global.ifp.addInfoViewUpdateListener(Global.bctp);
        JSplitPane bottom = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        bottom.setPreferredSize(
                new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
        ArrayList<BBlocButton> bb = new ArrayList<>();
        bb.add(new AddFolder());
        bb.add(new RefreshFileView());
        bb.add(new SlidersControl());
        bb.add(new MinimizePlayer());
        bb.add(new CommandWindow());
        bb.add(new Settings());
        bb.add(new LegalNoticeButton());
        bb.add(new MoreButton());
        BBlocView b = new BBlocView();
        b.addBBlockButtons(bb.toArray(new BBlocButton[0]));
        bottom.add(b);
        bottom.add(Global.bp);

        JSplitPane m = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tp, bottom);
        bgt = new com.jackmeng.halcyoninae.cosmos.Cosmos(m);
        Global.bp.pokeNewFileListTab(Global.ll);

        PhysicalFolder[] fi = Program.fetchSavedPlayLists();
        Debugger.warn("SPL_REC: " + Arrays.toString(Program.fetchLikedTracks()));
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

        bgt.run();
        bgt.getFrame().setSize(new Dimension(514, 518));
        Debugger.crit(ExternalResource.pm.exposeProperties().toString());
    }

    private static void run() {
        try {
            Setup.addSetupListener(e -> {
                SwingUtilities.invokeLater(Halcyon::boot_kick_mainUI);
                if (ExternalResource.pm.get(ProgramResourceManager.KEY_USER_USE_DISCORD_RPC).equals("true")) {
                    Debugger.alert(new TConstr(CLIStyles.CYAN_TXT, "Loading the almighty Discord RPC ;3"));
                    Discordo dp = new Discordo();
                    Global.ifp.addInfoViewUpdateListener(dp);
                    dp.start();
                } else {
                    Debugger.warn("Not starting the Discord RPC :3 okie doke!");
                }
            });
            Setup.main((String[]) null);
        } catch (Exception e) {
            ExternalResource.dispatchLog(e);
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
            ExternalResource.checkResourceFolder(
                    ProgramResourceManager.PROGRAM_RESOURCE_FOLDER);

            for (String str : ProgramResourceManager.RESOURCE_SUBFOLDERS) {
                ExternalResource.createFolder(str);
            }
            ExternalResource.pm.checkAllPropertiesExistence();

            if (ExternalResource.pm.get(ProgramResourceManager.KEY_USER_PROGRAM_USE_OPENGL).equals("true")) {
                System.setProperty("sun.java2d.opengl", "true");
            }
            /*
             * Everything else under this are GUI related code.
             */
            try {
                UIManager.setLookAndFeel(ColorManager.programTheme.getLAF().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new ThreadedScheduler();
            if (ExternalResource.pm.get(ProgramResourceManager.KEY_PROGRAM_FORCE_OPTIMIZATION).equals("false")) {
                new ConfirmWindow(
                        "Turning this feature OFF may result in unexpected performance anomalies!! Best to leave this on",
                        status -> {
                            if (status) {
                                run();
                            } else
                                System.exit(0);
                        }).run();
            } else {
                run();
            }
        } catch (Exception ex) {
            new ErrorWindow(ex.toString()).run();
            ExternalResource.dispatchLog(ex);
        }
    }
}
