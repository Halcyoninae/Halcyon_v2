package debug;

import project.utils.TimeParser;

public class Debugger {
  private Debugger() {}

  public static String getLogText() {
    return "[debug ~ MP4J@" + TimeParser.getLogCurrentTime() + "] > ";
  }

  @SafeVarargs
  public static <T> void log(T ... o) {
    for(int i = 0; i < o.length; i++) {
      if(o[i] != null) {
      System.err.print(o[i].toString() + " ");
      } else {
        System.err.print(getLogText() + "NULL_CONTENT" + " ");
      }
    }
    System.err.println();
  }
}
