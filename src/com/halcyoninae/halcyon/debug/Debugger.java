/*
 *  Copyright: (C) 2022 MP4J Jack Meng
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

import com.halcyoninae.halcyon.DefaultManager;
import com.halcyoninae.halcyon.connections.properties.ExternalResource;
import com.halcyoninae.halcyon.connections.properties.ProgramResourceManager;
import com.halcyoninae.halcyon.utils.TimeParser;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.lang.model.type.NullType;

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
     * @param am
     * @return The Header Log text
     */
    public static String getLogText(Class<?> am) {
        return "[DEBUG ~ MP4J@" + TimeParser.getLogCurrentTime() + "@" + am.getSimpleName() + "] > ";
    }

    /**
     * @param am
     * @return String Get's the default warning message header.
     */
    public static String getWarnText(Class<?> am) {
        return "[WARNING ~ MP4J@" + TimeParser.getLogCurrentTime() + "@" + am.getSimpleName() + "] > ";
    }

    /**
     * @param am
     * @return String Gets the default success/good message header
     */
    public static String getGoodText(Class<?> am) {
        return "[SUCCESS ~ MP4J@" + TimeParser.getLogCurrentTime() + "@" + am.getSimpleName() + "] > ";
    }

    /**
     * Default alert
     *
     * @param am
     * @return
     */
    public static String getProgramText(Class<?> am) {
        return "[PROGRAM ~ MP4J@" + TimeParser.getLogCurrentTime() + "@" + am.getSimpleName() + "] > ";
    }

    /**
     * @param am
     * @return String
     */
    public static String getDefaultInfoText(Class<?> am) {
        return "[INFO ~ MP4J@" + TimeParser.getLogCurrentTime() + "@" + am.getSimpleName() + "] > ";
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
                if (t != null) {
                    out.println(
                            CLIStyles.BOLD.getColor() + getWarnText(t.getClass())
                                    + CLIStyles.RESET.getColor()
                                    + CLIStyles.RED_BG.getColor()
                                    + CLIStyles.RED_TXT.getColor() + t
                                    + CLIStyles.RESET.getColor());
                } else {
                    out.println(CLIStyles.BOLD.getColor()
                            + getWarnText(NullType.class)
                            + CLIStyles.RESET.getColor()
                            + CLIStyles.RED_BG.getColor()
                            + CLIStyles.RED_TXT.getColor() + "NULL_CONTENT"
                            + CLIStyles.RESET.getColor());
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
        if (!DefaultManager.DEBUG_PROGRAM) {
            for (T t : o) {
                if (t != null) {
                    out.println(
                            CLIStyles.BOLD.getColor() + getGoodText(t.getClass())
                                    + CLIStyles.RESET.getColor()
                                    + CLIStyles.GREEN_TXT.getColor() + t
                                    + CLIStyles.RESET.getColor());
                } else {
                    out.println(CLIStyles.BOLD.getColor() + getGoodText(
                            NullType.class)
                            + CLIStyles.RESET.getColor()
                            + CLIStyles.GREEN_TXT.getColor() + "NULL_CONTENT"
                            + CLIStyles.RESET.getColor());
                }
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
        if (!DefaultManager.DEBUG_PROGRAM) {
            for (T t : o) {
                if (t != null) {
                    out.println(
                            CLIStyles.BOLD.getColor() + getGoodText(t.getClass())
                                    + CLIStyles.RESET.getColor()
                                    + CLIStyles.BLUE_TXT.getColor() + t
                                    + CLIStyles.RESET.getColor());
                } else {
                    out.println(CLIStyles.BOLD.getColor() + getGoodText(
                            NullType.class)
                            + CLIStyles.RESET.getColor()
                            + CLIStyles.BLUE_TXT.getColor() + "NULL_CONTENT"
                            + CLIStyles.RESET.getColor());
                }
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
                    out.println(CLIStyles.BOLD.getColor() + getProgramText(
                            NullType.class)
                            + CLIStyles.RESET.getColor() + x.toString() + CLIStyles.RESET.getColor());
                } else {
                    out.println(CLIStyles.BOLD.getColor() + getProgramText(
                            NullType.class)
                            + CLIStyles.RESET.getColor()
                            + CLIStyles.BLUE_TXT.getColor() + "NULL_CONTENT"
                            + CLIStyles.RESET.getColor());
                }
            }
        }
    }

}
