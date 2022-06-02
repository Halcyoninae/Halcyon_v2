package project.utils;

public class FileParser {
  private FileParser() {
  }

  public static String getDirectoryName(String absoluteFilePath) {
    return absoluteFilePath.substring(0, absoluteFilePath.lastIndexOf("/"));
  }
}
