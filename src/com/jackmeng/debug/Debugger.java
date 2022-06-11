package com.jackmeng.debug;

import com.jackmeng.app.utils.TimeParser;

/**
 * This is an external class that is called upon for when the
 * program needs something printed the Console.
 * 
 * However the standard console or System.out {@link java.lang.System}.out can
 * be disabled for extraneous logging.
 * 
 * This means the program must use System.err.
 * 
 * @author Jack Meng
 * @since 3.0
 */
public class Debugger {
  protected static final boolean DISABLE_DEBUGGER = true;

  private Debugger() {
  }

  /**
   * This function returns the default heder text to define itself as a
   * logging message instead of other extraneous messages.
   * 
   * @return The Header Log text
   */
  public static String getLogText() {
    return "[Debug ~ MP4J@" + TimeParser.getLogCurrentTime() + "] > ";
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
        for (int i = 0; i < o.length; i++) {
          if (o[i] != null) {
            System.err.println(getLogText() + o[i].toString() + " ");
          } else {
            System.err.println(getLogText() + "NULL_CONTENT" + " ");
          }
        }
        System.err.println();
      }).start();
    }
  }
}
