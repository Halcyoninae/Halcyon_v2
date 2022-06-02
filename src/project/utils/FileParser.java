package project.utils;

import java.io.*;

public class FileParser {
  private FileParser() {
  }

  public static File[] parseOnlyAudioFiles(File folder, String[] rules) {
    File[] files = folder.listFiles();
    File[] audioFiles = new File[files.length];
    for (int i = 0; i < files.length; i++) {
      if (files[i].isFile()) {
        for (int j = 0; j < rules.length; j++) {
          if (files[i].getAbsolutePath().endsWith(rules[j])) {
            audioFiles[i] = files[i];
            System.out.println(audioFiles[i].getName());
          }
        }
      }
    }
    return audioFiles;
  }

  public static String folderName(String f) {
    return new File(f).getName();
  }

  public static String[] parseFileNames(File... files) {
    String[] fileNames = new String[files.length];
    for (int i = 0; i < files.length; i++) {
      fileNames[i] = files[i].getName();
    }
    return fileNames;
  }

  public static boolean isEmptyFolder(File folder) {
    File[] files = folder.listFiles();
    return files.length == 0;
  }
}
