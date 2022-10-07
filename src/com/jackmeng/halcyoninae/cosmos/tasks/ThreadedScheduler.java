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

package com.jackmeng.halcyoninae.cosmos.tasks;

import com.jackmeng.halcyoninae.halcyon.runtime.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;
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
        UIManager.put("TitlePane.closeHoverForeground", new ColorUIResource(ColorManager.MAIN_FG2_THEME));
        UIManager.put("TitlePane.closePressedForeground", new ColorUIResource(ColorManager.MAIN_FG2_THEME));
        UIManager.put("TitlePane.buttonHoverForeground", new ColorUIResource(ColorManager.MAIN_FG2_THEME));
        UIManager.put("TitlePane.buttonPressedForeground", new ColorUIResource(ColorManager.MAIN_FG2_THEME));
        UIManager.put("Component.focusedBorderColor", ColorTool.getNullColor()); // takes a generic java.awt.Color
        // object
        UIManager.put("Component.focusColor", ColorTool.getNullColor()); // takes a generic java.awt.Color object
        UIManager.put("TitlePane.centerTitle", true);
        UIManager.put("TitlePane.buttonSize", new java.awt.Dimension(25, 20));
        UIManager.put("TitlePane.unifiedBackground", true);
        UIManager.put("SplitPaneDivider.gripDotCount", 0);
        UIManager.put("FileChooser.readOnly", false);
        LogManager.getLogManager().reset();


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
