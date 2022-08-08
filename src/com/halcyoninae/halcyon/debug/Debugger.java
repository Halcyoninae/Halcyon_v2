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

package com.halcyoninae.halcyon.debug;

import com.halcyoninae.halcyon.connections.properties.ProgramResourceManager;
import com.halcyoninae.halcyon.connections.properties.ResourceFolder;
import com.halcyoninae.halcyon.runtime.Program;
import com.halcyoninae.halcyon.utils.TimeParser;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * This is an external class that is called upon for when the
 * program needs something printed the Console.
 * <p>
 * However the standard console or System.out {@link java.lang.System}.out can
 * be disabled for extraneous logging.
 * <p>
 * This means the program must use out.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class Debugger {

    /**
     * The designated output stream to be used by
     * the debugger with attached encoding.
     */
    public static PrintStream out = new PrintStream(System.err, true, StandardCharsets.UTF_8);
    /**
     * A globally modifiable variable (should only be modified
     * programmatically) to either disable or enable during runtime.
     * <p>
     * Reminder: This does not clear the stream if it is called during
     * runtime; this boolean only stops any further asynced outputstream
     * that have not reached the stage of a check on the state of this
     * variable's equality.
     */
    public static boolean DISABLE_DEBUGGER = true;

    /**
     * Sad constructor that is ignored :(
     */
    private Debugger() {
    }

    /**
     * This function returns the default heder text to define itself as a
     * logging message instead of other extraneous messages.
     *
     * @return The Header Log text
     */
    public static String getLogText(Class<?> am) {
        return "[Debug ~ MP4J@" + TimeParser.getLogCurrentTime() + am.getSimpleName() + "] > ";
    }

    /**
     * @return String Get's the default warning message header.
     */
    public static String getWarnText() {
        return "[WARNING ~ MP4J@" + TimeParser.getLogCurrentTime() + "] > ";
    }

    /**
     * @return String Gets the default success/good message header
     */
    public static String getGoodText() {
        return "[SUCCESS ~ MP4J@" + TimeParser.getLogCurrentTime() + "] > ";
    }

    /**
     * @return String
     */
    public static String getDefaultInfoText() {
        return "[INFO ~ MP4J@" + TimeParser.getLogCurrentTime() + "] > ";
    }

    /**
     * Prints the necessary Objects to System.err
     *
     * @param <T> The varargs of types as a generic
     * @param o   The objects to be printed to the stream
     */
    @SafeVarargs
    public static <T> void log(T... o) {
        if (!DISABLE_DEBUGGER) {
            new Thread(() -> {
                for (T t : o) {
                    if (t != null) {
                        out.println(getLogText(o.getClass()) + t + " ");
                    } else {
                        out.println(getLogText(o.getClass()) + "NULL_CONTENT" + " ");
                    }
                }
                out.println();
            }).start();
        } else {
            String[] s = new String[o.length];
            int i = 0;
            for (T t : o) {
                s[i] = t.toString();
                i++;
            }
            Program.main(s);
        }
    }

    /**
     * Similar to {@link #log(Object...)} but with custom
     * coloring and custom header to signify a warning log.
     * <p>
     * This is not specifically for debugging and is used to
     * signify a fault somewhere that needs major attention.
     *
     * @param <T> The varargs of types as a generic
     * @param o   The objects to be printed to the stream
     */
    @SafeVarargs
    public static <T> void warn(T... o) {
        if (ResourceFolder.pm.get(ProgramResourceManager.KEY_USER_DSIABLE_CLI).equals("false")) {
            for (T t : o) {
                if (t != null) {
                    out.println(com.halcyoninae.halcyon.debug.Debugger.CLIStyles.BOLD.getColor() + getWarnText()
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RESET.getColor()
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RED_BG.getColor()
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RED_TXT.getColor() + t
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RESET.getColor());
                } else {
                    out.println(com.halcyoninae.halcyon.debug.Debugger.CLIStyles.BOLD.getColor() + getWarnText()
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RESET.getColor()
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RED_BG.getColor()
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RED_TXT.getColor() + "NULL_CONTENT"
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RESET.getColor());
                }
            }
        }
    }

    /**
     * Similar to {@link #log(Object...)} and {@link #warn(Object...)}
     * but instead is for when something goes good.
     * <p>
     * This is not specifically for debugging and is used
     * to signify a success message.
     *
     * @param <T> The varargs of types as a generic
     * @param o   The objects to be printed to the stream
     */
    @SafeVarargs
    public static <T> void good(T... o) {
        if (ResourceFolder.pm.get(ProgramResourceManager.KEY_USER_DSIABLE_CLI).equals("false")) {
            for (T t : o) {
                if (t != null) {
                    out.println(com.halcyoninae.halcyon.debug.Debugger.CLIStyles.BOLD.getColor() + getGoodText()
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RESET.getColor()
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.GREEN_TXT.getColor() + t
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RESET.getColor());
                } else {
                    out.println(com.halcyoninae.halcyon.debug.Debugger.CLIStyles.BOLD.getColor() + getGoodText()
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RESET.getColor()
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.GREEN_TXT.getColor() + "NULL_CONTENT"
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RESET.getColor());
                }
            }
        }
    }

    /**
     * @param e
     */
    public static void byteLog(byte... e) {
        if (ResourceFolder.pm.get(ProgramResourceManager.KEY_USER_DSIABLE_CLI).equals("false")) {
            out.println(Arrays.toString(e));
        }
    }

    /**
     * This method is only used for programming purposes.
     *
     * @param <T>
     * @param o
     */
    @SafeVarargs
    public static <T> void unsafeLog(T... o) {
        if (ResourceFolder.pm.get(ProgramResourceManager.KEY_USER_DSIABLE_CLI).equals("false")) {
            for (T t : o) {
                if (t != null) {
                    out.println(getLogText(o.getClass()) + t + " ");
                } else {
                    out.println(getLogText(o.getClass()) + "NULL_CONTENT" + " ");
                }
            }
        }
    }

    /**
     * @param <T>
     * @param o
     * @since 3.3
     */
    @SafeVarargs
    public static <T> void info(T... o) {
        if (ResourceFolder.pm.get(ProgramResourceManager.KEY_USER_DSIABLE_CLI).equals("false")) {
            for (T t : o) {
                if (t != null) {
                    out.println(com.halcyoninae.halcyon.debug.Debugger.CLIStyles.BOLD.getColor() + getGoodText()
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RESET.getColor()
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.BLUE_TXT.getColor() + t
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RESET.getColor());
                } else {
                    out.println(com.halcyoninae.halcyon.debug.Debugger.CLIStyles.BOLD.getColor() + getGoodText()
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RESET.getColor()
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.BLUE_TXT.getColor() + "NULL_CONTENT"
                            + com.halcyoninae.halcyon.debug.Debugger.CLIStyles.RESET.getColor());
                }
            }
        }
    }

    /**
     * Represents the styles that can be used with the
     * CLI interface
     * <p>
     * Adapted from an older version
     *
     * @author Jack Meng
     * @since 2.0
     */
    public enum CLIStyles {
        RED_BG("\u001B[41m"),
        GREEN_BG("\u001B[42m"),
        YELLOW_BG("\u001B[43m"),
        BLUE_BG("\u001B[44m"),
        MAGENTA_BG("\u001B[45m"),
        CYAN_BG("\u001B[46m"),
        WHITE_BG("\u001B[47m"),
        BLACK_BG("\u001B[40m"),
        RESET("\u001B[0m"),
        BOLD("\u001B[1m"),
        UNDERLINE("\u001B[4m"),
        BLINK("\u001B[5m"),
        REVERSE("\u001B[7m"),
        HIDDEN("\u001B[8m"),
        RED_TXT("\u001B[31m"),
        GREEN_TXT("\u001B[32m"),
        YELLOW_TXT("\u001B[33m"),
        BLUE_TXT("\u001B[34m"),
        MAGENTA_TXT("\u001B[35m"),
        CYAN_TXT("\u001B[36m"),
        WHITE_TXT("\u001B[37m"),
        BLACK_TXT("\u001B[30m");

        private final String color;

        CLIStyles(String e) {
            color = e;
        }

        /**
         * @return A string formatting code
         */
        public String getColor() {
            return color;
        }
    }
}
