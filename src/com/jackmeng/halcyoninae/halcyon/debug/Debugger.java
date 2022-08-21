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

package com.jackmeng.halcyoninae.halcyon.debug;


import com.jackmeng.halcyoninae.halcyon.DefaultManager;
import com.jackmeng.halcyoninae.halcyon.utils.TimeParser;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

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
     * runtime; this boolean only stops any further synced output stream
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
    public static String getLogText() {
        return "[LOG ~ MP4J@" + TimeParser.getLogCurrentTime() + "]$ Halcyon > ";
    }

    /**
     * @return String Get's the default warning message header.
     */
    public static String getWarnText() {
        return "[WRN ~ MP4J@" + TimeParser.getLogCurrentTime() + "]$ Halcyon > ";
    }

    /**
     * @return String Gets the default success/good message header
     */
    public static String getGoodText() {
        return "[SCS ~ MP4J@" + TimeParser.getLogCurrentTime() + "]$ Halcyon > ";
    }

    /**
     * Default alert
     *
     * @return String Gets teh default program alert message header
     */
    public static String getProgramText() {
        return "[PGM ~ MP4J@" + TimeParser.getLogCurrentTime() + "]$ Halcyon > ";
    }

    /**
     * @return String
     */
    public static String getDefaultInfoText() {
        return "[INF ~ MP4J@" + TimeParser.getLogCurrentTime() + "]$ Halcyon > ";
    }

    /**
     * Prints the necessary Objects to System. Err
     *
     * @param <T> The varargs of types as a generic
     * @param o   The objects to be printed to the stream
     */
    @SafeVarargs
    public static <T> void log(T... o) {
        if (!DISABLE_DEBUGGER) {
                for (T t : o) {
                    out.println(getLogText() + Objects.requireNonNullElse(t, "NULL_CONTENT") + " ");
                }
                out.println();
        } else {
            String[] s = new String[o.length];
            int i = 0;
            for (T t : o) {
                s[i] = t.toString();
                i++;
            }
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
        if (!DefaultManager.DEBUG_PROGRAM) {
            for (T t : o) {
                out.println(
                    CLIStyles.BOLD.getColor() + getWarnText()
                        + CLIStyles.RESET.getColor()
                        + CLIStyles.YELLOW_TXT.getColor() + Objects.requireNonNullElse(t, "NULL_CONTENT")
                        + CLIStyles.RESET.getColor());
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
        if (!DefaultManager.DEBUG_PROGRAM) {
            for (T t : o) {
                out.println(
                    CLIStyles.BOLD.getColor() + getGoodText()
                        + CLIStyles.RESET.getColor()
                        + CLIStyles.GREEN_TXT.getColor() + Objects.requireNonNullElse(t, "NULL_CONTENT")
                        + CLIStyles.RESET.getColor());
            }
        }
    }

    /**
     * @param e
     */
    public static void byteLog(byte... e) {
        if (!DefaultManager.DEBUG_PROGRAM) {
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
        if (!DefaultManager.DEBUG_PROGRAM) {
            for (T t : o) {
                if (t != null) {
                    out.println(CLIStyles.BOLD.getColor() + getLogText() + CLIStyles.RESET.getColor() + t + " ");
                } else {
                    out.println(getLogText() + "NULL_CONTENT" + " ");
                }
            }
        }
    }

    @SafeVarargs
    public static <T> void crit(T ... o) {
        if (!DefaultManager.DEBUG_PROGRAM) {
            for (T t : o) {
                out.println(
                        CLIStyles.BOLD.getColor() + getGoodText()
                                + CLIStyles.RESET.getColor()
                                + CLIStyles.RED_TXT.getColor() + Objects.requireNonNullElse(t, "NULL_CONTENT")
                                + CLIStyles.RESET.getColor());
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
        if (!DefaultManager.DEBUG_PROGRAM) {
            for (T t : o) {
                out.println(
                    CLIStyles.BOLD.getColor() + getGoodText()
                        + CLIStyles.RESET.getColor()
                        + CLIStyles.BLUE_TXT.getColor() + Objects.requireNonNullElse(t, "NULL_CONTENT")
                        + CLIStyles.RESET.getColor());
            }
        }
    }

    /**
     *
     * @param t
     */
    public static void alert(TConstr... t) {
        if (!DefaultManager.DEBUG_PROGRAM) {
            for (TConstr x : t) {
                if (x != null) {
                    out.println(CLIStyles.BOLD.getColor() + getProgramText() + CLIStyles.RESET.getColor()
                            + x + CLIStyles.RESET.getColor());
                } else {
                    out.println(CLIStyles.BOLD.getColor() + getProgramText()
                            + CLIStyles.RESET.getColor()
                            + CLIStyles.BLUE_TXT.getColor() + "NULL_CONTENT"
                            + CLIStyles.RESET.getColor());
                }
            }
        }
    }

}
