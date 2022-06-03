package debug;

import project.utils.TimeParser;

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
  private Debugger() {
  }

  /**
   * This function returns the default heder text to define itself as a
   * logging message instead of other extraneous messages.
   * 
   * @return The Header Log text
   */
  public static String getLogText() {
    return "[debug ~ MP4J@" + TimeParser.getLogCurrentTime() + "] > ";
  }

  /**
   * Prints the necessary Objects to System.err
   * 
   * @param <T> The varargs of types as a generic
   * @param o   The objects to be printed to the stream
   */
  @SafeVarargs
  public static <T> void log(T... o) {
    for (int i = 0; i < o.length; i++) {
      if (o[i] != null) {
        System.err.print(o[i].toString() + " ");
      } else {
        System.err.print(getLogText() + "NULL_CONTENT" + " ");
      }
    }
    System.err.println();
  }
}
