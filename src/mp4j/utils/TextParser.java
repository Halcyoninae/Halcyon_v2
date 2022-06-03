package mp4j.utils;

public class TextParser {
  private TextParser() {}

  public static String strip(String str, int validLength) {
    return str.length() > validLength ? str.substring(0, validLength) + "..." : str;
  }
}