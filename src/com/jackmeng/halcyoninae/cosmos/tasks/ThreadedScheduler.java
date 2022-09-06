/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly.
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

package com.jackmeng.halcyoninae.cosmos.tasks;

import com.jackmeng.halcyoninae.halcyon.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.utils.ColorTool;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.util.logging.LogManager;

public class ThreadedScheduler {
    /*
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
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
        System.setProperty("flatlaf.useWindowDecorations", "false");
        System.setProperty("flatlaf.uiScale.enabled", "false");
        System.setProperty("flatlaf.useJetBrainsCustomDecorations", "false");
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("ScrollBar.background", null);
        UIManager.put("ScrollBar.thumb", new ColorUIResource(ColorManager.MAIN_FG_FADED_THEME));
        UIManager.put("Scrollbar.pressedThumbColor", new ColorUIResource(ColorManager.MAIN_FG_FADED_THEME));
        UIManager.put("ScrollBar.hoverThumbColor", new ColorUIResource(ColorManager.MAIN_FG_FADED_THEME));
        UIManager.put("TabbedPane.underlineColor", new ColorUIResource(ColorManager.MAIN_FG_FADED_THEME));
        UIManager.put("TabbedPane.showTabSeparators", true);
        UIManager.put("ScrollBar.showButtons", false);
        UIManager.put("TitlePane.closeHoverBackground", new ColorUIResource(ColorManager.MAIN_FG_THEME));
        UIManager.put("TitlePane.closePressedBackground", new ColorUIResource(ColorManager.MAIN_FG_THEME));
        UIManager.put("TitlePane.buttonHoverBackground", new ColorUIResource(ColorManager.MAIN_FG_THEME));
        UIManager.put("TitlePane.buttonPressedBackground", new ColorUIResource(ColorManager.MAIN_FG_THEME));
        UIManager.put("TitlePane.closeHoverForeground", new ColorUIResource(ColorManager.MAIN_BG_THEME));
        UIManager.put("TitlePane.closePressedForeground", new ColorUIResource(ColorManager.MAIN_BG_THEME));
        UIManager.put("TitlePane.buttonHoverForeground", new ColorUIResource(ColorManager.MAIN_BG_THEME));
        UIManager.put("TitlePane.buttonPressedForeground", new ColorUIResource(ColorManager.MAIN_BG_THEME));
        UIManager.put("Component.focusedBorderColor", ColorTool.getNullColor()); // takes a generic java.awt.Color
        // object
        UIManager.put("Component.focusColor", ColorTool.getNullColor()); // takes a generic java.awt.Color object
        UIManager.put("TitlePane.centerTitle", true);
        UIManager.put("TitlePane.buttonSize", new java.awt.Dimension(25, 20));
        UIManager.put("TitlePane.unifiedBackground", true);
        UIManager.put("SplitPaneDivider.gripDotCount", 0);
        UIManager.put("FileChooser.readOnly", false);
        LogManager.getLogManager().reset();
        // fix blurriness on high-DPI screens
        System.setProperty("sun.java2d.opengl", "true");
        System.setProperty("sun.java2d.uiScale", "0.9");

        // PROGRAMMABLE THREADS
        WeakReference<Runnable[]> tasks = new WeakReference<>(new Runnable[]{
            new PingFileView(Global.bp),
            new DefunctOptimizer(),
        });

        for (Runnable t : tasks.get()) {
            t.run();
        }

    }

    public ThreadedScheduler() {
    }
}
